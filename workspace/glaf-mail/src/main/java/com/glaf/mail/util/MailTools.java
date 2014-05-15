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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailTools {
	

	public static boolean isMailAddress(String mail) {
		if (mail == null) {
			return false;
		}
		if (mail.length() < 6 || mail.indexOf("@") == -1) {
			return false;
		}
		boolean flag = true;
		final String pattern1 = "^[0-9a-z][a-z0-9\\._-]{1,}@[a-z0-9-]{1,}[a-z0-9]\\.[a-z\\.]{1,}[a-z]$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(mail);
		if (!mat.find()) {
			flag = false;
		}
		return flag;
	}

	public static String chineseStringToAscii(String str) {
		if (str == null) {
			return "";
		}
		try {
			byte[] source = str.getBytes();
			char[] dest = new char[source.length];
			for (int i = 0; i < source.length; i++) {
				dest[i] = (char) (source[i] & 0xFF);
			}
			return new String(dest);
		} catch (Exception ex) {
			return str;
		}
	}

	public static void main(String[] args) {
		System.out.println(MailTools.isMailAddress("joy@127.0.0.1"));
		System.out.println(MailTools.isMailAddress("g@g.cn"));
		System.out.println(MailTools.isMailAddress("joy@gmail.com"));
		System.out.println(MailTools.isMailAddress("139@gmail.com"));
	}

}