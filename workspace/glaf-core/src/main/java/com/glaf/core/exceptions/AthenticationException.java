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

package com.glaf.core.exceptions;

public class AthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errorCode = 0;

	protected Throwable cause;

	public AthenticationException() {
		super();
	}

	public AthenticationException(String message) {
		super(message);
	}

	public AthenticationException(Throwable cause) {
		super(cause);
		this.cause = cause;
	}

	public AthenticationException(String message, Throwable cause) {
		super(message, cause);
		this.cause = cause;
	}

	public AthenticationException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public AthenticationException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public AthenticationException(Throwable cause, int errorCode) {
		super(cause);
		this.cause = cause;
		this.errorCode = errorCode;
	}

	public AthenticationException(String message, Throwable cause, int errorCode) {
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