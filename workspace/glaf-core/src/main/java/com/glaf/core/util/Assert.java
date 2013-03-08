/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.util;

import static com.glaf.core.util.Assert.ExceptionType.*;

/**
 * ���Թ��ߣ�����ʵ��<a
 * href="http://martinfowler.com/ieeeSoftware/failFast.pdf">Fail-Fast</a>��
 * <p>
 * <strong>ע�����</strong>
 * </p>
 * <ul>
 * <li>Assertion��������ȷ���ó����е�������Լ���ģ�����һ�ֳ���Ա֮�佻���Ĺ��ߣ������Ǻ������û������Ĺ��ߡ�</li>
 * <li>һ������������Ԫ���Ե���ȷ���򣬲�Ӧ��ʹ�κ�һ��assertionʧ�ܡ�</li>
 * <li>Ӧ������ڸ��ӵ�assertion��������ռ�ù��������ʱ�䡣����Assertionʧ�ܡ�</li>
 * <li>Assertion�ĳ�����Ϣ���Ǹ������û����ģ����û��Ҫд�ù�����ϸ����û��Ҫ���ǹ��ʻ������⣬�������˷�CPU��ʱ�䡣
 * ��������������Ҫ����ģ�
 * <p/>
 * 
 * <pre>
 * assertTrue(type instanceof MyType, &quot;Unsupported type: &quot; + type);
 * </pre>
 * 
 * <p/>
 * �����滻�ɣ�
 * <p/>
 * 
 * <pre>
 * assertTrue(type instanceof MyType, &quot;Unsupported type: %s&quot;, type);
 * </pre>
 * 
 * <p/>
 * ��������assertion˳��ͨ��ʱ������ռ��CPUʱ�䡣</li>
 * </ul>
 * <p>
 * ���⣬���ַ������з���ֵ���Է����̣����磺
 * </p>
 * <p/>
 * 
 * <pre>
 * void foo(String param) {
 *     bar(assertNotNull(param));
 * }
 * 
 * int bar(String param) {
 *     if (true) {
 *         ...
 *     }
 * 
 *     return unreachableCode();
 * }
 * </pre>
 * 
 */
public final class Assert {
	/** ȷ������Ϊ�գ������׳�<code>IllegalArgumentException</code>�� */
	public static <T> T assertNotNull(T object) {
		return assertNotNull(object, null, null, (Object[]) null);
	}

	/** ȷ������Ϊ�գ������׳�<code>IllegalArgumentException</code>�� */
	public static <T> T assertNotNull(T object, String message, Object... args) {
		return assertNotNull(object, null, message, args);
	}

	/** ȷ������Ϊ�գ������׳�ָ���쳣��Ĭ��Ϊ<code>IllegalArgumentException</code>�� */
	public static <T> T assertNotNull(T object, ExceptionType exceptionType,
			String message, Object... args) {
		if (object == null) {
			if (exceptionType == null) {
				exceptionType = ILLEGAL_ARGUMENT;
			}

			throw exceptionType
					.newInstance(getMessage(message, args,
							"[Assertion failed] - the argument is required; it must not be null"));
		}

		return object;
	}

	/** ȷ������Ϊ�գ������׳�<code>IllegalArgumentException</code>�� */
	public static <T> T assertNull(T object) {
		return assertNull(object, null, null, (Object[]) null);
	}

	/** ȷ������Ϊ�գ������׳�<code>IllegalArgumentException</code>�� */
	public static <T> T assertNull(T object, String message, Object... args) {
		return assertNull(object, null, message, args);
	}

	/** ȷ������Ϊ�գ������׳�ָ���쳣��Ĭ��Ϊ<code>IllegalArgumentException</code>�� */
	public static <T> T assertNull(T object, ExceptionType exceptionType,
			String message, Object... args) {
		if (object != null) {
			if (exceptionType == null) {
				exceptionType = ILLEGAL_ARGUMENT;
			}

			throw exceptionType.newInstance(getMessage(message, args,
					"[Assertion failed] - the object argument must be null"));
		}

		return object;
	}

	/** ȷ�����ʽΪ�棬�����׳�<code>IllegalArgumentException</code>�� */
	public static void assertTrue(boolean expression) {
		assertTrue(expression, null, null, (Object[]) null);
	}

	/** ȷ�����ʽΪ�棬�����׳�<code>IllegalArgumentException</code>�� */
	public static void assertTrue(boolean expression, String message,
			Object... args) {
		assertTrue(expression, null, message, args);
	}

	/** ȷ�����ʽΪ�棬�����׳�ָ���쳣��Ĭ��Ϊ<code>IllegalArgumentException</code>�� */
	public static void assertTrue(boolean expression,
			ExceptionType exceptionType, String message, Object... args) {
		if (!expression) {
			if (exceptionType == null) {
				exceptionType = ILLEGAL_ARGUMENT;
			}

			throw exceptionType.newInstance(getMessage(message, args,
					"[Assertion failed] - the expression must be true"));
		}
	}

	/** �����ܵ���Ĵ��롣 */
	public static <T> T unreachableCode() {
		unreachableCode(null, (Object[]) null);
		return null;
	}

	/** �����ܵ���Ĵ��롣 */
	public static <T> T unreachableCode(String message, Object... args) {
		throw UNREACHABLE_CODE.newInstance(getMessage(message, args,
				"[Assertion failed] - the code is expected as unreachable"));
	}

	/** �����ܷ������쳣�� */
	public static <T> T unexpectedException(Throwable e) {
		unexpectedException(e, null, (Object[]) null);
		return null;
	}

	/** �����ܷ������쳣�� */
	public static <T> T unexpectedException(Throwable e, String message,
			Object... args) {
		RuntimeException exception = UNEXPECTED_FAILURE.newInstance(getMessage(
				message, args,
				"[Assertion failed] - unexpected exception is thrown"));

		exception.initCause(e);

		throw exception;
	}

	/** δԤ�ϵ�ʧ�ܡ� */
	public static <T> T fail() {
		fail(null, (Object[]) null);
		return null;
	}

	/** δԤ�ϵ�ʧ�ܡ� */
	public static <T> T fail(String message, Object... args) {
		throw UNEXPECTED_FAILURE.newInstance(getMessage(message, args,
				"[Assertion failed] - unexpected failure"));
	}

	/** ��֧�ֵĲ����� */
	public static <T> T unsupportedOperation() {
		unsupportedOperation(null, (Object[]) null);
		return null;
	}

	/** ��֧�ֵĲ����� */
	public static <T> T unsupportedOperation(String message, Object... args) {
		throw UNSUPPORTED_OPERATION
				.newInstance(getMessage(message, args,
						"[Assertion failed] - unsupported operation or unimplemented function"));
	}

	/** ȡ�ô���������Ϣ�� */
	private static String getMessage(String message, Object[] args,
			String defaultMessage) {
		if (message == null) {
			message = defaultMessage;
		}

		if (args == null || args.length == 0) {
			return message;
		}

		return String.format(message, args);
	}

	/** Assertion�������͡� */
	public static enum ExceptionType {
		ILLEGAL_ARGUMENT {
			@Override
			RuntimeException newInstance(String message) {
				return new IllegalArgumentException(message);
			}
		},

		ILLEGAL_STATE {
			@Override
			RuntimeException newInstance(String message) {
				return new IllegalStateException(message);
			}
		},

		NULL_POINT {
			@Override
			RuntimeException newInstance(String message) {
				return new NullPointerException(message);
			}
		},

		UNREACHABLE_CODE {
			@Override
			RuntimeException newInstance(String message) {
				return new RuntimeException(message);
			}
		},

		UNEXPECTED_FAILURE {
			@Override
			RuntimeException newInstance(String message) {
				return new RuntimeException(message);
			}
		},

		UNSUPPORTED_OPERATION {
			@Override
			RuntimeException newInstance(String message) {
				return new UnsupportedOperationException(message);
			}
		};

		abstract RuntimeException newInstance(String message);
	}
}
