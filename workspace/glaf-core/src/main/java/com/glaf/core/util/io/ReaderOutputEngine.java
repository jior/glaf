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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * �����ݴ�����<code>Reader</code>���Ƶ�<code>OutputStreamWriter</code>���������. ��������ֲ��IBM
 * developer works���£�
 * <ul>
 * <li><a
 * href="http://www.ibm.com/developerworks/cn/java/j-io1/index.shtml">����ת�������� 1
 * ���֣���������ж�ȡ</a>
 * <li><a
 * href="http://www.ibm.com/developerworks/cn/java/j-io2/index.shtml">����ת�������� 2
 * ���֣��Ż� Java �ڲ� I/O</a>
 * </ul>
 * 
 * 
 */
public class ReaderOutputEngine implements OutputEngine {
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	private Reader reader;
	private String encoding;
	private OutputStreamFactory factory;
	private char[] buffer;
	private Writer writer;

	public ReaderOutputEngine(Reader reader) {
		this(reader, null, null, DEFAULT_BUFFER_SIZE);
	}

	public ReaderOutputEngine(Reader reader, OutputStreamFactory factory) {
		this(reader, factory, null, DEFAULT_BUFFER_SIZE);
	}

	public ReaderOutputEngine(Reader reader, OutputStreamFactory factory,
			String encoding) {
		this(reader, factory, encoding, DEFAULT_BUFFER_SIZE);
	}

	public ReaderOutputEngine(Reader reader, OutputStreamFactory factory,
			String encoding, int bufferSize) {
		this.reader = reader;
		this.encoding = encoding;
		this.factory = factory == null ? DEFAULT_OUTPUT_STREAM_FACTORY
				: factory;
		buffer = new char[bufferSize];
	}

	public void close() throws IOException {
		reader.close();
	}

	public void execute() throws IOException {
		if (writer == null) {
			throw new IOException("Not yet initialized");
		} else {
			int amount = reader.read(buffer);

			if (amount < 0) {
				writer.close();
			} else {
				writer.write(buffer, 0, amount);
			}
		}
	}

	public void open(OutputStream out) throws IOException {
		if (writer != null) {
			throw new IOException("Already initialized");
		} else {
			writer = encoding == null ? new OutputStreamWriter(
					factory.getOutputStream(out)) : new OutputStreamWriter(
					factory.getOutputStream(out), encoding);
		}
	}
}
