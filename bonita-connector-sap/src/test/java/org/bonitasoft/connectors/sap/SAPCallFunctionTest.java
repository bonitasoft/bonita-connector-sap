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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import com.sap.conn.jco.ext.DestinationDataProvider;

class SAPCallFunctionTest {

	@Test
	void should_assign_proper_host_property_with_as_type() throws Exception {
		SAPCallFunction sapCallFunction = new SAPCallFunction();
		sapCallFunction.setInputParameters(
				inputParameters("TestDestination", SAPCallFunction.APPLICATION_SERVER_TYPE, "myHost"));

		Properties properties = sapCallFunction.createDestinationDataProperties("TestDestination");

		assertEquals(properties.get(DestinationDataProvider.JCO_ASHOST), "myHost");
	}

	@Test
	void should_assign_proper_host_property_with_messageserver_type() throws Exception {
		SAPCallFunction sapCallFunction = new SAPCallFunction();
		sapCallFunction
				.setInputParameters(inputParameters("TestDestination", SAPCallFunction.MESSAGE_SERVER_TYPE, "myHost"));

		Properties properties = sapCallFunction.createDestinationDataProperties("TestDestination");

		assertEquals(properties.get(DestinationDataProvider.JCO_MSHOST), "myHost");
	}

	private Map<String, Object> inputParameters(String destinationName, String serverType, String host) {
		Map<String, Object> input = new HashMap<String, Object>();
		input.put(SAPCallFunction.CLIENT, "");
		input.put(SAPCallFunction.USER, "");
		input.put(SAPCallFunction.PASSWORD, "");
		input.put(SAPCallFunction.LANGUAGE, "");
		input.put(SAPCallFunction.SYSTEM_NUMBER, "");
		input.put(SAPCallFunction.SYSTEM_ID, "");
		input.put(SAPCallFunction.GROUP_NAME, "");
		input.put(SAPCallFunction.DESTINATION_DATA, new ArrayList<Object>());
		input.put(SAPCallFunction.DESTINATION_NAME, destinationName);
		input.put(SAPCallFunction.SERVER_TYPE, serverType);
		input.put(SAPCallFunction.HOST, host);
		return input;
	}
}
