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

	public final static String TEXT_PLAIN = "text/plain"; // 平信

	public final static String TEXT_HTML = "text/html"; // 网页

	public final static String MULTIPART = "multipart/*"; // 混合类型

	public final static String UNPROCESSED = "unprocessed"; // 邮件体是未处理的(直接从James邮件服务器接收但未处理的)

	public final static String DRAFT = "draft"; // 草稿

	public final static String HAVE_SEND = "haveSent"; // 已经发送的邮件

	public final static String SEND_FAILED = "sentFailed"; // 发送失败的邮件

}