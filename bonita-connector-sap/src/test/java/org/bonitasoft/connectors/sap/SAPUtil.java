/*
 * Copyright (C) 2009 - 2020 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.bonitasoft.connectors.sap;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPUtil {

    static {
        final URL sapjcoURL = SAPUtil.class.getResource("sapjco3.dll");
        if (sapjcoURL != null) {
            final File sapjco = new File(sapjcoURL.getFile());
            final String key = "java.library.path";
            final String value = System.getProperty(key) + ":" + sapjco.getParent();
            System.err.println("Setting " + key + " to " + value);
            System.setProperty(key, value);
        }
    }
    private SAPUtil() { }

    public static void addInputRowow(final List<List<Object>> inputParameters, final String parameterType, final String tableName, final String parameterName, final List<Object> parameterValue) {
        final List<Object> row = new ArrayList<Object>();
        row.add(parameterType);row.add(tableName);row.add(parameterName);row.add(parameterValue);
        inputParameters.add(row);
    }

    public static void addInputRowow(final List<List<Object>> inputParameters, final String parameterType, final String tableName, final String parameterName, final String parameterValue) {
        final List<Object> row = new ArrayList<Object>(4);
        row.add(parameterType);row.add(tableName);row.add(parameterName);row.add(parameterValue);
        inputParameters.add(row);
    }

    public static void addOutputRow(final List<List<String>> outputParameters, final String parameterType, final String tableName, final String xpath) {
        final List<String> row = new ArrayList<String>(3);
        row.add(parameterType);row.add(tableName);row.add(xpath);
        outputParameters.add(row);
    }

    public static Map<String, Object> callFunction(
            final String destinationName, 
            final String functionName,
            final List<List<Object>> inputParameters, 
            final List<List<String>> outputParameters
            ) throws Exception {

        System.err.println("[" + functionName + "] Starting callFunction... ");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SAPCallFunction.FUNCTION_NAME, functionName);
        parameters.put(SAPCallFunction.DESTINATION_NAME, destinationName);
        parameters.put(SAPCallFunction.INPUT_PARAMETERS, inputParameters);
        parameters.put(SAPCallFunction.OUTPUT_PARAMETERS, outputParameters);


        final SAPCallFunction connector = new SAPCallFunction();
        parameters.put(SAPCallFunction.CLIENT, "000");
        parameters.put(SAPCallFunction.USER, "TALEND");
        parameters.put(SAPCallFunction.PASSWORD, "FRANCE");
        parameters.put(SAPCallFunction.LANGUAGE, "EN");
        parameters.put(SAPCallFunction.HOST, "192.168.0.58");
        parameters.put(SAPCallFunction.SERVER_TYPE, "ApplicationServer");
        parameters.put(SAPCallFunction.SYSTEM_NUMBER, "00");
        //TODO put it with additional data?
        parameters.put(DestinationDataProvider.JCO_TRACE, "1");

        connector.setInputParameters(parameters);
        connector.validateInputParameters();
        
        try {
            // connect to SAP
            connector.connect();
            System.err.println("[" + functionName + "] Ending callFunction...");
            return connector.execute();
        } finally {
            connector.disconnect();
        }
    }

}
