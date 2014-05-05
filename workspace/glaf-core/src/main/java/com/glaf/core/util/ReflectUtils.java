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

package com.glaf.core.util;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;

import com.glaf.core.config.Configuration;

public class ReflectUtils {

	private static final Log logger = LogFactory.getLog(ReflectUtils.class);

	private static final Class<?>[] EMPTY_ARRAY = new Class[] {};

	/**
	 * void(V).
	 */
	public static final char JVM_VOID = 'V';

	/**
	 * boolean(Z).
	 */
	public static final char JVM_BOOLEAN = 'Z';

	/**
	 * byte(B).
	 */
	public static final char JVM_BYTE = 'B';

	/**
	 * char(C).
	 */
	public static final char JVM_CHAR = 'C';

	/**
	 * double(D).
	 */
	public static final char JVM_DOUBLE = 'D';

	/**
	 * float(F).
	 */
	public static final char JVM_FLOAT = 'F';

	/**
	 * int(I).
	 */
	public static final char JVM_INT = 'I';

	/**
	 * long(J).
	 */
	public static final char JVM_LONG = 'J';

	/**
	 * short(S).
	 */
	public static final char JVM_SHORT = 'S';

	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

	public static final String JAVA_IDENT_REGEX = "(?:[_$a-zA-Z][_$a-zA-Z0-9]*)";

	public static final String JAVA_NAME_REGEX = "(?:" + JAVA_IDENT_REGEX
			+ "(?:\\." + JAVA_IDENT_REGEX + ")*)";

	public static final String CLASS_DESC = "(?:L" + JAVA_IDENT_REGEX
			+ "(?:\\/" + JAVA_IDENT_REGEX + ")*;)";

	public static final String ARRAY_DESC = "(?:\\[+(?:(?:[VZBCDFIJS])|"
			+ CLASS_DESC + "))";

	public static final String DESC_REGEX = "(?:(?:[VZBCDFIJS])|" + CLASS_DESC
			+ "|" + ARRAY_DESC + ")";

	public static final Pattern DESC_PATTERN = Pattern.compile(DESC_REGEX);

	public static final String METHOD_DESC_REGEX = "(?:(" + JAVA_IDENT_REGEX
			+ ")?\\((" + DESC_REGEX + "*)\\)(" + DESC_REGEX + ")?)";

	public static final Pattern METHOD_DESC_PATTERN = Pattern
			.compile(METHOD_DESC_REGEX);

	public static final Pattern GETTER_METHOD_DESC_PATTERN = Pattern
			.compile("get([A-Z][_a-zA-Z0-9]*)\\(\\)(" + DESC_REGEX + ")");

	public static final Pattern SETTER_METHOD_DESC_PATTERN = Pattern
			.compile("set([A-Z][_a-zA-Z0-9]*)\\((" + DESC_REGEX + ")\\)V");

	public static final Pattern IS_HAS_CAN_METHOD_DESC_PATTERN = Pattern
			.compile("(?:is|has|can)([A-Z][_a-zA-Z0-9]*)\\(\\)Z");

	private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = new ConcurrentHashMap<Class<?>, Constructor<?>>();

	/**
	 * desc to name. "[[I" => "int[][]"
	 * 
	 * @param desc
	 *            desc.
	 * @return name.
	 */
	public static String desc2name(String desc) {
		StringBuilder sb = new StringBuilder();
		int c = desc.lastIndexOf('[') + 1;
		if (desc.length() == c + 1) {
			switch (desc.charAt(c)) {
			case JVM_VOID: {
				sb.append("void");
				break;
			}
			case JVM_BOOLEAN: {
				sb.append("boolean");
				break;
			}
			case JVM_BYTE: {
				sb.append("byte");
				break;
			}
			case JVM_CHAR: {
				sb.append("char");
				break;
			}
			case JVM_DOUBLE: {
				sb.append("double");
				break;
			}
			case JVM_FLOAT: {
				sb.append("float");
				break;
			}
			case JVM_INT: {
				sb.append("int");
				break;
			}
			case JVM_LONG: {
				sb.append("long");
				break;
			}
			case JVM_SHORT: {
				sb.append("short");
				break;
			}
			default:
				throw new RuntimeException();
			}
		} else {
			sb.append(desc.substring(c + 1, desc.length() - 1)
					.replace('/', '.'));
		}
		while (c-- > 0)
			sb.append("[]");
		return sb.toString();
	}

	public static Constructor<?> findConstructor(Class<?> clazz,
			Class<?> paramType) throws NoSuchMethodException {
		Constructor<?> targetConstructor;
		try {
			targetConstructor = clazz
					.getConstructor(new Class<?>[] { paramType });
		} catch (NoSuchMethodException e) {
			targetConstructor = null;
			Constructor<?>[] constructors = clazz.getConstructors();
			for (Constructor<?> constructor : constructors) {
				if (Modifier.isPublic(constructor.getModifiers())
						&& constructor.getParameterTypes().length == 1
						&& constructor.getParameterTypes()[0]
								.isAssignableFrom(paramType)) {
					targetConstructor = constructor;
					break;
				}
			}
			if (targetConstructor == null) {
				throw e;
			}
		}
		return targetConstructor;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> Constructor<T> findMatchingConstructor(Class<T> clazz,
			Object[] args) {
		for (Constructor constructor : clazz.getDeclaredConstructors()) {
			if (matches(constructor.getParameterTypes(), args)) {
				return constructor;
			}
		}
		return null;
	}

	private static Method findMethod(Class<? extends Object> clazz,
			String methodName, Object[] args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& matches(method.getParameterTypes(), args)) {
				return method;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return findMethod(superClass, methodName, args);
		}
		return null;
	}

	public static Class<?> getBoxedClass(Class<?> c) {
		if (c == int.class)
			c = Integer.class;
		else if (c == boolean.class)
			c = Boolean.class;
		else if (c == long.class)
			c = Long.class;
		else if (c == float.class)
			c = Float.class;
		else if (c == double.class)
			c = Double.class;
		else if (c == char.class)
			c = Character.class;
		else if (c == byte.class)
			c = Byte.class;
		else if (c == short.class)
			c = Short.class;
		return c;
	}

	public static ClassLoader getClassLoader() {
		ClassLoader loader = getCustomClassLoader();
		if (loader == null) {
			loader = Thread.currentThread().getContextClassLoader();
		}
		return loader;
	}

	public static String getCodeBase(Class<?> cls) {
		if (cls == null)
			return null;
		ProtectionDomain domain = cls.getProtectionDomain();
		if (domain == null)
			return null;
		CodeSource source = domain.getCodeSource();
		if (source == null)
			return null;
		URL location = source.getLocation();
		if (location == null)
			return null;
		return location.getFile();
	}

	private static ClassLoader getCustomClassLoader() {
		return null;
	}

	/**
	 * get class desc. boolean[].class => "[Z" Object.class =>
	 * "Ljava/lang/Object;"
	 * 
	 * @param c
	 *            class.
	 * @return desc.
	 * @throws NotFoundException
	 */
	public static String getDesc(Class<?> c) {
		StringBuilder ret = new StringBuilder();

		while (c.isArray()) {
			ret.append('[');
			c = c.getComponentType();
		}

		if (c.isPrimitive()) {
			String t = c.getName();
			if ("void".equals(t))
				ret.append(JVM_VOID);
			else if ("boolean".equals(t))
				ret.append(JVM_BOOLEAN);
			else if ("byte".equals(t))
				ret.append(JVM_BYTE);
			else if ("char".equals(t))
				ret.append(JVM_CHAR);
			else if ("double".equals(t))
				ret.append(JVM_DOUBLE);
			else if ("float".equals(t))
				ret.append(JVM_FLOAT);
			else if ("int".equals(t))
				ret.append(JVM_INT);
			else if ("long".equals(t))
				ret.append(JVM_LONG);
			else if ("short".equals(t))
				ret.append(JVM_SHORT);
		} else {
			ret.append('L');
			ret.append(c.getName().replace('.', '/'));
			ret.append(';');
		}
		return ret.toString();
	}

	/**
	 * get class array desc. [int.class, boolean[].class, Object.class] =>
	 * "I[ZLjava/lang/Object;"
	 * 
	 * @param cs
	 *            class array.
	 * @return desc.
	 * @throws NotFoundException
	 */
	public static String getDesc(final Class<?>[] cs) {
		if (cs.length == 0)
			return "";

		StringBuilder sb = new StringBuilder(64);
		for (Class<?> c : cs)
			sb.append(getDesc(c));
		return sb.toString();
	}

	/**
	 * get constructor desc. "()V", "(Ljava/lang/String;I)V"
	 * 
	 * @param c
	 *            constructor.
	 * @return desc
	 */
	public static String getDesc(final Constructor<?> c) {
		StringBuilder ret = new StringBuilder("(");
		Class<?>[] parameterTypes = c.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append('V');
		return ret.toString();
	}

	/**
	 * get class desc. Object.class => "Ljava/lang/Object;" boolean[].class =>
	 * "[Z"
	 * 
	 * @param c
	 *            class.
	 * @return desc.
	 * @throws NotFoundException
	 */
	public static String getDesc(final CtClass c) throws NotFoundException {
		StringBuilder ret = new StringBuilder();
		if (c.isArray()) {
			ret.append('[');
			ret.append(getDesc(c.getComponentType()));
		} else if (c.isPrimitive()) {
			String t = c.getName();
			if ("void".equals(t))
				ret.append(JVM_VOID);
			else if ("boolean".equals(t))
				ret.append(JVM_BOOLEAN);
			else if ("byte".equals(t))
				ret.append(JVM_BYTE);
			else if ("char".equals(t))
				ret.append(JVM_CHAR);
			else if ("double".equals(t))
				ret.append(JVM_DOUBLE);
			else if ("float".equals(t))
				ret.append(JVM_FLOAT);
			else if ("int".equals(t))
				ret.append(JVM_INT);
			else if ("long".equals(t))
				ret.append(JVM_LONG);
			else if ("short".equals(t))
				ret.append(JVM_SHORT);
		} else {
			ret.append('L');
			ret.append(c.getName().replace('.', '/'));
			ret.append(';');
		}
		return ret.toString();
	}

	/**
	 * get constructor desc. "()V", "(Ljava/lang/String;I)V"
	 * 
	 * @param c
	 *            constructor.
	 * @return desc
	 */
	public static String getDesc(final CtConstructor c)
			throws NotFoundException {
		StringBuilder ret = new StringBuilder("(");
		CtClass[] parameterTypes = c.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append('V');
		return ret.toString();
	}

	/**
	 * get method desc. "do(I)I", "do()V", "do(Ljava/lang/String;Z)V"
	 * 
	 * @param m
	 *            method.
	 * @return desc.
	 */
	public static String getDesc(final CtMethod m) throws NotFoundException {
		StringBuilder ret = new StringBuilder(m.getName()).append('(');
		CtClass[] parameterTypes = m.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append(getDesc(m.getReturnType()));
		return ret.toString();
	}

	/**
	 * get method desc. int do(int arg1) => "do(I)I" void do(String arg1,boolean
	 * arg2) => "do(Ljava/lang/String;Z)V"
	 * 
	 * @param m
	 *            method.
	 * @return desc.
	 */
	public static String getDesc(final Method m) {
		StringBuilder ret = new StringBuilder(m.getName()).append('(');
		Class<?>[] parameterTypes = m.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append(getDesc(m.getReturnType()));
		return ret.toString();
	}

	/**
	 * get method desc. "(I)I", "()V", "(Ljava/lang/String;Z)V".
	 * 
	 * @param m
	 *            method.
	 * @return desc.
	 */
	public static String getDescWithoutMethodName(final CtMethod m)
			throws NotFoundException {
		StringBuilder ret = new StringBuilder();
		ret.append('(');
		CtClass[] parameterTypes = m.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append(getDesc(m.getReturnType()));
		return ret.toString();
	}

	/**
	 * get method desc. "(I)I", "()V", "(Ljava/lang/String;Z)V"
	 * 
	 * @param m
	 *            method.
	 * @return desc.
	 */
	public static String getDescWithoutMethodName(Method m) {
		StringBuilder ret = new StringBuilder();
		ret.append('(');
		Class<?>[] parameterTypes = m.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++)
			ret.append(getDesc(parameterTypes[i]));
		ret.append(')').append(getDesc(m.getReturnType()));
		return ret.toString();
	}

	public static Object getEmptyObject(Class<?> returnType) {
		return getEmptyObject(returnType,
				new java.util.concurrent.ConcurrentHashMap<Class<?>, Object>(),
				0);
	}

	private static Object getEmptyObject(Class<?> returnType,
			Map<Class<?>, Object> emptyInstances, int level) {
		if (level > 2)
			return null;
		if (returnType == null) {
			return null;
		} else if (returnType == boolean.class || returnType == Boolean.class) {
			return false;
		} else if (returnType == char.class || returnType == Character.class) {
			return '\0';
		} else if (returnType == byte.class || returnType == Byte.class) {
			return (byte) 0;
		} else if (returnType == short.class || returnType == Short.class) {
			return (short) 0;
		} else if (returnType == int.class || returnType == Integer.class) {
			return 0;
		} else if (returnType == long.class || returnType == Long.class) {
			return 0L;
		} else if (returnType == float.class || returnType == Float.class) {
			return 0F;
		} else if (returnType == double.class || returnType == Double.class) {
			return 0D;
		} else if (returnType.isArray()) {
			return Array.newInstance(returnType.getComponentType(), 0);
		} else if (returnType.isAssignableFrom(ArrayList.class)) {
			return new java.util.ArrayList<Object>(0);
		} else if (returnType.isAssignableFrom(HashSet.class)) {
			return new HashSet<Object>(0);
		} else if (returnType.isAssignableFrom(HashMap.class)) {
			return new java.util.concurrent.ConcurrentHashMap<Object, Object>(0);
		} else if (String.class.equals(returnType)) {
			return "";
		} else if (!returnType.isInterface()) {
			try {
				Object value = emptyInstances.get(returnType);
				if (value == null) {
					value = returnType.newInstance();
					emptyInstances.put(returnType, value);
				}
				Class<?> cls = value.getClass();
				while (cls != null && cls != Object.class) {
					Field[] fields = cls.getDeclaredFields();
					for (Field field : fields) {
						Object property = getEmptyObject(field.getType(),
								emptyInstances, level + 1);
						if (property != null) {
							try {
								if (!field.isAccessible()) {
									field.setAccessible(true);
								}
								field.set(value, property);
							} catch (Throwable e) {
							}
						}
					}
					cls = cls.getSuperclass();
				}
				return value;
			} catch (Throwable e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Returns the field of the given class or null if it doesnt exist.
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new RuntimeException("not allowed to access field " + field
					+ " on class " + clazz.getCanonicalName());
		} catch (NoSuchFieldException e) {
			// for some reason getDeclaredFields doesnt search superclasses
			// (which getFields() does ... but that gives only public fields)
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getField(fieldName, superClass);
			}
		}
		return field;
	}

	/**
	 * Returns the field of the given object or null if it doesnt exist.
	 */
	public static Field getField(String fieldName, Object object) {
		return getField(fieldName, object.getClass());
	}

	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = ReflectionUtils.findField(object.getClass(),
					fieldName);
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			return ReflectionUtils.getField(field, object);
		} catch (Exception ex) {
			try {
				return BeanUtils.getProperty(object, fieldName);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static Class<?> getGenericClass(Class<?> cls) {
		return getGenericClass(cls, 0);
	}

	public static Class<?> getGenericClass(Class<?> cls, int i) {
		try {
			ParameterizedType parameterizedType = ((ParameterizedType) cls
					.getGenericInterfaces()[0]);
			Object genericClass = parameterizedType.getActualTypeArguments()[i];
			if (genericClass instanceof ParameterizedType) { // 处理多级泛型
				return (Class<?>) ((ParameterizedType) genericClass)
						.getRawType();
			} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
				return (Class<?>) ((GenericArrayType) genericClass)
						.getGenericComponentType();
			} else {
				return (Class<?>) genericClass;
			}
		} catch (Throwable e) {
			throw new IllegalArgumentException(cls.getName()
					+ " generic type undefined!", e);
		}
	}

	/**
	 * get name. java.lang.Object[][].class => "java.lang.Object[][]"
	 * 
	 * @param c
	 *            class.
	 * @return name.
	 */
	public static String getName(Class<?> c) {
		if (c.isArray()) {
			StringBuilder sb = new StringBuilder();
			do {
				sb.append("[]");
				c = c.getComponentType();
			} while (c.isArray());

			return c.getName() + sb.toString();
		}
		return c.getName();
	}

	/**
	 * get constructor name. "()", "(java.lang.String,int)"
	 * 
	 * @param c
	 *            constructor.
	 * @return name.
	 */
	public static String getName(final Constructor<?> c) {
		StringBuilder ret = new StringBuilder("(");
		Class<?>[] parameterTypes = c.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			if (i > 0)
				ret.append(',');
			ret.append(getName(parameterTypes[i]));
		}
		ret.append(')');
		return ret.toString();
	}

	/**
	 * get method name. "void do(int)", "void do()",
	 * "int do(java.lang.String,boolean)"
	 * 
	 * @param m
	 *            method.
	 * @return name.
	 */
	public static String getName(final Method m) {
		StringBuilder ret = new StringBuilder();
		ret.append(getName(m.getReturnType())).append(' ');
		ret.append(m.getName()).append('(');
		Class<?>[] parameterTypes = m.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			if (i > 0)
				ret.append(',');
			ret.append(getName(parameterTypes[i]));
		}
		ret.append(')');
		return ret.toString();
	}

	public static String getPropertyNameFromBeanReadMethod(Method method) {
		if (isBeanPropertyReadMethod(method)) {
			if (method.getName().startsWith("get")) {
				return method.getName().substring(3, 4).toLowerCase()
						+ method.getName().substring(4);
			}
			if (method.getName().startsWith("is")) {
				return method.getName().substring(2, 3).toLowerCase()
						+ method.getName().substring(3);
			}
		}
		return null;
	}

	public static String getPropertyNameFromBeanWriteMethod(Method method) {
		if (isBeanPropertyWriteMethod(method)) {
			return method.getName().substring(3, 4).toLowerCase()
					+ method.getName().substring(4);
		}
		return null;
	}

	public static URL getResource(String name) {
		URL url = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(name);
		}
		if (url == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			url = classLoader.getResource(name);
			if (url == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtils.class.getClassLoader();
				url = classLoader.getResource(name);
			}
		}

		return url;
	}

	public static InputStream getResourceAsStream(String name) {
		InputStream resourceStream = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			resourceStream = classLoader.getResourceAsStream(name);
		}

		if (resourceStream == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			resourceStream = classLoader.getResourceAsStream(name);
			if (resourceStream == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtils.class.getClassLoader();
				resourceStream = classLoader.getResourceAsStream(name);
			}
		}
		return resourceStream;
	}

	/**
	 * Returns the setter-method for the given field name or null if no setter
	 * exists.
	 */
	public static Method getSetter(String fieldName, Class<?> clazz,
			Class<?> fieldType) {
		String setterName = "set" + Character.toTitleCase(fieldName.charAt(0))
				+ fieldName.substring(1, fieldName.length());
		try {
			// Using getMathods(), getMathod(...) expects exact parameter type
			// matching and ignores inheritance-tree.
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(setterName)) {
					Class<?>[] paramTypes = method.getParameterTypes();
					if (paramTypes != null && paramTypes.length == 1
							&& paramTypes[0].isAssignableFrom(fieldType)) {
						return method;
					}
				}
			}
			return null;
		} catch (SecurityException e) {
			throw new RuntimeException("Not allowed to access method "
					+ setterName + " on class " + clazz.getCanonicalName());
		}
	}

	public static String getSignature(String methodName,
			Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(methodName);
		sb.append("(");
		if (parameterTypes != null && parameterTypes.length > 0) {
			boolean first = true;
			for (Class<?> type : parameterTypes) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append(type.getName());
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public static Object instantiate(String className) {
		try {
			Class<?> clazz = loadClass(className);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("couldn't instantiate class "
					+ className, e);
		}
	}

	public static Object instantiate(String className, Object[] args) {
		Class<?> clazz = loadClass(className);
		Constructor<?> constructor = findMatchingConstructor(clazz, args);
		if (constructor == null) {
			throw new RuntimeException("couldn't find constructor for "
					+ className + " with args " + Arrays.asList(args));
		}
		try {
			return constructor.newInstance(args);
		} catch (Exception e) {
			throw new RuntimeException("couldn't find constructor for "
					+ className + " with args " + Arrays.asList(args), e);
		}
	}

	public static Object invoke(Object object, String methodName) {
		try {
			Method method = ReflectionUtils.findMethod(object.getClass(),
					methodName);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			return ReflectionUtils.invokeMethod(method, object);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Object invoke(Object target, String methodName, Object[] args) {
		try {
			Class<? extends Object> clazz = target.getClass();
			Method method = findMethod(clazz, methodName, args);
			method.setAccessible(true);
			return method.invoke(target, args);
		} catch (Exception e) {
			throw new RuntimeException("couldn't invoke " + methodName + " on "
					+ target, e);
		}
	}

	public static boolean isBeanPropertyReadMethod(Method method) {
		return method != null
				&& Modifier.isPublic(method.getModifiers())
				&& !Modifier.isStatic(method.getModifiers())
				&& method.getReturnType() != void.class
				&& method.getDeclaringClass() != Object.class
				&& method.getParameterTypes().length == 0
				&& ((method.getName().startsWith("get") && method.getName()
						.length() > 3) || (method.getName().startsWith("is") && method
						.getName().length() > 2));
	}

	public static boolean isBeanPropertyWriteMethod(Method method) {
		return method != null && Modifier.isPublic(method.getModifiers())
				&& !Modifier.isStatic(method.getModifiers())
				&& method.getDeclaringClass() != Object.class
				&& method.getParameterTypes().length == 1
				&& method.getName().startsWith("set")
				&& method.getName().length() > 3;
	}

	/**
	 * is compatible.
	 * 
	 * @param c
	 *            class.
	 * @param o
	 *            instance.
	 * @return compatible or not.
	 */
	public static boolean isCompatible(Class<?> c, Object o) {
		boolean pt = c.isPrimitive();
		if (o == null)
			return !pt;

		if (pt) {
			if (c == int.class)
				c = Integer.class;
			else if (c == boolean.class)
				c = Boolean.class;
			else if (c == long.class)
				c = Long.class;
			else if (c == float.class)
				c = Float.class;
			else if (c == double.class)
				c = Double.class;
			else if (c == char.class)
				c = Character.class;
			else if (c == byte.class)
				c = Byte.class;
			else if (c == short.class)
				c = Short.class;
		}
		if (c == o.getClass())
			return true;
		return c.isInstance(o);
	}

	/**
	 * is compatible.
	 * 
	 * @param cs
	 *            class array.
	 * @param os
	 *            object array.
	 * @return compatible or not.
	 */
	public static boolean isCompatible(Class<?>[] cs, Object[] os) {
		int len = cs.length;
		if (len != os.length)
			return false;
		if (len == 0)
			return true;
		for (int i = 0; i < len; i++)
			if (!isCompatible(cs[i], os[i]))
				return false;
		return true;
	}

	/**
	 * 检查对象是否是指定接口的实现。
	 * <p>
	 * 不会触发到指定接口的{@link Class}，所以如果ClassLoader中没有指定接口类时，也不会出错。
	 * 
	 * @param obj
	 *            要检查的对象
	 * @param interfaceClazzName
	 *            指定的接口名
	 * @return 返回{@code true}，如果对象实现了指定接口；否则返回{@code false}。
	 */
	public static boolean isInstance(Object obj, String interfaceClazzName) {
		for (Class<?> clazz = obj.getClass(); clazz != null
				&& !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> itf : interfaces) {
				if (itf.getName().equals(interfaceClazzName)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isPrimitive(Class<?> cls) {
		return cls.isPrimitive() || cls == String.class || cls == Boolean.class
				|| cls == Character.class || Number.class.isAssignableFrom(cls)
				|| Date.class.isAssignableFrom(cls);
	}

	public static boolean isPrimitives(Class<?> cls) {
		if (cls.isArray()) {
			return isPrimitive(cls.getComponentType());
		}
		return isPrimitive(cls);
	}

	public static boolean isPublicInstanceField(Field field) {
		return Modifier.isPublic(field.getModifiers())
				&& !Modifier.isStatic(field.getModifiers())
				&& !Modifier.isFinal(field.getModifiers())
				&& !field.isSynthetic();
	}

	public static Class<?> loadClass(String className) {
		Class<?> clazz = null;
		ClassLoader classLoader = getCustomClassLoader();

		// First exception in chain of classloaders will be used as cause when
		// no class is found in any of them
		Throwable throwable = null;

		if (classLoader != null) {
			try {
				logger.debug("Trying to load class with custom classloader: "
						+ className);
				clazz = Class.forName(className, true, classLoader);
			} catch (Throwable t) {
				throwable = t;
			}
		}
		if (clazz == null) {
			try {
				logger.debug("Trying to load class with current thread context classloader: "
						+ className);
				clazz = Class.forName(className, true, Thread.currentThread()
						.getContextClassLoader());
			} catch (Throwable t) {
				if (throwable == null) {
					throwable = t;
				}
			}
			if (clazz == null) {
				try {
					logger.debug("Trying to load class with local classloader: "
							+ className);
					clazz = Class.forName(className, true,
							ReflectUtils.class.getClassLoader());
				} catch (Throwable t) {
					if (throwable == null) {
						throwable = t;
					}
				}
			}
		}

		if (clazz == null) {
			throw new RuntimeException(className, throwable);
		}
		return clazz;
	}

	private static boolean matches(Class<?>[] parameterTypes, Object[] args) {
		if ((parameterTypes == null) || (parameterTypes.length == 0)) {
			return ((args == null) || (args.length == 0));
		}
		if ((args == null) || (parameterTypes.length != args.length)) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if ((args[i] != null)
					&& (!parameterTypes[i].isAssignableFrom(args[i].getClass()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * name to desc. java.util.Map[][] => "[[Ljava/util/Map;"
	 * 
	 * @param name
	 *            name.
	 * @return desc.
	 */
	public static String name2desc(String name) {
		StringBuilder sb = new StringBuilder();
		int c = 0, index = name.indexOf('[');
		if (index > 0) {
			c = (name.length() - index) / 2;
			name = name.substring(0, index);
		}
		while (c-- > 0)
			sb.append("[");
		if ("void".equals(name))
			sb.append(JVM_VOID);
		else if ("boolean".equals(name))
			sb.append(JVM_BOOLEAN);
		else if ("byte".equals(name))
			sb.append(JVM_BYTE);
		else if ("char".equals(name))
			sb.append(JVM_CHAR);
		else if ("double".equals(name))
			sb.append(JVM_DOUBLE);
		else if ("float".equals(name))
			sb.append(JVM_FLOAT);
		else if ("int".equals(name))
			sb.append(JVM_INT);
		else if ("long".equals(name))
			sb.append(JVM_LONG);
		else if ("short".equals(name))
			sb.append(JVM_SHORT);
		else
			sb.append('L').append(name.replace('.', '/')).append(';');
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> theClass, Configuration conf) {
		T result;
		try {
			Constructor<T> meth = (Constructor<T>) CONSTRUCTOR_CACHE
					.get(theClass);
			if (meth == null) {
				meth = theClass.getDeclaredConstructor(EMPTY_ARRAY);
				meth.setAccessible(true);
				CONSTRUCTOR_CACHE.put(theClass, meth);
			}
			result = meth.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static void setField(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		}
	}

	public static void setFieldValue(Object target, String name, Class<?> type,
			Object value) {
		if (target == null || StringUtils.isEmpty(name)
				|| (value != null && !type.isAssignableFrom(value.getClass()))) {
			return;
		}
		Class<?> clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod(
					"set" + Character.toUpperCase(name.charAt(0))
							+ name.substring(1), type);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, value);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
			}
			try {
				Field field = clazz.getDeclaredField(name);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, value);
			} catch (Exception e) {
				if (LogUtils.isDebug()) {
					logger.debug(e);
				}
			}
		}
	}

	public static Constructor<?> getConstructor(Class<?> type,
			Class<?>[] parameterTypes) {
		try {
			Constructor<?> constructor = type
					.getDeclaredConstructor(parameterTypes);
			constructor.setAccessible(true);
			return constructor;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object newInstance(Class<?> type) {
		return newInstance(type, EMPTY_CLASS_ARRAY, null);
	}

	public static Object newInstance(Class<?> type, Class<?>[] parameterTypes,
			Object[] args) {
		return newInstance(getConstructor(type, parameterTypes), args);
	}

	public static Object newInstance(final Constructor<?> cstruct,
			final Object[] args) {
		boolean flag = cstruct.isAccessible();
		try {
			cstruct.setAccessible(true);
			Object result = cstruct.newInstance(args);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			cstruct.setAccessible(flag);
		}
	}

	public static void setFieldValue(Object target, String fieldName,
			Object fieldValue) {
		try {
			Field field = ReflectionUtils.findField(target.getClass(),
					fieldName);
			if (field != null && !Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			ReflectionUtils.setField(field, target, fieldValue);
		} catch (Exception ex) {
			try {
				BeanUtils.setProperty(target, fieldName, fieldValue);
			} catch (Exception e) {
			}
		}
	}
}