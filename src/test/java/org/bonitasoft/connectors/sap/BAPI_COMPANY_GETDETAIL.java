package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.connectors.sap.bos5connector.SAPCallFunctionConnector;
import org.junit.Test;

public class BAPI_COMPANY_GETDETAIL {

    private static boolean commitOnSuccess = true;
    private static final boolean rollbackOnFailure = true;

    @Test
    public void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.INPUT_SINGLE, "", "COMPANYID", "000001");

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COMPANY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "NAME1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "NAME2");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COUNTRY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "LANGU");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "STREET");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "PO_BOX");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "POSTL_COD1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CITY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CURRENCY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COUNTRY_ISO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CURRENCY_ISO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "LANGU_ISO");
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

        SAPUtil.callFunction(commitOnSuccess, rollbackOnFailure, "", "BAPI_COMPANY_GETDETAIL", inputParameters, outputParameters);
    }

}
