/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.base;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClassDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected byte[] bytes;

	/**
	 * 类名
	 */
	protected String className;

	/**
	 * 英文标题
	 */
	protected String englishTitle;

	/**
	 * 实体类
	 */
	protected Class<?> entityClass;

	/**
	 * Java 实体类名
	 */
	protected String entityName;

	protected Map<String, FieldDefinition> fields = new LinkedHashMap<String, FieldDefinition>();

	protected byte[] formBytes;

	/**
	 * 表单资源名
	 */
	protected String formResourceName;

	protected FieldDefinition idField;

	/**
	 * 是否需要JBPM工作流支持
	 */
	protected boolean jbpmSupport;

	/**
	 * Java 包名
	 */
	protected String packageName;

	/**
	 * 数据库表名
	 */
	protected String tableName;

	/**
	 * 标题
	 */
	protected String title;

	public ClassDefinition() {

	}

	public void addField(FieldDefinition field) {
		if (fields == null) {
			fields = new LinkedHashMap<String, FieldDefinition>();
		}
		if (!fields.containsKey(field.getName())) {
			fields.put(field.getName(), field);
			field.setClassDefinition(this);
		}
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getClassName() {
		if (className == null) {
			if (packageName != null && entityName != null) {
				className = packageName + ".model." + entityName;
			}
		}
		return className;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public Map<String, FieldDefinition> getFields() {
		return fields;
	}

	public byte[] getFormBytes() {
		return formBytes;
	}

	public String getFormResourceName() {
		return formResourceName;
	}

	public FieldDefinition getIdField() {
		return idField;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTitle() {
		return title;
	}

	public boolean isJbpmSupport() {
		return jbpmSupport;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setFields(Map<String, FieldDefinition> fields) {
		this.fields = fields;
	}

	public void setFormBytes(byte[] formBytes) {
		this.formBytes = formBytes;
	}

	public void setFormResourceName(String formResourceName) {
		this.formResourceName = formResourceName;
	}

	public void setIdField(FieldDefinition idField) {
		this.idField = idField;
	}

	public void setJbpmSupport(boolean jbpmSupport) {
		this.jbpmSupport = jbpmSupport;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
