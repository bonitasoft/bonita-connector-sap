package com.sap.conn.jco;

public interface JCoTable {

	boolean isEmpty();

	String getString(String xpath);

	boolean isLastRow();

	void nextRow();

	int getNumRows();

	void firstRow();

	void appendRow();

	void setValue(String parameterName, Object object);

}
