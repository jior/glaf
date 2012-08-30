package com.glaf.base.exception;

public class CanNotBeNullException extends Exception {
 
	private static final long serialVersionUID = 1L;

	public CanNotBeNullException(){
		super();
	}
	
	public CanNotBeNullException(String msg){
		super(msg);
	}
}
