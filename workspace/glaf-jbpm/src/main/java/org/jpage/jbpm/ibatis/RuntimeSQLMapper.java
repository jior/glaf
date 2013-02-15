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

package org.jpage.jbpm.ibatis;

import java.util.HashMap;
import java.util.Map;

public class RuntimeSQLMapper implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String sqlMapAction;

	/**
	 * ���̼���ز����������ڲ�����ʹ��
	 */
	private Map params = new HashMap();

	/**
	 * SQLMAP���ļ��� ����key�Ƕ�����SQLMap�����ļ��е�id���������ƿռ�
	 * value�Ƕ�Ӧ��key��ֵ���������κε�pojo��Map����
	 */
	private Map statementMap = new HashMap();

	public RuntimeSQLMapper() {

	}

	public String getSqlMapAction() {
		return sqlMapAction;
	}

	public void setSqlMapAction(String sqlMapAction) {
		this.sqlMapAction = sqlMapAction;
	}

	/**
	 * ���̼���ز����������ڲ�����ʹ��
	 */
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	/**
	 * SQLMAP���ļ��� ����key�Ƕ�����SQLMap�����ļ��е�id���������ƿռ�
	 * value�Ƕ�Ӧ��key��ֵ���������κε�pojo��Map����
	 */
	public Map getStatementMap() {
		return statementMap;
	}

	public void setStatementMap(Map statementMap) {
		this.statementMap = statementMap;
	}

}
