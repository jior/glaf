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

import java.util.HashMap;
import java.util.Map;

public class MailStatus {

	public static String NEW = "0"; // 新邮件

	public static String HAVE_READ = "1"; // 已读邮件

	public static String DRAFT = "2"; // 草稿

	public static String HAVE_SENT = "3"; // 已发邮件

	public static String SEND_FAILED = "4"; // 发送失败

	public static String RUBBISH = "5"; // 临时邮件

	public static String TEMP = "T"; // 临时邮件

	protected static Map names = new HashMap();

	static {
		names.put(NEW, "新邮件");
		names.put(HAVE_READ, "已读邮件");
		names.put(DRAFT, "草稿");
		names.put(HAVE_SENT, "已发邮件");
		names.put(SEND_FAILED, "发送失败");
		names.put(RUBBISH, "垃圾邮件");
		names.put(TEMP, "临时邮件");
	}

	private MailStatus() {
	}

	public static String getStatusDesc(String status) {
		String tReturn = null;
		if ((status == null) || (status.trim().equals(""))) {
			status = NEW; // 默认：新邮件
		}
		status = status.trim();
		Object name = names.get(status);
		// 默认：新邮件
		tReturn = (name == null) ? (names.get(NEW).toString()) : (name
				.toString());
		return tReturn;
	}
}