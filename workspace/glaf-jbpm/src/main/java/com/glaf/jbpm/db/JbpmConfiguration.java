package com.glaf.jbpm.db;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.*;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;

public class JbpmConfiguration {

	public static void main(String[] args) {
		JbpmConfiguration cfg = new JbpmConfiguration();
		cfg.config();
	}

	public void config() {
		Properties properties = DBConfiguration
				.getDefaultDataSourceProperties();
		String filename = SystemConfig.getConfigRootPath()
				+ "/conf/jbpm/hibernate.cfg.xml";
		SAXReader xmlReader = new SAXReader();
		Resource resource = null;
		try {
			resource = new FileSystemResource(filename);
			Document doc = xmlReader.read(resource.getInputStream());
			Element root = doc.getRootElement();
			Element element = root.element("session-factory");
			String datasource = properties
					.getProperty(DBConfiguration.JDBC_DATASOURCE);
			List<?> props = element.elements("property");
			if (props != null && !props.isEmpty()) {
				Iterator<?> iter = props.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					if (StringUtils.equals("hibernate.connection.datasource",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals("hibernate.dialect",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals(
							"hibernate.connection.provider_class",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals("hibernate.connection.driver_class",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals("hibernate.connection.url",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals("hibernate.connection.username",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}

					if (StringUtils.equals("hibernate.connection.password",
							elem.attributeValue("name"))) {
						element.remove(elem);
					}
				}
			}

			if (StringUtils.isNotEmpty(datasource)) {
				Element em = element.addElement("property");
				em.addAttribute("name", "hibernate.connection.datasource");
				em.setText(datasource);
			} else {
				Element em1 = element.addElement("property");
				em1.addAttribute("name", "hibernate.dialect");
				em1.setText(DBConfiguration.getDefaultHibernateDialect());

				Element em2 = element.addElement("property");
				em2.addAttribute("name", "hibernate.connection.driver_class");
				em2.setText(properties.getProperty(DBConfiguration.JDBC_DRIVER));

				Element em3 = element.addElement("property");
				em3.addAttribute("name", "hibernate.connection.url");
				em3.setText(properties.getProperty(DBConfiguration.JDBC_URL));

				Element em4 = element.addElement("property");
				em4.addAttribute("name", "hibernate.connection.username");
				em4.setText(properties.getProperty(DBConfiguration.JDBC_USER));

				Element em5 = element.addElement("property");
				em5.addAttribute("name", "hibernate.connection.password");
				em5.setText(properties
						.getProperty(DBConfiguration.JDBC_PASSWORD));

				Element em6 = element.addElement("property");
				em6.addAttribute("name", "hibernate.connection.provider_class");
				em6.setText("com.glaf.jbpm.connection.DruidConnectionProvider");
			}

			Document newDoc = DocumentHelper.createDocument();
			newDoc.addDocType("hibernate-configuration",
					"-//Hibernate/Hibernate Configuration DTD 3.0//EN",
					"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd");
			Element newRoot = newDoc.addElement("hibernate-configuration");
			Element newElement = newRoot.addElement("session-factory");
			if (props != null && !props.isEmpty()) {
				Iterator<?> iter = props.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					Element newElem = newElement.addElement("property");
					newElem.addAttribute("name", elem.attributeValue("name"));
					newElem.setText(elem.getStringValue());
				}
			}

			props = element.elements("mapping");
			if (props != null && !props.isEmpty()) {
				Iterator<?> iter = props.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					Element newElem = newElement.addElement("mapping");
					newElem.addAttribute("resource",
							elem.attributeValue("resource"));
				}
			}

			props = element.elements("class-cache");
			if (props != null && !props.isEmpty()) {
				Iterator<?> iter = props.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					Element newElem = newElement.addElement("class-cache");
					newElem.addAttribute("class", elem.attributeValue("class"));
					newElem.addAttribute("usage", elem.attributeValue("usage"));
				}
			}

			props = element.elements("collection-cache");
			if (props != null && !props.isEmpty()) {
				Iterator<?> iter = props.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					Element newElem = newElement.addElement("collection-cache");
					newElem.addAttribute("collection",
							elem.attributeValue("collection"));
					newElem.addAttribute("usage", elem.attributeValue("usage"));
				}
			}

			byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(newDoc,
					"UTF-8");
			FileUtils.save(filename, bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
