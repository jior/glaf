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

package org.jpage.jbpm.util;

public class MessageType {

	/**
	 * 新消息
	 */
	public final static int NEW = 0;

	/**
	 * 已经阅读的消息
	 */
	public final static int HAS_READ = 1;

	/**
	 * 已经发送
	 */
	public final static int HAS_SENT = 2;
	
	/**
	 * 流程运行中发送邮件信息
	 */
	public final static int PROCESS_MAIL_MESSAGE = 2000;

	/**
	 * 流程运行中的消息状态
	 */
	public final static int PROCESS_RUNNING_STATUS = 5000;

	/**
	 * 流程已完成的消息状态
	 */
	public final static int PROCESS_FINISHED_STATUS = 10000;

	/**
	 * 电子邮件
	 */
	public final static String MAIL_MESSAGE = "mail";

	/**
	 * 手机短信
	 */
	public final static String SMS_MESSAGE = "sms";

	/**
	 * 系统短消息
	 */
	public final static String SYSTEM_MESSAGE = "system";

	/**
	 * 即时信息
	 */
	public final static String IM_MESSAGE = "im";

	/**
	 * 系统默认的
	 */
	public final static String DEFAULT_MESSAGE = "default";

}
