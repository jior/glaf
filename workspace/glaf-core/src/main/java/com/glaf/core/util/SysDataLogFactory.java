package com.glaf.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.service.SysDataLogService;

public class SysDataLogFactory {
	protected final static Log logger = LogFactory
			.getLog(SysDataLogFactory.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static boolean isMongoDB = false;

	static {
		isMongoDB = conf.getBoolean("mongodb.logEnable", false);
	}

	public static void create(SysDataLog log) {
		if (isMongoDB) {
			logger.debug(" save mongodb log.");
		} else {
			SysDataLogService sysDataLogService = ContextFactory
					.getBean("sysDataLogService");
			sysDataLogService.save(log);
			logger.debug(" save database log.");
		}
	}

}
