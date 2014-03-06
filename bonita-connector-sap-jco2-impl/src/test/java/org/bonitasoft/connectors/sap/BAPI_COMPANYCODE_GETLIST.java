package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.connectors.sap.bos5connector.SAPCallFunctionConnector;
import org.junit.Test;

public class BAPI_COMPANYCODE_GETLIST {
    private static boolean commitOnSuccess = true;
    private static final boolean rollbackOnFailure = true;

    @Test
    public void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();

        final List<List<String>> outputParameters = new ArrayList<List<String>>();

        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "COMPANYCODE_LIST", "COMP_CODE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "COMPANYCODE_LIST", "COMP_NAME");

        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "TYPE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "CODE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "MESSAGE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "LOG_NO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "LOG_MSG_NO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V2");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V3");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V4");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "RETURN", "");

        SAPUtil.callFunction(commitOnSuccess, rollbackOnFailure, "", "BAPI_COMPANYCODE_GETLIST", inputParameters, outputParameters);
    }

}
