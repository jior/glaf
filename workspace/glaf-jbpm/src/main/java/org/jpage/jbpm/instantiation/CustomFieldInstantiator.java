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


package org.jpage.jbpm.instantiation;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.JbpmException;
import org.jbpm.instantiation.FieldInstantiator;
import org.jbpm.instantiation.Instantiator;
import org.jbpm.util.ClassLoaderUtil;
import org.jpage.util.DateTools;

public class CustomFieldInstantiator extends FieldInstantiator implements
		Instantiator {
	private static final Log logger = LogFactory
			.getLog(CustomFieldInstantiator.class);

	public static Object getValue(Class type, Element propertyElement) {
		Object value = null;
		try {
			if (type == String.class) {
				value = propertyElement.getText();
			} else if ((type == Integer.class) || (type == int.class)) {
				value = new Integer(propertyElement.getTextTrim());
			} else if ((type == Long.class) || (type == long.class)) {
				value = new Long(propertyElement.getTextTrim());
			} else if ((type == Float.class) || (type == float.class)) {
				value = new Float(propertyElement.getTextTrim());
			} else if ((type == Double.class) || (type == double.class)) {
				value = new Double(propertyElement.getTextTrim());
			} else if ((type == Boolean.class) || (type == boolean.class)) {
				value = Boolean.valueOf(propertyElement.getTextTrim());
			} else if ((type == Character.class) || (type == char.class)) {
				value = new Character(propertyElement.getTextTrim().charAt(0));
			} else if ((type == Short.class) || (type == short.class)) {
				value = new Short(propertyElement.getTextTrim());
			} else if ((type == Byte.class) || (type == byte.class)) {
				value = new Byte(propertyElement.getTextTrim());
			} else if (type.isAssignableFrom(java.util.Date.class)) {
				value = DateTools.toDate(propertyElement.getTextTrim());
			} else if (type.isAssignableFrom(List.class)) {
				value = getCollectionValue(propertyElement, new ArrayList());
			} else if (type.isAssignableFrom(Set.class)) {
				value = getCollectionValue(propertyElement, new LinkedHashSet());
			} else if (type.isAssignableFrom(Collection.class)) {
				value = getCollectionValue(propertyElement, new ArrayList());
			} else if (type.isAssignableFrom(Map.class)) {
				value = getMapValue(propertyElement, new LinkedHashMap());
			} else if (type == Element.class) {
				value = propertyElement;
			} else {
				Constructor constructor = type
						.getConstructor(new Class[] { String.class });
				if ((propertyElement.isTextOnly()) && (constructor != null)) {
					value = constructor
							.newInstance(new Object[] { propertyElement
									.getTextTrim() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("couldn't parse the bean property value '"
					+ propertyElement.asXML() + "' to a '" + type.getName()
					+ "'");
			throw new JbpmException(ex);
		}
		return value;
	}

	private static Object getMapValue(Element mapElement, Map map) {
		Class keyClass = String.class;
		String keyType = mapElement.attributeValue("key-type");
		if (keyType != null) {
			keyClass = ClassLoaderUtil.loadClass(keyType);
		}
		Class valueClass = String.class;
		String valueType = mapElement.attributeValue("value-type");
		if (valueType != null) {
			valueClass = ClassLoaderUtil.loadClass(valueType);
		}
		Iterator iter = mapElement.elementIterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			Element keyElement = element.element("key");
			Element valueElement = element.element("value");
			Class clazz = null;
			String className = element.attributeValue("value-type");
			if (className != null) {
				clazz = ClassLoaderUtil.loadClass(className);
			}
			if (clazz == null) {
				clazz = valueClass;
			}
			map.put(getValue(keyClass, keyElement), getValue(clazz,
					valueElement));
		}
		return map;
	}

	private static Object getCollectionValue(Element collectionElement,
			Collection collection) {
		Class elementClass = String.class;
		String elementType = collectionElement.attributeValue("element-type");
		if (elementType != null) {
			elementClass = ClassLoaderUtil.loadClass(elementType);
		}
		Iterator iter = collectionElement.elementIterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			collection.add(getValue(elementClass, element));
		}
		return collection;
	}

}
