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

public interface MailType {

	public final static String TEXT_PLAIN = "text/plain"; // ƽ��

	public final static String TEXT_HTML = "text/html"; // ��ҳ

	public final static String MULTIPART = "multipart/*"; // �������

	public final static String UNPROCESSED = "unprocessed"; // �ʼ�����δ�����(ֱ�Ӵ�James�ʼ����������յ�δ�����)

	public final static String DRAFT = "draft"; // �ݸ�

	public final static String HAVE_SEND = "haveSent"; // �Ѿ����͵��ʼ�

	public final static String SEND_FAILED = "sentFailed"; // ����ʧ�ܵ��ʼ�

}