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
	 * JAVA������
	 */
	protected String name;

	/**
	 * ���ݿ��ֶ���
	 */
	protected String columnName;

	/**
	 * ����
	 */
	protected String title;

	/**
	 * Ӣ�ı���
	 */
	protected String englishTitle;

	/**
	 * JAVA�������ͣ��������ͼ��������ͣ�
	 */
	protected String type;

	/**
	 * ��������
	 */
	protected String mask;

	/**
	 * �����Ļ������ݴ���
	 */
	protected String dataCode;

	/**
	 * ��Ⱦ����(select,radio,checkbox)
	 */
	protected String renderType;

	/**
	 * �ֶ�˳���
	 */
	protected int sortNo;

	/**
	 * ���ݿ�����
	 */
	protected int dataType;

	/**
	 * ��ʾ����
	 */
	protected int displayType;

	/**
	 * ����
	 */
	protected int length;

	/**
	 * ������볤��
	 */
	protected int maxLength;

	/**
	 * ��С���볤��
	 */
	protected int minLength;

	/**
	 * �Ƿ�Ψһ
	 */
	protected boolean unique = false;

	/**
	 * �Ƿ�Ϊ��
	 */
	protected boolean nullable = true;

	/**
	 * �Ƿ�ɱ༭
	 */
	protected boolean editable = false;

	/**
	 * �Ƿ�ɸ���
	 */
	protected boolean updatable = true;

	/**
	 * �Ƿ�ɹ�����
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
