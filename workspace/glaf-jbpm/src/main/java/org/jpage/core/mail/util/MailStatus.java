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

	public static String NEW = "0"; // ���ʼ�

	public static String HAVE_READ = "1"; // �Ѷ��ʼ�

	public static String DRAFT = "2"; // �ݸ�

	public static String HAVE_SENT = "3"; // �ѷ��ʼ�

	public static String SEND_FAILED = "4"; // ����ʧ��

	public static String RUBBISH = "5"; // ��ʱ�ʼ�

	public static String TEMP = "T"; // ��ʱ�ʼ�

	protected static Map names = new HashMap();

	static {
		names.put(NEW, "���ʼ�");
		names.put(HAVE_READ, "�Ѷ��ʼ�");
		names.put(DRAFT, "�ݸ�");
		names.put(HAVE_SENT, "�ѷ��ʼ�");
		names.put(SEND_FAILED, "����ʧ��");
		names.put(RUBBISH, "�����ʼ�");
		names.put(TEMP, "��ʱ�ʼ�");
	}

	private MailStatus() {
	}

	public static String getStatusDesc(String status) {
		String tReturn = null;
		if ((status == null) || (status.trim().equals(""))) {
			status = NEW; // Ĭ�ϣ����ʼ�
		}
		status = status.trim();
		Object name = names.get(status);
		// Ĭ�ϣ����ʼ�
		tReturn = (name == null) ? (names.get(NEW).toString()) : (name
				.toString());
		return tReturn;
	}
}