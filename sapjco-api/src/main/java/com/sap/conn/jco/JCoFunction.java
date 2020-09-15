package com.sap.conn.jco;

public interface JCoFunction {

	JCoParameterList getExportParameterList();

	void execute(JCoDestination destination) throws JCoException;

	JCoParameterList getTableParameterList();

	JCoParameterList getImportParameterList();

}
