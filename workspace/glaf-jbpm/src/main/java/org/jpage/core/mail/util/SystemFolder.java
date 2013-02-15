/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
