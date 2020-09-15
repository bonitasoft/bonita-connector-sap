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
