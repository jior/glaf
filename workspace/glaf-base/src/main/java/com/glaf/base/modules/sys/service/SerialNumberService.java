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

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SerialNumber;

@Transactional(readOnly = true)
public interface SerialNumberService {

	@Transactional
	List<SerialNumber> getImportSupplierSerialNumber(Map<String, Object> params);

	@Transactional
	String getSerialNumber(Map<String, Object> params);

	// 通过模块NO、category、area来查找SerialNumber中对应的moduleNo，从而获取SerialNumber对象
	@Transactional
	List<SerialNumber> getSupplierSerialNumber(Map<String, Object> params);

	@Transactional
	boolean update(SerialNumber bean);

}