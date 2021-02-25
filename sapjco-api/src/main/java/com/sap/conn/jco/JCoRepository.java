package com.sap.conn.jco;

public interface JCoRepository {

	JCoFunctionTemplate getFunctionTemplate(String functionName) throws JCoException;
	
	JCoFunction getFunction(String functionName) throws JCoException;

}
