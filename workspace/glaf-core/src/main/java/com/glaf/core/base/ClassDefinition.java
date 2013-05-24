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

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ClassDefinition extends JSONable {

	void addField(FieldDefinition field);

	String getClassName();

	String getEnglishTitle();

	String getEntityName();

	Map<String, FieldDefinition> getFields();

	FieldDefinition getIdField();

	String getModuleName();

	String getPackageName();

	String getTableName();

	String getTitle();

	boolean isJbpmSupport();

	boolean isTreeSupport();

	ClassDefinition jsonToObject(JSONObject jsonObject);

	void setClassName(String className);

	void setEnglishTitle(String englishTitle);

	void setEntityName(String entityName);

	void setIdField(FieldDefinition idField);

	void setJbpmSupport(boolean jbpmSupport);

	void setModuleName(String moduleName);

	void setPackageName(String packageName);

	void setTableName(String tableName);

	void setTitle(String title);

	void setTreeSupport(boolean treeSupport);

	JSONObject toJsonObject();

	ObjectNode toObjectNode();

}
