package com.bonitasoft.engine.connector;

import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;

/**
 * 
 * Mock to remove when bonita-common 7.12 is available on public repo
 *
 */
public class SAPMonoDestinationDataProvider implements DestinationDataProvider {

	public String getDestinationName() {
	    return null;
	}

	public static SAPMonoDestinationDataProvider getInstance(String destinationName) {
		return null;
	}

	public void changeProperties(Properties destinationProperties) {
	    
	}

}
