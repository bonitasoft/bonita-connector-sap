package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.connectors.sap.bos5connector.SAPCallFunctionConnector;
import org.junit.Test;

public class RFC_READ_TABLE_SFLIGHT {

    private static boolean commitOnSuccess = true;
    private static final boolean rollbackOnFailure = true;

    @Test
    public void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.INPUT_SINGLE, "", "DELIMITER", "|");
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.INPUT_SINGLE, "", "QUERY_TABLE", "SFLIGHT");
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.INPUT_SINGLE, "", "ROWCOUNT", "20");

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "DATA", "WA");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "FIELDS", "FIELDNAME");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "FIELDS", "OFFSET");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "FIELDS", "LENGTH");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "FIELDS", "TYPE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "FIELDS", "FIELDTEXT");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "OPTIONS", "TEXT");

        SAPUtil.callFunction(commitOnSuccess, rollbackOnFailure, "", "RFC_READ_TABLE", inputParameters, outputParameters);
    }

}
