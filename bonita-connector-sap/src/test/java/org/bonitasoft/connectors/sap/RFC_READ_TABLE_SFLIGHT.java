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
