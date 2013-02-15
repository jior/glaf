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


package org.jpage.jbpm.ibatis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapClientFactoryBean implements FactoryBean, InitializingBean {

	private Properties sqlMapClientProperties;

	private Resource configLocation;

	private SqlMapClient sqlMapClient;

	public SqlMapClientFactoryBean() {

	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setSqlMapClientProperties(Properties sqlMapClientProperties) {
		this.sqlMapClientProperties = sqlMapClientProperties;
	}

	public void afterPropertiesSet() throws Exception {
		if (this.configLocation == null) {
			throw new IllegalArgumentException("configLocation is required");
		}
		InputStream is = this.configLocation.getInputStream();
		if (sqlMapClientProperties != null) {
			this.sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(
					new InputStreamReader(is), this.sqlMapClientProperties);
		} else {
			this.sqlMapClient = SqlMapClientBuilder
					.buildSqlMapClient(new InputStreamReader(is));
		}
	}

	public Object getObject() {
		return this.sqlMapClient;
	}

	public Class getObjectType() {
		return (this.sqlMapClient != null ? this.sqlMapClient.getClass()
				: SqlMapClient.class);
	}

	public boolean isSingleton() {
		return true;
	}

}
