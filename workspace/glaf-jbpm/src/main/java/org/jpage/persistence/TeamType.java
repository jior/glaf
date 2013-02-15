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
	 * 他自己
	 */
	public final static String X_TYPE = "x";

	/**
	 * 下属用户(包含下属用户和直接管理的用户但不包含自己)
	 */
	public final static String YZ_TYPE = "yz";

	/**
	 * 下属用户(包含下属用户和直接管理的用户)及自己
	 */
	public final static String XYZ_TYPE = "xyz";

	/**
	 * 下属销售用户
	 */
	public final static String Y_TYPE = "y";

	/**
	 * 经理直接管理的用户同时包含自己
	 */
	public final static String XZ_TYPE = "xz";

	/**
	 * 经理直接管理的用户但不包含自己
	 */
	public final static String Z_TYPE = "z";

	public static String getTeamTypeScript(String elementId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<select id=\"").append(elementId).append("\" name=\"")
				.append(elementId).append("\">\n");
		buffer.append("    <option value=\"\" selected>----请选择----</option>\n");
		buffer.append("    <option value=\"x\">本人</option>\n");
		buffer.append("    <option value=\"z\">直接管理的团队成员</option>\n");
		buffer.append("    <option value=\"yz\">下属团队成员但不包含自己</option>\n");
		buffer.append("    <option value=\"xyz\">下属团队成员及自己</option>\n");
		buffer.append("</select>");
		return buffer.toString();
	}

}
