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
package com.glaf.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.realm.text.IniRealm;

public class MyIniRealm extends IniRealm {

	protected final static Log logger = LogFactory.getLog(MyIniRealm.class);

	public MyIniRealm() {
		super();
		logger.debug("--------------------MyIniRealm-------------------");
	}

	@Override
	public void onInit() {
		super.onInit();
		logger.debug("roles:" + super.getRoleDefinitions());
	}

}
