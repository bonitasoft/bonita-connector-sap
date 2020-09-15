package com.sap.conn.jco;

import java.io.Serializable;

public interface JCoStructure {

	 Serializable getString(String xpath);

	void setValue(String parameterName, Object parameterValue);


}
