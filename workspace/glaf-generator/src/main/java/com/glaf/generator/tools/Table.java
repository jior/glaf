package com.glaf.generator.tools;

import java.util.*;

public class Table implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String tablename = null;

	private List<Column> columns = new ArrayList<Column>();

	public Table() {

	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
