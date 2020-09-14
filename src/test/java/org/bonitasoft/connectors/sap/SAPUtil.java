/*******************************************************************************
 * Copyright (C) 2013 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 *      BonitaSoft, 32 rue Gustave Eiffel 38000 Grenoble
 *      or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
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
