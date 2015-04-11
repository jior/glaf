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

package com.glaf.core.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SysKeyMapper {

	void deleteSysKeys(SysKeyQuery query);

	void deleteSysKeyById(String id);

	SysKey getSysKeyById(String id);

	SysKey getSysKeyById_postgres(String id);

	int getSysKeyCount(SysKeyQuery query);

	List<SysKey> getSysKeys(SysKeyQuery query);

	void insertSysKey(SysKey model);

	void insertSysKey_postgres(SysKey model);

}
