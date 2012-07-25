package com.glaf.base.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.beanutils.PropertyUtils;

public class PropertyUtilExtends extends PropertyUtils {
	
	private String format;
	public void setFormat(String format){
		this.format = format;
	}

	public void copyCustomerProperties(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ParseException {

		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		PropertyDescriptor origDescriptors[] = getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if (isReadable(orig, name)) {
				if (isWriteable(dest, name)) {
					Object value = getCustomerProperty(orig, name);
					setCustomerProperty(dest, name, value);
				}
			}
		}

	}

	private Object getCustomerProperty(Object bean, String name)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}
		if (name == null) {
			throw new IllegalArgumentException("No name specified");
		}

		PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
		if (descriptor == null) {
			throw new NoSuchMethodException("Unknown property '" + name + "'");
		}
		Method readMethod = getReadMethod(descriptor);
		if (readMethod == null) {
			throw new NoSuchMethodException("Property '" + name
					+ "' has no getter method");
		}

		Object value = readMethod.invoke(bean, new Object[0]);
		return (value);

	}

	private void setCustomerProperty(Object bean, String name,
			Object value) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, ParseException {

		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}
		if (name == null) {
			throw new IllegalArgumentException("No name specified");
		}

		PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
		if (descriptor == null) {
			throw new NoSuchMethodException("Unknown property '" + name + "'");
		}

		Method writeMethod = getWriteMethod(descriptor);
		if (writeMethod == null) {
			throw new NoSuchMethodException("Property '" + name
					+ "' has no setter method");
		}

		Object values[] = new Object[1];
		Method readMethod = getReadMethod(descriptor);
		if (readMethod.getReturnType() == Date.class) {
			if( value instanceof String ){
				values[0] = WebUtil.stringToDate((String) value,format);
			}
		} else {
			if( value instanceof Date ){
				values[0] = WebUtil.dateToString((Date)value,format);
			}else{
				values[0] = value;
			}
		}
		writeMethod.invoke(bean, values);
	}

}
