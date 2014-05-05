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

public interface Accessable extends java.io.Serializable {

	public final static String OP_READ = "r";

	public final static String OP_READ_WRITE = "rw";

	public final static String OP_ALL = "*";

	/**
	 * 操作（增加、删除、修改、查询）
	 * 
	 * @return
	 */
	String getOperation();

	/**
	 * 服务标识
	 * 
	 * @return
	 */
	String getServiceKey();

	/**
	 * 访问目标（用户、角色、机构、上下级、观察者等）
	 * 
	 * @return
	 */
	String getTarget();

	/**
	 * 访问目标类型
	 * 
	 * @return
	 */
	int getTargetType();

	/**
	 * 设置操作
	 * 
	 * @return
	 */
	void setOperation(String operation);

	/**
	 * 设置服务标识
	 * 
	 * @return
	 */
	void setServiceKey(String serviceKey);

	/**
	 * 设置访问目标
	 * 
	 * @return
	 */
	void setTarget(String target);

	/**
	 * 设置访问目标类型
	 * 
	 * @return
	 */
	void setTargetType(int targetType);

}