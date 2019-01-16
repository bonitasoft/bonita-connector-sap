package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.connectors.sap.bos5connector.SAPCallFunctionConnector;
import org.junit.Test;

public class BAPI_PO_GETITEMS {

    private static boolean commitOnSuccess = true;
    private static final boolean rollbackOnFailure = true;

    @Test
    public void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunctionConnector.INPUT_SINGLE, "", "PURCHASEORDER", "1234567890");

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunctionConnector.TABLE_OUTPUT, "PO_ITEMS", "");

        SAPUtil.callFunction(commitOnSuccess, rollbackOnFailure, "", "BAPI_PO_GETITEMS", inputParameters, outputParameters);
    }

}
