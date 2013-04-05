
/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.glaf.form;

public class FormException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errorCode = 0;

	protected Throwable cause;

	public FormException() {
		super();
	}

	public FormException(String message) {
		super(message);
	}

	public FormException(Throwable cause) {
		super(cause);
		this.cause = cause;
	}

	public FormException(String message, Throwable cause) {
		super(message, cause);
		this.cause = cause;
	}

	public FormException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public FormException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public FormException(Throwable cause, int errorCode) {
		super(cause);
		this.cause = cause;
		this.errorCode = errorCode;
	}

	public FormException(String message, Throwable cause, int errorCode) {
		super(message, cause);
		this.cause = cause;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}