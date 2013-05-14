package com.glaf.cms.webfile;

import java.util.Enumeration;
import java.util.Hashtable;

public class Request {

	private int m_counter;

	private Hashtable<String, Hashtable<Integer, String>> m_parameters;

	Request() {
		m_parameters = new Hashtable<String, Hashtable<Integer, String>>();
		m_counter = 0;
	}

	public int getCount() {
		return m_counter;
	}

	public String getParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"Form's name is invalid or does not exist (1305).");
		}
		Hashtable<?, ?> values = m_parameters.get(name);
		if (values == null) {
			return null;
		} else {
			return (String) values.get(new Integer(0));
		}
	}

	public Enumeration<String> getParameterNames() {
		return m_parameters.keys();
	}

	public String[] getParameterValues(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"Form's name is invalid or does not exist (1305).");
		}
		Hashtable<?, ?> values = m_parameters.get(name);
		if (values == null) {
			return null;
		}
		String strValues[] = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			strValues[i] = (String) values.get(new Integer(i));
		}

		return strValues;
	}

	protected void putParameter(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException(
					"The name of an element cannot be null.");
		}
		if (m_parameters.containsKey(name)) {
			Hashtable<Integer, String> values = m_parameters.get(name);
			values.put(new Integer(values.size()), value);
		} else {
			Hashtable<Integer, String> values = new Hashtable<Integer, String>();
			values.put(new Integer(0), value);
			m_parameters.put(name, values);
			m_counter++;
		}
	}
}
