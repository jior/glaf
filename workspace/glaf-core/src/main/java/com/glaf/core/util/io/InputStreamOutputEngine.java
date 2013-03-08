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
import java.io.OutputStream;

/**
 * �����ݴ�����<code>InputStream</code>���Ƶ�<code>FilterOutputStream</code>���������.
 * ��������ֲ��IBM developer works���£�
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
public class InputStreamOutputEngine implements OutputEngine {
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	private InputStream in;
	private OutputStreamFactory factory;
	private byte[] buffer;
	private OutputStream out;

	public InputStreamOutputEngine(InputStream in, OutputStreamFactory factory) {
		this(in, factory, DEFAULT_BUFFER_SIZE);
	}

	public InputStreamOutputEngine(InputStream in, OutputStreamFactory factory,
			int bufferSize) {
		this.in = in;
		this.factory = factory == null ? DEFAULT_OUTPUT_STREAM_FACTORY
				: factory;
		buffer = new byte[bufferSize];
	}

	public void close() throws IOException {
		in.close();
	}

	public void execute() throws IOException {
		if (out == null) {
			throw new IOException("Not yet initialized");
		} else {
			int amount = in.read(buffer);

			if (amount < 0) {
				out.close();
			} else {
				out.write(buffer, 0, amount);
			}
		}
	}

	public void open(OutputStream out) throws IOException {
		if (this.out != null) {
			throw new IOException("Already initialized");
		} else {
			this.out = factory.getOutputStream(out);
		}
	}
}
