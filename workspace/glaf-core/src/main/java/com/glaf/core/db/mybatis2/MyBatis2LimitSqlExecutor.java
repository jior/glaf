/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.db.mybatis2;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.LogUtils;
import com.ibatis.sqlmap.engine.execution.DefaultSqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class MyBatis2LimitSqlExecutor extends DefaultSqlExecutor {

	protected static final Log logger = LogFactory
			.getLog(MyBatis2LimitSqlExecutor.class);

	private Dialect dialect;

	private String dialectClass;

	private boolean enableLimit = true;

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public String getDialectClass() {
		return dialectClass;
	}

	public void setDialectClass(String dialectClass) {
		this.dialectClass = dialectClass;
		if (dialectClass == null) {
			dialectClass = DBConfiguration.getCurrentDialectClass();
		}
		if (dialectClass != null) {
			Object object = ClassUtils.instantiateObject(dialectClass);
			if (object instanceof Dialect) {
				dialect = (Dialect) object;
				logger.debug(dialectClass + " dialect is injected!");
			}
		}
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	@Override
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
				&& supportsLimit()) {
			sql = dialect.getLimitString(sql, skipResults, maxResults);
			if (LogUtils.isDebug()) {
				logger.debug(sql);
			}
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,
				maxResults, callback);
	}

	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return dialect.supportsLimit();
		}
		return false;
	}

}