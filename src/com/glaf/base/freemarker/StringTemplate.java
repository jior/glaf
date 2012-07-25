package com.glaf.base.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StringTemplate {

	public static String convert(Map root, String source) {
		if (source == null) {
			return null;
		}

		String rst = "";
		try {

			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(new StringTemplateLoader(source));
			cfg.setDefaultEncoding("UTF-8");

			Template template = cfg.getTemplate("");

			StringWriter writer = new StringWriter();
			template.process(root, writer);
			writer.flush();
			rst = writer.toString();

		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
}
