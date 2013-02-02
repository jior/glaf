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

package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysLog;

public interface SysLogService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean create(SysLog bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean update(SysLog bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean delete(SysLog bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysLog findById(long id);
}