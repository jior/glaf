package com.glaf.base.modules.sys.interceptor;

import javax.servlet.ServletException;

public class AuthorizeException extends ServletException{
	private static final long serialVersionUID = -6902385387621115631L;

	public AuthorizeException() {
		super();
	}
	
	public AuthorizeException(String msg) {
		super(msg);
	}
}
