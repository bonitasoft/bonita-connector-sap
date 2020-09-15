package com.sap.conn.jco;

public interface JCoDestination {

	Object getProperties();

	JCoRepository getRepository() throws JCoException;

}
