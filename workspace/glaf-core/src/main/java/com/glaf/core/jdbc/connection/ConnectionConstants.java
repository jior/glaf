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
package com.glaf.core.jdbc.connection;

public final class ConnectionConstants {

	public static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";

	public static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
	public static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
	public static final String PROP_DEFAULTCATALOG = "defaultCatalog";
	public static final String PROP_MAXACTIVE = "maxActive";
	public static final String PROP_MINACTIVE = "minActive";
	public static final String PROP_MAXIDLE = "maxIdle";
	public static final String PROP_MINIDLE = "minIdle";
	public static final String PROP_INITIALSIZE = "initialSize";
	public static final String PROP_MAXWAIT = "maxWait";
	public static final String PROP_MAXAGE = "maxAge";
	public static final String PROP_MAXSTATEMENTS = "maxStatements";
	public static final String PROP_TESTONBORROW = "testOnBorrow";

	public static final String PROP_TESTONRETURN = "testOnReturn";
	public static final String PROP_TESTWHILEIDLE = "testWhileIdle";
	public static final String PROP_TESTONCONNECT = "testOnConnect";
	public static final String PROP_VALIDATIONQUERY = "validationQuery";
	public static final String PROP_VALIDATOR_CLASS_NAME = "validatorClassName";
	public static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";

	public static final String PROP_TIMEBETWEENEVICTIONRUNS = "timeBetweenEvictionRuns";
	public static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
	public static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
	public static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";

	public static final String PROP_REMOVEABANDONED = "removeAbandoned";

	public static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
	public static final String PROP_LOGABANDONED = "logAbandoned";
	public static final String PROP_ABANDONWHENPERCENTAGEFULL = "abandonWhenPercentageFull";
	public static final String PROP_INITSQL = "initSQL";

	public static final String PROP_INTERCEPTORS = "jdbcInterceptors";
	public static final String PROP_VALIDATIONINTERVAL = "validationInterval";
	public static final String PROP_JMX_ENABLED = "jmxEnabled";
	public static final String PROP_FAIR_QUEUE = "fairQueue";
	public static final String PROP_USE_EQUALS = "useEquals";

	public static final String PROP_USE_CON_LOCK = "useLock";
	public static final String PROP_SUSPECT_TIMEOUT = "suspectTimeout";

	public static final String PROP_ALTERNATE_USERNAME_ALLOWED = "alternateUsernameAllowed";

	public static final int UNKNOWN_TRANSACTIONISOLATION = -1;

	private ConnectionConstants() {

	}

}
