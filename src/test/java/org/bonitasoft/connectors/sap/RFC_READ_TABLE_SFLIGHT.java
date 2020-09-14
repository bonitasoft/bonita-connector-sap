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

class RFC_READ_TABLE_SFLIGHT {

    @Test
    void callFunction() throws Exception {

        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.INPUT_SINGLE, "", "DELIMITER", "|");
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.INPUT_SINGLE, "", "QUERY_TABLE", "SFLIGHT");
        SAPUtil.addInputRowow(inputParameters, SAPCallFunction.INPUT_SINGLE, "", "ROWCOUNT", "20");

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "DATA", "WA");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "FIELDS", "FIELDNAME");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "FIELDS", "OFFSET");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "FIELDS", "LENGTH");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "FIELDS", "TYPE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "FIELDS", "FIELDTEXT");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "OPTIONS", "TEXT");

        SAPUtil.callFunction("destination", "RFC_READ_TABLE", inputParameters, outputParameters);
    }

}
