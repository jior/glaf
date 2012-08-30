package com.glaf.base.utils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;

import com.glaf.base.config.Configuration;

public class ReflectUtil {

	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	private static final Class<?>[] EMPTY_ARRAY = new Class[] {};

	private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = new ConcurrentHashMap<Class<?>, Constructor<?>>();

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

	public static ClassLoader getClassLoader() {
		ClassLoader loader = getCustomClassLoader();
		if (loader == null) {
			loader = Thread.currentThread().getContextClassLoader();
		}
		return loader;
	}

	private static ClassLoader getCustomClassLoader() {
		return null;
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
				classLoader = ReflectUtil.class.getClassLoader();
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
				classLoader = ReflectUtil.class.getClassLoader();
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
							ReflectUtil.class.getClassLoader());
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
			try {
				Field field = clazz.getDeclaredField(name);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, value);
			} catch (Exception e) {
			}
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
