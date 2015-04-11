package com.glaf.chart.export.controller;

public class ZeroRequestParameterException extends Exception {

	private static final long serialVersionUID = -5110552374074051446L;
	private String mistake;

	public ZeroRequestParameterException() {
		super();
		mistake = "unknown to men";
	}

	public ZeroRequestParameterException(String err) {
		super(err);
		mistake = err;
	}

	public String getError() {
		return mistake;
	}

}
