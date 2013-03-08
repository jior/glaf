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
package com.glaf.base.modules.workspace.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.base.modules.workspace.model.*;
import com.glaf.base.modules.workspace.query.*;

@Component
public interface MyMenuMapper {

	void deleteMyMenus(MyMenuQuery query);

	void deleteMyMenuById(Long id);

	MyMenu getMyMenuById(Long id);

	int getMyMenuCount(MyMenuQuery query);

	List<MyMenu> getMyMenus(MyMenuQuery query);

	void insertMyMenu(MyMenu model);

	void updateMyMenu(MyMenu model);

}
