package com.glaf.base.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class StringTemplateLoader implements TemplateLoader {

	private String template;

	public StringTemplateLoader(String template) {
		this.template = template;
		if (template == null) {
			this.template = "";
		}
	}

	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(template);
	}

	public long getLastModified(Object templateSource) {
		return 0;
	}

	public Reader getReader(Object templateSource, String encoding)
			throws IOException {
		return (Reader) templateSource;
	}

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader("»¶Ó­£º${user}"));
		cfg.setDefaultEncoding("UTF-8");

		Template template = cfg.getTemplate("");

		Map root = new HashMap();
		root.put("user", "Keven Chen");

		StringWriter writer = new StringWriter();
		template.process(root, writer);
		System.out.println(writer.toString());
	}
}
