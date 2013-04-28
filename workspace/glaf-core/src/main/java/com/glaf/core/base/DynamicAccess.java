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

package com.glaf.core.base;

public interface DynamicAccess extends Accessable {

	/**
	 * ʵ�����ͣ�mybatis3��jpa��
	 * 
	 * @return
	 */
	String getEntityType();

	/**
	 * ����SQL
	 * 
	 * @return
	 */
	String getFilterSql();

	/**
	 * ����
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Ŀ��ID
	 * 
	 * @return
	 */
	String getObjectId();

	/**
	 * Ŀ��ֵ
	 * 
	 * @return
	 */
	String getObjectValue();

	void setEntityType(String entityType);

	void setFilterSql(String filterSql);

	void setId(String id);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

}