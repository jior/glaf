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
