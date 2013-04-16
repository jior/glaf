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
package com.glaf.form.core.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormApplication;

@Transactional 
public interface FormArchiveService {

	void archives(FormApplication formApplication, String businessKey,
			Map<String, Object> dataMap);

	void archives(String app_name, String businessKey,
			Map<String, Object> dataMap);

}