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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FieldDefinition implements java.lang.Comparable<FieldDefinition>,
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * JAVA属性名
	 */
	protected String name;

	/**
	 * 数据库字段名
	 */
	protected String columnName;

	/**
	 * 标题
	 */
	protected String title;

	/**
	 * 英文标题
	 */
	protected String englishTitle;

	/**
	 * JAVA数据类型（基本类型及日期类型）
	 */
	protected String type;

	/**
	 * 输入掩码
	 */
	protected String mask;

	/**
	 * 关联的基础数据代码
	 */
	protected String dataCode;

	/**
	 * 渲染类型(select,radio,checkbox)
	 */
	protected String renderType;

	/**
	 * 字段顺序号
	 */
	protected int sortNo;

	/**
	 * 数据库类型
	 */
	protected int dataType;

	/**
	 * 显示类型
	 */
	protected int displayType;

	/**
	 * 长度
	 */
	protected int length;

	/**
	 * 最大输入长度
	 */
	protected int maxLength;

	/**
	 * 最小输入长度
	 */
	protected int minLength;

	/**
	 * 是否唯一
	 */
	protected boolean unique = false;

	/**
	 * 是否为空
	 */
	protected boolean nullable = true;

	/**
	 * 是否可编辑
	 */
	protected boolean editable = false;

	/**
	 * 是否可更新
	 */
	protected boolean updatable = true;

	/**
	 * 是否可供搜索
	 */
	protected boolean searchable = false;

	protected ClassDefinition classDefinition;

	public FieldDefinition() {

	}

	public int compareTo(FieldDefinition o) {
		if (o == null) {
			return -1;
		}

		FieldDefinition field = o;

		int l = this.sortNo - field.getSortNo();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDefinition other = (FieldDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDataCode() {
		return dataCode;
	}

	public int getDataType() {
		return dataType;
	}

	public int getDisplayType() {
		return displayType;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public String getFirstLowerName() {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public String getFirstUpperName() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public int getLength() {
		return length;
	}

	public String getMask() {
		return mask;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public String getName() {
		return name;
	}

	public String getRenderType() {
		return renderType;
	}

	public int getSortNo() {
		return sortNo;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public boolean isUnique() {
		return unique;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public void setRenderType(String renderType) {
		this.renderType = renderType;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
