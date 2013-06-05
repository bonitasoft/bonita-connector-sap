package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.connectors.sap.bos5connector.SAPCallFunctionConnector;
import org.junit.Test;

public class BAPI_MATERIAL_GETLIST {

    private static boolean commitOnSuccess = true;
    private static final boolean rollbackOnFailure = true;

    @Test
    public void callFunction() throws Exception {
        List<Object> signValues = new ArrayList<Object>();
        signValues.add(String.valueOf("I"));
        signValues.add(String.valueOf("I"));
        List<Object> optionsValues = new ArrayList<Object>();
        optionsValues.add(String.valueOf("EQ"));
        optionsValues.add(String.valueOf("EQ"));
        List<Object> matnrLowValues = new ArrayList<Object>();
        matnrLowValues.add(String.valueOf("P1001087"));
        matnrLowValues.add(String.valueOf("P1001089"));
        List<Object> matnrHighValues = new ArrayList<Object>();
        matnrHighValues.add(String.valueOf(""));
        matnrHighValues.add(String.valueOf(""));

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.TABLE_INPUT, "MATNRSELECTION", "SIGN", signValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.TABLE_INPUT, "MATNRSELECTION", "OPTION", optionsValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.TABLE_INPUT, "MATNRSELECTION", "MATNR_LOW", matnrLowValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.TABLE_INPUT, "MATNRSELECTION", "MATNR_HIGH", matnrHighValues);

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "RETURN", "MESSAGE");

        SAPUtil.callFunction(commitOnSuccess, rollbackOnFailure, "", "BAPI_MATERIAL_GETLIST", inputParameters, outputParameters);
    }
}
