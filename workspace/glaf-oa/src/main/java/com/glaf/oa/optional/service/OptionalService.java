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
package com.glaf.oa.optional.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.optional.model.*;
import com.glaf.oa.optional.query.*;

@Transactional(readOnly = true)
public interface OptionalService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Integer id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Integer> optionalIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Optional> list(OptionalQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getOptionalCountByQueryCriteria(OptionalQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Optional> getOptionalsByQueryCriteria(int start, int pageSize,
			OptionalQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Optional getOptional(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Optional optional);

}