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
	 * ����Ϣ
	 */
	public final static int NEW = 0;

	/**
	 * �Ѿ��Ķ�����Ϣ
	 */
	public final static int HAS_READ = 1;

	/**
	 * �Ѿ�����
	 */
	public final static int HAS_SENT = 2;
	
	/**
	 * ���������з����ʼ���Ϣ
	 */
	public final static int PROCESS_MAIL_MESSAGE = 2000;

	/**
	 * ���������е���Ϣ״̬
	 */
	public final static int PROCESS_RUNNING_STATUS = 5000;

	/**
	 * ��������ɵ���Ϣ״̬
	 */
	public final static int PROCESS_FINISHED_STATUS = 10000;

	/**
	 * �����ʼ�
	 */
	public final static String MAIL_MESSAGE = "mail";

	/**
	 * �ֻ�����
	 */
	public final static String SMS_MESSAGE = "sms";

	/**
	 * ϵͳ����Ϣ
	 */
	public final static String SYSTEM_MESSAGE = "system";

	/**
	 * ��ʱ��Ϣ
	 */
	public final static String IM_MESSAGE = "im";

	/**
	 * ϵͳĬ�ϵ�
	 */
	public final static String DEFAULT_MESSAGE = "default";

}
