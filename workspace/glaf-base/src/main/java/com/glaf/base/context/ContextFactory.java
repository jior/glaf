package com.glaf.base.context;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ContextFactory {

	protected static final Log logger = LogFactory.getLog(ContextFactory.class);

	private static org.springframework.context.ApplicationContext ctx;

	private static DataSource dataSource;

	public static org.springframework.context.ApplicationContext getApplicationContext() {
		return ctx;
	}


	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) {
		if (ctx == null) {

		}
		String name = clazz.getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return (T) ctx.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (ctx == null) {

		}
		return (T) ctx.getBean(name);
	}

	public static java.sql.Connection getConnection() throws SQLException {
		if (dataSource == null) {
			dataSource = ContextFactory.getBean("dataSource");
		}
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		return null;
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = ContextFactory.getBean("dataSource");
		}
		return dataSource;
	}

	public static DataSource getDataSource(String dataSourceName) {
		return ContextFactory.getBean(dataSourceName);
	}

	public static boolean hasBean(String name) {
		return ctx.containsBean(name);
	}

	public static void setContext(
			org.springframework.context.ApplicationContext context) {
		if (context != null) {
			ctx = context;
		}
	}

}