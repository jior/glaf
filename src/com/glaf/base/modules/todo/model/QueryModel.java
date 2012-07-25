package com.glaf.base.modules.todo.model;

import java.util.*;

public class QueryModel implements java.io.Serializable {

	private static final long serialVersionUID = -6755721626335022203L;

	private String sql;

	private List values;

	public QueryModel() {

	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List getValues() {
		return values;
	}

	public void setValues(List values) {
		this.values = values;
	}

}
