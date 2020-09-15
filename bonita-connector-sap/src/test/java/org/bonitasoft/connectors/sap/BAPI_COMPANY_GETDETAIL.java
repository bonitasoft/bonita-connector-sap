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


class BAPI_COMPANY_GETDETAIL {

    @Test
    void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.INPUT_SINGLE, "", "COMPANYID", "000001");

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COMPANY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "NAME1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "NAME2");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COUNTRY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "LANGU");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "STREET");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "PO_BOX");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "POSTL_COD1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CITY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CURRENCY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "COUNTRY_ISO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "CURRENCY_ISO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "COMPANY_DETAIL", "LANGU_ISO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "TYPE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "CODE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "MESSAGE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "LOG_NO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "LOG_MSG_NO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V1");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V2");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V3");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "MESSAGE_V4");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RETURN", "");

        SAPUtil.callFunction("destination", "BAPI_COMPANY_GETDETAIL", inputParameters, outputParameters);
    }

}
