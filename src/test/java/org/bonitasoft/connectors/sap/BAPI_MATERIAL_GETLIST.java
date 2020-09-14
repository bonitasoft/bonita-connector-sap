/*******************************************************************************
 * Copyright (C) 2013 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 *      BonitaSoft, 32 rue Gustave Eiffel 38000 Grenoble
 *      or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package org.bonitasoft.connectors.sap;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BAPI_MATERIAL_GETLIST {

    @Test
    void callFunction() throws Exception {
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
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.TABLE_INPUT, "MATNRSELECTION", "SIGN", signValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.TABLE_INPUT, "MATNRSELECTION", "OPTION", optionsValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.TABLE_INPUT, "MATNRSELECTION", "MATNR_LOW", matnrLowValues);
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.TABLE_INPUT, "MATNRSELECTION", "MATNR_HIGH", matnrHighValues);

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "RETURN", "MESSAGE");

        SAPUtil.callFunction("destination", "BAPI_MATERIAL_GETLIST", inputParameters, outputParameters);
    }
}
