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
 * ��ͬ����<code>ByteArrayOutputStream</code>�滻����, ִ��<code>toByteArray()</code>
 * ����ʱ���ص���ֻ�����ڲ��ֽ�����, ������û�б�Ҫ���ֽڸ���. ��������ֲ��IBM developer works���£�
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
public class ByteArrayOutputStream extends OutputStream {
	private static final int DEFAULT_INITIAL_BUFFER_SIZE = 8192;

	// internal buffer
	private byte[] buffer;
	private int index;
	private int capacity;

	// is the stream closed?
	private boolean closed;

	// is the buffer shared?
	private boolean shared;

	public ByteArrayOutputStream() {
		this(DEFAULT_INITIAL_BUFFER_SIZE);
	}

	public ByteArrayOutputStream(int initialBufferSize) {
		capacity = initialBufferSize;
		buffer = new byte[capacity];
	}

	@Override
	public void close() {
		closed = true;
	}

	public void reset() throws IOException {
		if (closed) {
			throw new IOException("Stream closed");
		} else {
			if (shared) {
				// create a new buffer if it is shared
				buffer = new byte[capacity];
				shared = false;
			}

			// reset index
			index = 0;
		}
	}

	public ByteArray toByteArray() {
		shared = true;
		return new ByteArray(buffer, 0, index);
	}

	public InputStream toInputStream() {
		// return a stream reading from the shared internal buffer
		shared = true;
		return new ByteArrayInputStream(buffer, 0, index);
	}

	@Override
	public void write(byte[] data, int offset, int length) throws IOException {
		if (data == null) {
			throw new NullPointerException();
		} else if (offset < 0 || offset + length > data.length || length < 0) {
			throw new IndexOutOfBoundsException();
		} else if (closed) {
			throw new IOException("Stream closed");
		} else {
			if (index + length > capacity) {
				// expand the internal buffer
				capacity = capacity * 2 + length;

				byte[] tmp = new byte[capacity];

				System.arraycopy(buffer, 0, tmp, 0, index);
				buffer = tmp;

				// the new buffer is not shared
				shared = false;
			}

			// copy in the subarray
			System.arraycopy(data, offset, buffer, index, length);
			index += length;
		}
	}

	@Override
	public void write(int datum) throws IOException {
		if (closed) {
			throw new IOException("Stream closed");
		} else {
			if (index >= capacity) {
				// expand the internal buffer
				capacity = capacity * 2 + 1;

				byte[] tmp = new byte[capacity];

				System.arraycopy(buffer, 0, tmp, 0, index);
				buffer = tmp;

				// the new buffer is not shared
				shared = false;
			}

			// store the byte
			buffer[index++] = (byte) datum;
		}
	}

	public void writeTo(OutputStream out) throws IOException {
		// write the internal buffer directly
		out.write(buffer, 0, index);
	}
}
