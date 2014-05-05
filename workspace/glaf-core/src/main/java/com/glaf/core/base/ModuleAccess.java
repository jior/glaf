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

public interface ModuleAccess extends Accessable {

	/**
	 * 获取基础代码
	 * 
	 * @return
	 */
	String getBaseCode();

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 获取目标ID
	 * 
	 * @return
	 */
	String getObjectId();

	/**
	 * 获取目标值
	 * 
	 * @return
	 */
	String getObjectValue();

	/**
	 * 操作（新增create，修改update，删除delete，查看view，审核audit，打印print，导出export）
	 */
	String getOperation();

	/**
	 * 访问目标（用户、部门、角色或上级）
	 */
	String getTarget();

	/**
	 * 访问目标类型<br/>
	 * 0-用户<br/>
	 * 1-部门<br/>
	 * 2-角色<br/>
	 * 3-上级<br/>
	 */
	int getTargetType();

	void setBaseCode(String baseCode);

	void setId(String id);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

	void setOperation(String operation);

	void setTarget(String target);

	void setTargetType(int targetType);

}