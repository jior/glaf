package com.glaf.core.test;

import com.glaf.core.config.*;

public class ConfigurationTest {
	protected static Configuration conf = BaseConfiguration.create();

	public static void main(String[] args) {
		System.out.println(conf.get("i18n.messages_key"));
		System.out.println(conf.getBoolean("password.enc", false));
		System.out.println(conf.getInt("wx_log_step", 100));
	}
}
