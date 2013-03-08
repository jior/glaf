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

package com.glaf.core.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * �������Ĺ����ࡣ
 * 
 */
public class StreamUtil {
	private static final int DEFAULT_BUFFER_SIZE = 8192;

	/** ����������ȡ���ݣ�д�뵽������С� */
	public static void io(InputStream in, OutputStream out, boolean closeIn,
			boolean closeOut) throws IOException {
		int bufferSize = DEFAULT_BUFFER_SIZE;
		byte[] buffer = new byte[bufferSize];
		int amount;

		try {
			while ((amount = in.read(buffer)) >= 0) {
				out.write(buffer, 0, amount);
			}

			out.flush();
		} finally {
			if (closeIn) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}

			if (closeOut) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/** ����������ȡ���ݣ�д�뵽������С� */
	public static void io(Reader in, Writer out, boolean closeIn,
			boolean closeOut) throws IOException {
		int bufferSize = DEFAULT_BUFFER_SIZE >> 1;
		char[] buffer = new char[bufferSize];
		int amount;

		try {
			while ((amount = in.read(buffer)) >= 0) {
				out.write(buffer, 0, amount);
			}

			out.flush();
		} finally {
			if (closeIn) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}

			if (closeOut) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/** ��ָ��<code>InputStream</code>����������ȫ��������һ��byte�����С� */
	public static ByteArray readBytes(InputStream in, boolean closeIn)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		io(in, out, closeIn, true);

		return out.toByteArray();
	}

	/** ��ָ���������������ı�ȫ��������һ���ַ����С� */
	public static String readText(InputStream in, String charset,
			boolean closeIn) throws IOException {
		Reader reader = charset == null ? new InputStreamReader(in)
				: new InputStreamReader(in, charset);

		return readText(reader, closeIn);
	}

	/** ��ָ��<code>Reader</code>�������ı�ȫ��������һ���ַ����С� */
	public static String readText(Reader in, boolean closeIn)
			throws IOException {
		StringWriter out = new StringWriter();

		io(in, out, closeIn, true);

		return out.toString();
	}

	/** ��byte����д�뵽ָ��<code>OutputStream</code>�С� */
	public static void writeBytes(byte[] bytes, OutputStream out,
			boolean closeOut) throws IOException {
		writeBytes(new ByteArray(bytes), out, closeOut);
	}

	/** ��byte����д�뵽ָ��<code>OutputStream</code>�С� */
	public static void writeBytes(ByteArray bytes, OutputStream out,
			boolean closeOut) throws IOException {
		try {
			out.write(bytes.getRawBytes(), bytes.getOffset(), bytes.getLength());
			out.flush();
		} finally {
			if (closeOut) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/** ���ַ���д�뵽ָ��������С� */
	public static void writeText(CharSequence chars, OutputStream out,
			String charset, boolean closeOut) throws IOException {
		Writer writer = charset == null ? new OutputStreamWriter(out)
				: new OutputStreamWriter(out, charset);

		writeText(chars, writer, closeOut);
	}

	/** ���ַ���д�뵽ָ��<code>Writer</code>�С� */
	public static void writeText(CharSequence chars, Writer out,
			boolean closeOut) throws IOException {
		try {
			out.write(chars.toString());
			out.flush();
		} finally {
			if (closeOut) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
