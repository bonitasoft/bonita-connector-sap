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


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BAPI_COMPANY_GETLIST {

    public static final String FUNCTION_NAME = "BAPI_COMPANY_GETLIST";

    @Test
    void testCallFunction() throws Exception {
        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "COMPANY_LIST", "COMPANY");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.TABLE_OUTPUT, "COMPANY_LIST", "NAME1");
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

        SAPUtil.callFunction("destination", FUNCTION_NAME, inputParameters, outputParameters);

    }

}
