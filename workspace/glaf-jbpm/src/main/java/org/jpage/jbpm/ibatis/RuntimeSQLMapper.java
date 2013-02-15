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
	 * 流程及相关参数，引擎内部处理使用
	 */
	private Map params = new HashMap();

	/**
	 * SQLMAP语句的集合 其中key是定义在SQLMap配置文件中的id，不带名称空间
	 * value是对应于key的值，可以是任何的pojo或Map对象
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
	 * 流程及相关参数，引擎内部处理使用
	 */
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	/**
	 * SQLMAP语句的集合 其中key是定义在SQLMap配置文件中的id，不带名称空间
	 * value是对应于key的值，可以是任何的pojo或Map对象
	 */
	public Map getStatementMap() {
		return statementMap;
	}

	public void setStatementMap(Map statementMap) {
		this.statementMap = statementMap;
	}

}
