/**
 * Copyright (C) 2020 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.connectors.sap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorException;
import org.bonitasoft.engine.connector.ConnectorValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bonitasoft.engine.connector.sap.SAPMonoDestinationDataProvider;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

/**
 * BE CAREFUL: only one destinationName is possible
 * 
 * Register and unregister of destination data provider need to be completely
 * reworked in order to support multithreading
 * 
 * @author Aurélien
 *
 */
public class SAPCallFunction extends AbstractConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(SAPCallFunction.class);

	static final String MESSAGE_SERVER_TYPE = "MessageServer";
	static final String APPLICATION_SERVER_TYPE = "ApplicationServer";

	public static final String SERVER_TYPE = "serverType";
	public static final String CLIENT = "client";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String LANGUAGE = "language";
	public static final String HOST = "host";
	public static final String SYSTEM_NUMBER = "systemNumber";
	public static final String SYSTEM_ID = "systemId";
	public static final String GROUP_NAME = "groupName";
	public static final String DESTINATION_NAME = "destinationName";
	public static final String REPOSITORY = "repository";
	public static final String FUNCTION_NAME = "functionName";
	public static final String COMMIT_ON_SUCCESS = "commitOnSuccess";
	public static final String ROLLBACK_ON_FAILURE = "rollbackOnFailure";
	public static final String DESTINATION_DATA = "destinationData";
	// Need to be a List<List<Object>>
	// columns in the map must be in the following order:
	// parameterType
	// tableName
	// parameterName
	// parameterValue
	public static final String INPUT_PARAMETERS = "inputParameters";
	// Need to be a List<List<String>>
	// columns in the map must be in the following order:
	// parameterType
	// tableName
	// XPath
	// variableName
	public static final String OUTPUT_PARAMETERS = "outputParameters";
	public static final String HTML_OUTPUT = "htmlOutput";
	public static final String OUTPUT_RESULTS = "outputResults";

	private static final int EXPECTED_INPUT_COLUMN_NUMBER = 4;
	private static final List<String> INPUT_PARAMETER_TYPES = new ArrayList<String>();
	public static final String TABLE_INPUT = "table_input";
	public static final String INPUT_TABLE = "input_table";
	public static final String INPUT_STRUCTURE = "input_structure";
	public static final String INPUT_SINGLE = "input_single";

	private static final int EXPECTED_OUTPUT_COLUMN_NUMBER = 3;
	private static final List<String> OUTPUT_PARAMETER_TYPES = new ArrayList<String>();
	public static final String TABLE_OUTPUT = "table_output";
	public static final String OUTPUT_TABLE = "output_table";
	public static final String OUTPUT_STRUCTURE = "output_structure";
	public static final String OUTPUT_SINGLE = "output_single";

	static {
		INPUT_PARAMETER_TYPES.add(TABLE_INPUT);
		INPUT_PARAMETER_TYPES.add(INPUT_TABLE);
		INPUT_PARAMETER_TYPES.add(INPUT_STRUCTURE);
		INPUT_PARAMETER_TYPES.add(INPUT_SINGLE);

		OUTPUT_PARAMETER_TYPES.add(TABLE_OUTPUT);
		OUTPUT_PARAMETER_TYPES.add(OUTPUT_TABLE);
		OUTPUT_PARAMETER_TYPES.add(OUTPUT_STRUCTURE);
		OUTPUT_PARAMETER_TYPES.add(OUTPUT_SINGLE);
	}

	public enum SAPDestinationType {
		APPLICATION_SERVER, MESSAGE_SERVER
	}

	private JCoFunction function;
	private JCoDestination destination;
	private SAPMonoDestinationDataProvider destinationProvider;


	@Override
	public void executeBusinessLogic() throws ConnectorException {
		final JCoParameterList tableParameterList = fillJCOTableParameterList();
		callJCOFunction();
		final List<Serializable> results = fillBonitaResultWithJCOCallResult(tableParameterList);
		setOutputParameter(OUTPUT_RESULTS, results);
	}

	private void callJCOFunction() throws ConnectorException {
		try {
			destination = JCoDestinationManager.getDestination(destinationProvider.getDestinationName());
		} catch (JCoException e) {
			throw new ConnectorException("Failed to get destination", e);
		}
		try {
			function.execute(destination);
		} catch (JCoException e) {
			throw new ConnectorException("Failed to execute function", e);
		}
	}

	private List<Serializable> fillBonitaResultWithJCOCallResult(final JCoParameterList tableParameterList) {
		final List<Serializable> results = new ArrayList<>();
		@SuppressWarnings("unchecked")
		final List<List<String>> outputParameters = (List<List<String>>) getInputParameter(OUTPUT_PARAMETERS);
		if (outputParameters != null) {
			final JCoParameterList outputParameterList = this.function.getExportParameterList();
			for (List<String> row : outputParameters) {
				final String parameterType = row.get(0);
				final String tableName = row.get(1);
				final String xpath = row.get(2);

				if (xpath.length() > 0) {
					Serializable variableValue;
					if (OUTPUT_STRUCTURE.equals(parameterType)) {
						final JCoStructure structure = outputParameterList.getStructure(tableName);
						variableValue = structure.getString(xpath);
					} else if (OUTPUT_SINGLE.equals(parameterType)) {
						variableValue = outputParameterList.getString(xpath);
					} else {
						// OUTPUT_TABLE or TABLE_OUTPUT
						variableValue = retrieveTableVariableValue(tableParameterList, outputParameterList,
								parameterType, tableName, xpath);
					}
					results.add(variableValue);
				}
			}
		}
		return results;
	}

	private Serializable retrieveTableVariableValue(final JCoParameterList tableParameterList,
			final JCoParameterList outputParameterList, final String parameterType, final String tableName,
			final String xpath) {
		Serializable variableValue;
		JCoTable table = null;
		if (OUTPUT_TABLE.equals(parameterType)) {
			table = outputParameterList.getTable(tableName);
		} else if (TABLE_OUTPUT.equals(parameterType)) {
			table = tableParameterList.getTable(tableName);
		}
		ArrayList<String> list = new ArrayList<>();
		if (table != null) {
			boolean hasMoreElements = !table.isEmpty();
			while (hasMoreElements) {
				list.add(table.getString(xpath));
				if (table.isLastRow()) {
					hasMoreElements = false;
				} else {
					table.nextRow();
				}
			}
		}
		if (list.isEmpty()) {
			variableValue = null;
		} else if (list.size() == 1) {
			variableValue = list.get(0);
		} else {
			variableValue = list;
		}
		return variableValue;
	}

	@SuppressWarnings("unchecked")
	private JCoParameterList fillJCOTableParameterList() throws ConnectorException {
		final JCoParameterList tableParameterList = function.getTableParameterList();

		final List<List<Object>> inputParameters = (List<List<Object>>) getInputParameter(INPUT_PARAMETERS);
		if (inputParameters != null) {
			final JCoParameterList inputParameterList = function.getImportParameterList();
			for (List<Object> column : inputParameters) {
				final String parameterType = (String) column.get(0);
				final String tableName = (String) column.get(1);
				final String parameterName = (String) column.get(2);
				final Object parameterValue = column.get(3);

				if (INPUT_STRUCTURE.equals(parameterType)) {
					if (inputParameterList == null) {
						throw new ConnectorException("input_structure is not a supported input type for parameter '"
								+ parameterName + "' of table '" + tableName + "'");
					}
					final JCoStructure structure = inputParameterList.getStructure(tableName);
					if (structure == null) {
						throw new ConnectorException("input_structure is not a supported input type for parameter '"
								+ parameterName + "' of table '" + tableName + "'");
					}
					structure.setValue(parameterName, parameterValue);
				} else if (INPUT_SINGLE.equals(parameterType)) {
					if (inputParameterList == null) {
						throw new ConnectorException("input_single is not a supported input type for parameter '"
								+ parameterName + "' of table '" + tableName + "'");
					}
					inputParameterList.setValue(parameterName, parameterValue);
				} else {
					// INPUT_TABLE or TABLE_INPUT
					List<Object> list = new ArrayList<>();
					if (parameterValue instanceof List<?>) {
						list.addAll((List<Object>) parameterValue);
					} else {
						list.add(parameterValue);
					}
					int rowSize = list.size();
					JCoTable table = null;
					if (INPUT_TABLE.equals(parameterType)) {
						if (inputParameterList == null) {
							throw new ConnectorException("input_table is not a supported input type for parameter '"
									+ parameterName + "' of table '" + tableName + "'");
						}
						table = inputParameterList.getTable(tableName);
					} else if (TABLE_INPUT.equals(parameterType)) {
						if (tableParameterList == null) {
							throw new ConnectorException("table_input is not a supported input type for parameter '"
									+ parameterName + "' of table '" + tableName + "'");
						}
						table = tableParameterList.getTable(tableName);
					}
					if (table != null) {
						table.firstRow();
						for (int rowNb = 0; rowNb < rowSize; rowNb++) {
							if (table.getNumRows() < (rowNb + 1)) {
								table.appendRow();
							}
							table.setValue(parameterName, list.get(rowNb));
							table.nextRow();
						}
					}
				}
			}
		}
		return tableParameterList;
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		checkParameterIsStringAndIsNotNull(FUNCTION_NAME);
		checkParameterIsStringAndIsNotNull(CLIENT);
		checkParameterIsStringAndIsNotNull(USER);
		checkParameterIsStringAndIsNotNull(PASSWORD);
		checkParameterIsStringAndIsNotNull(LANGUAGE);
		checkParameterIsStringAndIsNotNull(HOST);

		final Object inputServerTypeParameterValue = getInputParameter(SERVER_TYPE);
		if (APPLICATION_SERVER_TYPE.equals(inputServerTypeParameterValue)) {
			checkIsNotNull(SYSTEM_NUMBER);
		} else if (MESSAGE_SERVER_TYPE.equals(inputServerTypeParameterValue)) {
			checkIsNotNull(GROUP_NAME);
			checkIsNotNull(SYSTEM_ID);
		}

		validateJCOInputParameter();
		validateJCOOutputPrameter();
	}

	private void validateJCOOutputPrameter() throws ConnectorValidationException {
		// check OUTPUT_PARAMETER_TYPES
		final Object outputParameter = getInputParameter(OUTPUT_PARAMETERS);
		if (outputParameter != null) {
			final Object functionOutputParameters = outputParameter;
			if (!(functionOutputParameters instanceof List<?>)) {
				throw new ConnectorValidationException("parameter: " + OUTPUT_PARAMETERS
						+ " is specified but is not an instance of " + List.class.getName() + ".");
			}
		}
		@SuppressWarnings("unchecked")
		List<List<String>> outputParameters = (List<List<String>>) outputParameter;
		if (outputParameters != null) {
			int outputRowNb = 1;
			for (List<String> row : outputParameters) {
				if (row.size() != EXPECTED_OUTPUT_COLUMN_NUMBER) {
					throw new ConnectorValidationException(
							"Row number: " + outputRowNb + " has a wrong number of columns: " + row.size()
									+ " (instead of " + EXPECTED_OUTPUT_COLUMN_NUMBER + ").");

				}
				if (!OUTPUT_PARAMETER_TYPES.contains(row.get(0))) {
					throw new ConnectorValidationException(
							"Row number: " + outputRowNb + " has a wrong value for parameterType: " + row.get(0)
									+ " (instead of " + OUTPUT_PARAMETER_TYPES + ").");
				}
				outputRowNb++;
			}
		}
	}

	private void validateJCOInputParameter() throws ConnectorValidationException {
		// check INPUT_PARAMETER_TYPES
		final Object inputParameter = getInputParameter(INPUT_PARAMETERS);
		if (inputParameter != null) {
			final Object functionInputParameters = inputParameter;
			if (!(functionInputParameters instanceof List<?>)) {
				throw new ConnectorValidationException("parameter: " + INPUT_PARAMETERS
						+ " is specified but is not an instance of " + List.class.getName() + ".");
			}
		}
		@SuppressWarnings("unchecked")
		List<List<Object>> inputParameters = (List<List<Object>>) inputParameter;
		if (inputParameters != null) {
			int inputRowNb = 1;
			for (List<Object> row : inputParameters) {
				if (row.size() != EXPECTED_INPUT_COLUMN_NUMBER) {
					throw new ConnectorValidationException(
							"Row number: " + inputRowNb + " has a wrong number of columns: " + row.size()
									+ " (instead of " + EXPECTED_INPUT_COLUMN_NUMBER + ").");
				}
				if (!INPUT_PARAMETER_TYPES.contains(row.get(0))) {
					throw new ConnectorValidationException(
							"Row number: " + inputRowNb + " has a wrong value for parameterType: " + row.get(0)
									+ " (instead of " + INPUT_PARAMETER_TYPES + ").");
				}
				inputRowNb++;
			}
		}
	}

	private String checkParameterIsStringAndIsNotNull(final String parameterName) throws ConnectorValidationException {
		final Object inputParameterValue = getInputParameter(parameterName);
		if (inputParameterValue == null) {
			throw new ConnectorValidationException(
					"Mandatory parameter: " + parameterName + " is specified but has a null value (forbidden).");
		}
		if (!(inputParameterValue instanceof String)) {
			throw new ConnectorValidationException("Mandatory parameter: " + parameterName
					+ " is specified but is not an instance of " + String.class.getName() + ".");
		}
		return (String) inputParameterValue;
	}

	private void checkIsNotNull(final String key) throws ConnectorValidationException {
		if (getInputParameter(key) == null) {
			throw new ConnectorValidationException(
					"Mandatory property: " + key + " is specified but has a null value (forbidden).");
		}
	}

	@Override
	public void connect() throws ConnectorException {

		final String destinationName = getInputParameter(DESTINATION_NAME)
				.toString() /* + UUID.randomUUID().toString() */;
		Properties destinationProperties = createDestinationDataProperties(destinationName);

		// maybe not necessary but to avoid several destinations to be the same, we
		// prefer to have one unique name... Maybe impact performances the wrong way, to
		// be improved
		try {
			destinationProvider = SAPMonoDestinationDataProvider.getInstance(destinationName);
		} catch (Exception e1) {
			throw new ConnectorException("Can't get a SAP Destination Provider", e1);
		}
		destinationProvider.changeProperties(destinationProperties);
		Environment.unregisterDestinationDataProvider(destinationProvider);
		Environment.registerDestinationDataProvider(destinationProvider);

		final JCoDestination destination = retrieveJCODestination(destinationName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("JCoDestination properties = " + destination.getProperties().toString());
		}

		final JCoRepository repository = retrieveJCORepository(destination);

		// get function
		final String functionName = (String) getInputParameter(FUNCTION_NAME);
		try {
			function = repository.getFunctionTemplate(functionName).getFunction();
		} catch (JCoException e) {
			throw new ConnectorException(e);
		}

		if (function == null) {
			throw new RuntimeException("Function " + functionName + " does not exist.");
		}

		JCoContext.begin(destination);
	}

	private JCoRepository retrieveJCORepository(final JCoDestination destination) throws ConnectorException {
		final JCoRepository repository;
		try {
			repository = destination.getRepository();
		} catch (JCoException e) {
			throw new ConnectorException(e);
		}
		return repository;
	}

	private JCoDestination retrieveJCODestination(final String destinationName) throws ConnectorException {
		try {
			return JCoDestinationManager.getDestination(destinationName);
		} catch (JCoException e) {
			throw new ConnectorException(e);
		}
	}

	Properties createDestinationDataProperties(final String destinationName) {
		final Properties destinationData = new Properties();
		destinationData.put(DestinationDataProvider.JCO_CLIENT, getInputParameter(CLIENT));
		destinationData.put(DestinationDataProvider.JCO_USER, getInputParameter(USER));
		destinationData.put(DestinationDataProvider.JCO_PASSWD, getInputParameter(PASSWORD));
		destinationData.put(DestinationDataProvider.JCO_LANG, getInputParameter(LANGUAGE));
		destinationData.put(DestinationDataProvider.JCO_DEST, destinationName);
		if (APPLICATION_SERVER_TYPE.equals(getInputParameter(SERVER_TYPE))) {
			destinationData.put(DestinationDataProvider.JCO_ASHOST, getInputParameter(HOST));
			destinationData.put(DestinationDataProvider.JCO_SYSNR, getInputParameter(SYSTEM_NUMBER));
		} else {
			destinationData.put(DestinationDataProvider.JCO_MSHOST, getInputParameter(HOST));
			destinationData.put(DestinationDataProvider.JCO_R3NAME, getInputParameter(SYSTEM_ID));
			destinationData.put(DestinationDataProvider.JCO_GROUP, getInputParameter(GROUP_NAME));
		}

		final List<List<?>> inputParameterDestinationData = (List<List<?>>) getInputParameter(DESTINATION_DATA);
		if (inputParameterDestinationData != null) {
			for (List<?> list : inputParameterDestinationData) {
				if (list.size() >= 2) {
					destinationData.put(list.get(0), list.get(1));
				}
			}
		}
		return destinationData;
	}

	@Override
	public void disconnect() throws ConnectorException {
		try {
			JCoContext.end(destination);
		} catch (JCoException e) {
			throw new ConnectorException(e);
		}
	}
}
