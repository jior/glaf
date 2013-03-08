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

/**
 * �����ؽ�����Դд�뵽ָ������������档
 * <p>
 * <code>OutputEngine</code>��ʲô�ã�����˵���� <code>GZIPInputStream</code>�Ƕ�ѹ�������н�ѹ����
 * </p>
 * <p/>
 * 
 * <pre>
 * read ԭʼ���� &lt;- decompress &lt;- compressed data stream
 * </pre>
 * <p>
 * <code>GZIPOutputStream</code>�Ƕ����������ѹ����
 * </p>
 * <p/>
 * 
 * <pre>
 * write ԭʼ���� -&gt; compress -&gt; compressed data stream
 * </pre>
 * <p>
 * ����JDK�в���������һ������
 * </p>
 * <p/>
 * 
 * <pre>
 * read compressed data &lt;- compress &lt;- ԭʼ������
 * </pre>
 * <p>
 * ����OutputEngine�Ϳ���ʵ������������
 * </p>
 * <p>
 * ��������ֲ��IBM developer works���£�
 * </p>
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
public interface OutputEngine {
	/** ����������Ĺ���. */
	interface OutputStreamFactory {
		/**
		 * ���������, ͨ������һ��<code>FilterOutputStream</code>���ӵ�ָ�����������.
		 * 
		 * @param out
		 *            �����ָ���������
		 * @return �����
		 * @throws IOException
		 *             ��������쳣
		 */
		OutputStream getOutputStream(OutputStream out) throws IOException;
	}

	/** Ĭ�ϵ����������, ֱ�ӷ���ָ���������. */
	OutputStreamFactory DEFAULT_OUTPUT_STREAM_FACTORY = new OutputStreamFactory() {
		public OutputStream getOutputStream(OutputStream out) {
			return out;
		}
	};

	/**
	 * ɨβ����. �����е����������Ժ�, �˷���������.
	 * 
	 * @throws IOException
	 *             ��������쳣
	 */
	void close() throws IOException;

	/**
	 * ִ��һ���������. �˲�����<code>OutputEngine</code>���������лᱻִ�ж��,
	 * ÿ�ζ�����������д�뵽��ʼ��ʱָ���������.
	 * 
	 * @throws IOException
	 *             ��������쳣
	 */
	void execute() throws IOException;

	/**
	 * ��ʼ���������, ͨ��<code>OutputEngine</code>��ʵ�ֻὫһ��
	 * <code>FilterOutputStream</code>���ӵ�ָ�����������.
	 * 
	 * @param out
	 *            �����ָ���������
	 * @throws IOException
	 *             ��������쳣
	 */
	void open(OutputStream out) throws IOException;
}
