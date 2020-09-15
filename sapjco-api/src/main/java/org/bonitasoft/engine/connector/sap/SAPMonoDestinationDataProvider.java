package org.bonitasoft.engine.connector.sap;

import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;

/**
 * 
 * Mock to remove when bonita-common 7.12 is available on public repo
 *
 */
public interface SAPMonoDestinationDataProvider extends DestinationDataProvider {

	String getDestinationName();

	static SAPMonoDestinationDataProvider getInstance(String destinationName) {
		return null;
	}

	void changeProperties(Properties destinationProperties);

}
