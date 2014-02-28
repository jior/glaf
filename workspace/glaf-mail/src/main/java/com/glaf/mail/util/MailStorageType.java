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

package com.glaf.mail.util;

import java.util.*;

public class MailStorageType {
	
	public final static String RDBMS_TYPE = "0";

	public final static String MONGODB_TYPE = "1";

	public final static String CASSANDRA_TYPE = "2";

	public final static String HBASE_TYPE = "3";

	protected static Map<String, String> names = new java.util.concurrent.ConcurrentHashMap<String, String>();
	
	static {
		names.put(RDBMS_TYPE, "rdbms");
		names.put(MONGODB_TYPE, "mogodb");
		names.put(CASSANDRA_TYPE, "cassandra");
		names.put(HBASE_TYPE, "hbase");
	}
	
	public static String getTypeName(String type){
		return names.get(type);
	}

}