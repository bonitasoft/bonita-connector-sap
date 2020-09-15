package com.sap.conn.jco;

import java.io.Serializable;

public interface JCoParameterList {

	JCoStructure getStructure(String tableName);

	Serializable getString(String xpath);

	JCoTable getTable(String tableName);

	void setValue(String parameterName, Object parameterValue);

}
