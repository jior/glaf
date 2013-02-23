package com.glaf.generator.tools;

import java.beans.PropertyDescriptor;
import java.util.*;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.glaf.core.util.*;

public class Java2Hbm {

	public static void exportHbm(String className, String tablename,
			String primaryKey, String filename) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\"?>\n");
		buffer.append("<!DOCTYPE hibernate-mapping PUBLIC\n");
		buffer.append("	\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n");
		buffer.append("	\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n");
		buffer.append("<hibernate-mapping>\n");
		buffer.append("  <class name=\"").append(className).append('"');
		buffer.append(" table=\"").append(tablename.toUpperCase())
				.append("\" dynamic-update=\"true\" dynamic-insert=\"true\" >");
		buffer.append('\n');

		Object model = ReflectUtils.instantiate(className);

		BeanWrapper beanWrapper = new BeanWrapperImpl(model);
		PropertyDescriptor[] propertyDescriptor = beanWrapper
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if (propertyName.equalsIgnoreCase("class")) {
				continue;
			}

			Class<?> type = descriptor.getPropertyType();

			if (type.isAssignableFrom(List.class)) {
				continue;
			} else if (type.isAssignableFrom(Set.class)) {
				continue;
			} else if (type.isAssignableFrom(Collection.class)) {
				continue;
			} else if (type.isAssignableFrom(Map.class)) {
				continue;
			}

			if (propertyName.equals(primaryKey)) {
				buffer.append("    <id column=\"")
						.append(propertyName.toUpperCase()).append("_\"")
						.append(" name=\"").append(propertyName).append('"');
			} else {
				buffer.append("    <property column=\"")
						.append(propertyName.toUpperCase()).append("_\"")
						.append(" name=\"").append(propertyName).append('"');
			}

			try {
				if (type == byte.class || type == java.lang.Byte.class) {
					buffer.append(" type=\"byte\" ");
				} else if (type == short.class || type == java.lang.Short.class) {
					buffer.append(" type=\"short\" ");
				} else if (type == float.class || type == java.lang.Float.class) {
					buffer.append(" type=\"double\" ");
				} else if (type == double.class
						|| type == java.lang.Double.class) {
					buffer.append(" type=\"double\" ");
				} else if (type == int.class || type == java.lang.Integer.class) {
					buffer.append(" type=\"int\" ");
				} else if (type == long.class || type == java.lang.Long.class) {
					buffer.append(" type=\"long\" ");
				} else if (type == java.util.Date.class) {
					buffer.append(" type=\"timestamp\" ");
				} else if (type == java.sql.Date.class) {
					buffer.append(" type=\"timestamp\" ");
				} else if (type == java.sql.Timestamp.class) {
					buffer.append(" type=\"timestamp\" ");
				} else if (type == String.class) {
					buffer.append(" type=\"string\" length=\"50\" ");
				} else if (type == java.sql.Clob.class) {
					buffer.append(" type=\"clob\" ");
				} else if (type == java.sql.Blob.class) {
					buffer.append(" type=\"blob\" ");
				}

				if (propertyName.equals(primaryKey)) {
					buffer.append(">\n");
					buffer.append("        <generator class=\"assigned\" />\n");
					buffer.append("    </id>\n");
				} else {
					buffer.append("/>\n");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		buffer.append("  </class>\n");
		buffer.append("</hibernate-mapping>\n");

		FileUtils.save(filename, buffer.toString().getBytes());
	}

	public static void main(String[] args) throws Exception {

		if (args.length == 4) {
			Java2Hbm.exportHbm(args[0], args[1], args[2], args[3]);
		}
	}

}
