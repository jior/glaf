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

package org.jpage.core.mail.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemFolder {

	public final static String INBOX = "0001"; // ÊÕ¼þÏä

	public final static String OUTBOX = "0002"; // ·¢¼þÏä

	public final static String HAVE_SENT = "0003"; // ÒÑ·¢ËÍÓÊ¼þÏä

	public final static String DRAFT_BOX = "0004"; // ²Ý¸åÏä

	public final static String RUBBISH_BOX = "0005"; // ·Ï¼þÏä

	private static Map names = new LinkedHashMap();

	private static Map folderMap = new LinkedHashMap();

	private static Map folderTypeMap = new LinkedHashMap();

	private static Map icons = new LinkedHashMap();

	static {

		names.put(INBOX, "ÊÕ¼þÏä");
		names.put(OUTBOX, "·¢¼þÏä");
		names.put(HAVE_SENT, "ÒÑ·¢ËÍÓÊ¼þÏä");
		names.put(DRAFT_BOX, "²Ý¸åÏä");
		names.put(RUBBISH_BOX, "·Ï¼þÏä");

		icons.put(INBOX, "images/inbox.gif");
		icons.put(OUTBOX, "images/outbox.gif");
		icons.put(HAVE_SENT, "images/send.gif");
		icons.put(DRAFT_BOX, "images/draft.gif");
		icons.put(RUBBISH_BOX, "images/trash.gif");

		folderMap.put(INBOX, INBOX);
		folderMap.put(OUTBOX, OUTBOX);
		folderMap.put(HAVE_SENT, HAVE_SENT);
		folderMap.put(DRAFT_BOX, DRAFT_BOX);
		folderMap.put(RUBBISH_BOX, RUBBISH_BOX);

	}

	private SystemFolder() {
	}

	public static Map getSystemFolderMap() {
		return folderMap;
	}

	public static String getSystemFolderType(String key) {
		if (key == null) {
			return null;
		}
		return (String) folderTypeMap.get(key);
	}

	public static String getIcon(String type) {
		String tReturn = null;
		if (type == null) {
			type = INBOX;
		}
		tReturn = (String) icons.get(type);
		return tReturn;
	}

	public static String getName(String mailbox) {
		String tReturn = null;
		if (mailbox == null) {
			mailbox = INBOX;
		}
		tReturn = (String) names.get(mailbox);
		return tReturn;
	}

}
