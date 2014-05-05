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

package com.glaf.cms.info.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.cms.info.model.*;
import com.glaf.cms.info.query.*;

@Transactional(readOnly = true)
public interface PublicInfoService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<PublicInfo> list(PublicInfoQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPublicInfoCountByQueryCriteria(PublicInfoQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<PublicInfo> getPublicInfosByQueryCriteria(int start, int pageSize,
			PublicInfoQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	PublicInfo getPublicInfo(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(PublicInfo publicInfo);
	
	@Transactional
	void updateViewCount(String id);

}
