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

public class MailStatus {

	public final static int NEW = 0; // ÐÂÓÊ¼þ

	public final static int HAS_READ = 1; // ÒÑ¶ÁÓÊ¼þ

	public final static int DRAFT = 2; // ²Ý¸å

	public final static int HAS_SENT = 3; // ÒÑ·¢ÓÊ¼þ

	public final static int SEND_FAILED = 4; // ·¢ËÍÊ§°Ü

	public final static int RUBBISH = 5; // ·ÏÆúÓÊ¼þ

	protected static Map<Integer, String> names = new java.util.concurrent.ConcurrentHashMap<Integer, String>();

	static {
		names.put(NEW, "ÐÂÓÊ¼þ");
		names.put(HAS_READ, "ÒÑ¶ÁÓÊ¼þ");
		names.put(DRAFT, "²Ý¸å");
		names.put(HAS_SENT, "ÒÑ·¢ÓÊ¼þ");
		names.put(SEND_FAILED, "·¢ËÍÊ§°Ü");
		names.put(RUBBISH, "·ÏÆúÓÊ¼þ");
	}

	private MailStatus() {
	}

}