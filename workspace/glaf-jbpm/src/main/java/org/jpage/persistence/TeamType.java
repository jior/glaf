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

package org.jpage.persistence;

public class TeamType {

	/**
	 * ���Լ�
	 */
	public final static String X_TYPE = "x";

	/**
	 * �����û�(���������û���ֱ�ӹ�����û����������Լ�)
	 */
	public final static String YZ_TYPE = "yz";

	/**
	 * �����û�(���������û���ֱ�ӹ�����û�)���Լ�
	 */
	public final static String XYZ_TYPE = "xyz";

	/**
	 * ���������û�
	 */
	public final static String Y_TYPE = "y";

	/**
	 * ����ֱ�ӹ�����û�ͬʱ�����Լ�
	 */
	public final static String XZ_TYPE = "xz";

	/**
	 * ����ֱ�ӹ�����û����������Լ�
	 */
	public final static String Z_TYPE = "z";

	public static String getTeamTypeScript(String elementId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<select id=\"").append(elementId).append("\" name=\"")
				.append(elementId).append("\">\n");
		buffer.append("    <option value=\"\" selected>----��ѡ��----</option>\n");
		buffer.append("    <option value=\"x\">����</option>\n");
		buffer.append("    <option value=\"z\">ֱ�ӹ�����Ŷӳ�Ա</option>\n");
		buffer.append("    <option value=\"yz\">�����Ŷӳ�Ա���������Լ�</option>\n");
		buffer.append("    <option value=\"xyz\">�����Ŷӳ�Ա���Լ�</option>\n");
		buffer.append("</select>");
		return buffer.toString();
	}

}
