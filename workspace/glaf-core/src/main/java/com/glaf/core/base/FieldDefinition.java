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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface FieldDefinition extends JSONable{

	ClassDefinition getClassDefinition();

	String getColumnName();

	String getDataCode();

	int getDisplayType();

	String getEnglishTitle();

	String getFirstLowerName();

	String getFirstUpperName();

	int getLength();

	String getMask();

	int getMaxLength();

	int getMinLength();

	String getName();

	String getRenderType();

	int getSortNo();

	String getTitle();

	String getType();

	boolean isEditable();

	boolean isNullable();

	boolean isSearchable();

	boolean isUnique();

	boolean isUpdatable();

	FieldDefinition jsonToObject(JSONObject jsonObject);

	void setClassDefinition(ClassDefinition classDefinition);

	void setColumnName(String columnName);

	void setDataCode(String dataCode);

	void setDataType(int dataType);

	void setDisplayType(int displayType);

	void setEditable(boolean editable);

	void setEnglishTitle(String englishTitle);

	void setLength(int length);

	void setMask(String mask);

	void setMaxLength(int maxLength);

	void setMinLength(int minLength);

	void setName(String name);

	void setNullable(boolean nullable);

	void setRenderType(String renderType);

	void setSearchable(boolean searchable);

	void setSortNo(int sortNo);

	void setTitle(String title);

	void setType(String type);

	void setUnique(boolean unique);

	void setUpdatable(boolean updatable);

	JSONObject toJsonObject();

	ObjectNode toObjectNode();

}
