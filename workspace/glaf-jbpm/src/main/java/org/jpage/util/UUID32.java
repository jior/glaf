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

package org.jpage.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UUID32 {
	private StringBuffer uuid = null;

	private static Random r = new Random();

	public UUID32() {
		uuid = new StringBuffer();
		uuid.append(genKey(8));
		uuid.append(genKey(4));
		uuid.append(genKey(4));
		uuid.append(genKey(4));
		uuid.append(genKey(12));
	}

	private String genKey(int j) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < j; i++) {
			byte b = (byte) (Math.abs(r.nextInt()) % 16);
			char c = (char) ((b >= 10) ? (97 + b) : (48 + b));
			buff.append(c);
		}
		return buff.toString();
	}

	public String toString() {
		return uuid.toString();
	}

	public static String getUUID() {
		UUID32 uuid32 = new UUID32();
		return uuid32.toString();
	}

	public static void main(String[] args) {
		Map map = new HashMap();
		for (int i = 0; i < 1000 * 200; i++) {
			String id = UUID32.getUUID();
			map.put(id, id);
		}
		System.out.println(map.size());

	}
}