package com.glaf.generator.xml;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.generator.CodeDef;

public class XmlReader {

	public List<CodeDef> read(java.io.InputStream inputStream) {
		List<CodeDef> rows = new ArrayList<CodeDef>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> elements = root.elements("definition");
			if (elements != null) {
				Iterator<?> iterator = elements.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					CodeDef def = new CodeDef();
					def.setName(element.attributeValue("name"));
					def.setEncoding(element.elementText("encoding"));
					def.setSaveName(element.elementText("saveName"));
					def.setSavePath(element.elementText("savePath"));
					def.setTemplate(element.elementText("template"));
					def.setProcessor(element.elementText("processor"));

					Element elem = element.element("properties");
					if (elem != null) {
						List<?> properties = elem.elements("property");
						if (properties != null && properties.size() > 0) {
							Iterator<?> iter = properties.iterator();
							while (iter.hasNext()) {
								Element em = (Element) iter.next();
								String propertyName = em.attributeValue("name");
								String propertyValue = null;
								if (StringUtils.isNotEmpty(em
										.attributeValue("value"))) {
									propertyValue = em.attributeValue("value");
								} else {
									propertyValue = em.getTextTrim();
								}
								def.addProperty(propertyName, propertyValue);
							}
						}
					}
					rows.add(def);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return rows;
	}
}
