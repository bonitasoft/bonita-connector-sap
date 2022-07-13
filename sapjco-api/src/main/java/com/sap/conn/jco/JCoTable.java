package com.sap.conn.jco;

public interface JCoTable extends JCoRecord {

	boolean isEmpty();

	String getString(String xpath);

	boolean isLastRow();

	boolean nextRow();

	int getNumRows();

	void firstRow();

	void appendRow();

	void setValue(String parameterName, Object object);

}
