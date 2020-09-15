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

class RFC_SYSTEM_INFO {
   
    @Test
    void callFunction() throws Exception {
        final List<List<Object>> inputParameters = new ArrayList<List<Object>>();

        final List<List<String>> outputParameters = new ArrayList<List<String>>();
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCPROTO");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCCHARTYP");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCINTTYP");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCFLOTYP");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCDEST");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCHOST");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCSYSID");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCDATABS");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCDBHOST");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCDBSYS");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCSAPRL");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCMACH");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCOPSYS");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCTZONE");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCDAYST");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCIPADDR");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCKERNRL");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCHOST2");
        SAPUtil.addOutputRow(outputParameters, SAPCallFunction.OUTPUT_STRUCTURE, "RFCSI_EXPORT", "RFCSI_RESV");//return may be empty for this one

        SAPUtil.callFunction("destination", "RFC_SYSTEM_INFO", inputParameters, outputParameters);
    }

}
