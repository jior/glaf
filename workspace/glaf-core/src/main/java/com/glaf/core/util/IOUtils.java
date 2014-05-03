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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.commons.logging.Log;

import com.glaf.core.config.Configuration;

/**
 * An utility class for I/O related functionality.
 */
public class IOUtils {

	/**
	 * /dev/null of OutputStreams.
	 */
	public static class NullOutputStream extends OutputStream {
		public void write(byte[] b, int off, int len) throws IOException {
		}

		public void write(int b) throws IOException {
		}
	}

	public static final int BUFFER_SIZE = 1024 * 8;

	/**
	 * append lines.
	 * 
	 * @param file
	 *            file.
	 * @param lines
	 *            lines.
	 * @throws IOException
	 */
	public static void appendLines(File file, String[] lines)
			throws IOException {
		if (file == null)
			throw new IOException("File is null.");
		writeLines(new FileOutputStream(file, true), lines);
	}

	/**
	 * Close the Closeable objects and <b>ignore</b> any {@link IOException} or
	 * null pointers. Must only be used for cleanup in exception handlers.
	 * 
	 * @param log
	 *            the log to record problems to at debug level. Can be null.
	 * @param closeables
	 *            the objects to close
	 */
	public static void cleanup(Log log, java.io.Closeable... closeables) {
		for (java.io.Closeable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {
					if (log != null && log.isDebugEnabled()) {
						log.debug("Exception in closing " + c, e);
					}
				}
			}
		}
	}

	/**
	 * Closes the socket ignoring {@link IOException}
	 * 
	 * @param sock
	 *            the Socket to close
	 */
	public static void closeSocket(Socket sock) {
		// avoids try { close() } dance
		if (sock != null) {
			try {
				sock.close();
			} catch (IOException ignored) {
			}
		}
	}

	/**
	 * Closes the stream ignoring {@link IOException}. Must only be called in
	 * cleaning up from exception handlers.
	 * 
	 * @param stream
	 *            the Stream to close
	 */
	public static void closeStream(java.io.Closeable stream) {
		cleanup(null, stream);
	}

	public static void closeStream(java.io.InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
				stream = null;
			} catch (IOException ex) {
			}
		}
	}

	public static void closeStream(java.io.OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
				stream = null;
			} catch (IOException ex) {
			}
		}
	}
	
	/**
	 * Copies from one stream to another. <strong>closes the input and output
	 * streams at the end</strong>.
	 * 
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param conf
	 *            the Configuration object
	 */
	public static void copyBytes(InputStream in, OutputStream out,
			Configuration conf) throws IOException {
		copyBytes(in, out, conf.getInt("io.file.buffer.size", 65536), true);
	}
	
	/**
	 * Copies from one stream to another.
	 * 
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param conf
	 *            the Configuration object
	 * @param close
	 *            whether or not close the InputStream and OutputStream at the
	 *            end. The streams are closed in the finally clause.
	 */
	public static void copyBytes(InputStream in, OutputStream out,
			Configuration conf, boolean close) throws IOException {
		copyBytes(in, out, conf.getInt("io.file.buffer.size", 65536), close);
	}

	/**
	 * Copies from one stream to another.
	 * 
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param buffSize
	 *            the size of the buffer
	 */
	@SuppressWarnings("resource")
	public static void copyBytes(InputStream in, OutputStream out, int buffSize)
			throws IOException {
		PrintStream ps = out instanceof PrintStream ? (PrintStream) out : null;
		byte buf[] = new byte[buffSize];
		int bytesRead = in.read(buf);
		while (bytesRead >= 0) {
			out.write(buf, 0, bytesRead);
			if ((ps != null) && ps.checkError()) {
				throw new IOException("Unable to write to output stream.");
			}
			bytesRead = in.read(buf);
		}
	}

	/**
	 * Copies from one stream to another.
	 * 
	 * @param in
	 *            InputStrem to read from
	 * @param out
	 *            OutputStream to write to
	 * @param buffSize
	 *            the size of the buffer
	 * @param close
	 *            whether or not close the InputStream and OutputStream at the
	 *            end. The streams are closed in the finally clause.
	 */
	public static void copyBytes(InputStream in, OutputStream out,
			int buffSize, boolean close) throws IOException {
		try {
			copyBytes(in, out, buffSize);
		} finally {
			if (close) {
				out.close();
				in.close();
			}
		}
	}

 
	/**
	 * Copies the specified length of bytes from in to out.
	 * 
	 * @param in
	 *            InputStream to read from
	 * @param out
	 *            OutputStream to write to
	 * @param length
	 *            number of bytes to copy
	 * @param bufferSize
	 *            the size of the buffer
	 * @param close
	 *            whether to close the streams
	 * @throws IOException
	 *             if bytes can not be read or written
	 */
	public static void copyBytes(InputStream in, OutputStream out,
			final long length, final int bufferSize, final boolean close)
			throws IOException {
		final byte buf[] = new byte[bufferSize];
		try {
			int n = 0;
			for (long remaining = length; remaining > 0 && n != -1; remaining -= n) {
				final int toRead = remaining < buf.length ? (int) remaining
						: buf.length;
				n = in.read(buf, 0, toRead);
				if (n > 0) {
					out.write(buf, 0, n);
				}
			}

			if (close) {
				out.close();
				out = null;
				in.close();
				in = null;
			}
		} finally {
			if (close) {
				closeStream(out);
				closeStream(in);
			}
		}
	}

	/**
	 * read string.
	 * 
	 * @param reader
	 *            Reader instance.
	 * @return String.
	 * @throws IOException
	 */
	public static String read(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		try {
			write(reader, writer);
			return writer.getBuffer().toString();
		} finally {
			writer.close();
		}
	}

	/**
	 * Reads len bytes in a loop using the channel of the stream
	 * 
	 * @param fileChannel
	 *            a FileChannel to read len bytes into buf
	 * @param buf
	 *            The buffer to fill
	 * @param off
	 *            offset from the buffer
	 * @param len
	 *            the length of bytes to read
	 * @throws IOException
	 *             if it could not read requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void readFileChannelFully(FileChannel fileChannel,
			byte buf[], int off, int len) throws IOException {
		int toRead = len;
		ByteBuffer byteBuffer = ByteBuffer.wrap(buf, off, len);
		while (toRead > 0) {
			int ret = fileChannel.read(byteBuffer);
			if (ret < 0) {
				throw new IOException("Premeture EOF from inputStream");
			}
			toRead -= ret;
			off += ret;
		}
	}

	/**
	 * Reads len bytes in a loop.
	 * 
	 * @param in
	 *            The InputStream to read from
	 * @param buf
	 *            The buffer to fill
	 * @param off
	 *            offset from the buffer
	 * @param len
	 *            the length of bytes to read
	 * @throws IOException
	 *             if it could not read requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void readFully(InputStream in, byte buf[], int off, int len)
			throws IOException {
		int toRead = len;
		while (toRead > 0) {
			int ret = in.read(buf, off, toRead);
			if (ret < 0) {
				throw new IOException("Premature EOF from inputStream");
			}
			toRead -= ret;
			off += ret;
		}
	}

	/**
	 * read lines.
	 * 
	 * @param file
	 *            file.
	 * @return lines.
	 * @throws IOException
	 */
	public static String[] readLines(File file) throws IOException {
		if (file == null || !file.exists() || !file.canRead())
			return new String[0];

		return readLines(new FileInputStream(file));
	}

	/**
	 * read lines.
	 * 
	 * @param is
	 *            input stream.
	 * @return lines.
	 * @throws IOException
	 */
	public static String[] readLines(InputStream is) throws IOException {
		List<String> lines = new java.util.ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String line;
			while ((line = reader.readLine()) != null){
				lines.add(line);
			}
			return lines.toArray(new String[0]);
		} finally {
			reader.close();
		}
	}

	/**
	 * Similar to readFully(). Skips bytes in a loop.
	 * 
	 * @param in
	 *            The InputStream to skip bytes from
	 * @param len
	 *            number of bytes to skip.
	 * @throws IOException
	 *             if it could not skip requested number of bytes for any reason
	 *             (including EOF)
	 */
	public static void skipFully(InputStream in, long len) throws IOException {
		while (len > 0) {
			long ret = in.skip(len);
			if (ret < 0) {
				throw new IOException("Premature EOF from inputStream");
			}
			len -= ret;
		}
	}

	/**
	 * write.
	 * 
	 * @param is
	 *            InputStream instance.
	 * @param os
	 *            OutputStream instance.
	 * @return count.
	 * @throws IOException.
	 */
	public static long write(InputStream is, OutputStream os)
			throws IOException {
		return write(is, os, BUFFER_SIZE);
	}

	/**
	 * write.
	 * 
	 * @param is
	 *            InputStream instance.
	 * @param os
	 *            OutputStream instance.
	 * @param bufferSize
	 *            buffer size.
	 * @return count.
	 * @throws IOException.
	 */
	public static long write(InputStream is, OutputStream os, int bufferSize)
			throws IOException {
		int read;
		long total = 0;
		byte[] buff = new byte[bufferSize];
		while (is.available() > 0) {
			read = is.read(buff, 0, buff.length);
			if (read > 0) {
				os.write(buff, 0, read);
				total += read;
			}
		}
		return total;
	}

	/**
	 * write.
	 * 
	 * @param reader
	 *            Reader.
	 * @param writer
	 *            Writer.
	 * @return count.
	 * @throws IOException
	 */
	public static long write(Reader reader, Writer writer) throws IOException {
		return write(reader, writer, BUFFER_SIZE);
	}

	/**
	 * write.
	 * 
	 * @param reader
	 *            Reader.
	 * @param writer
	 *            Writer.
	 * @param bufferSize
	 *            buffer size.
	 * @return count.
	 * @throws IOException
	 */
	public static long write(Reader reader, Writer writer, int bufferSize)
			throws IOException {
		int read;
		long total = 0;
		char[] buf = new char[BUFFER_SIZE];
		while ((read = reader.read(buf)) != -1) {
			writer.write(buf, 0, read);
			total += read;
		}
		return total;
	}

	/**
	 * write string.
	 * 
	 * @param writer
	 *            Writer instance.
	 * @param string
	 *            String.
	 * @throws IOException
	 */
	public static long write(Writer writer, String string) throws IOException {
		Reader reader = new StringReader(string);
		try {
			return write(reader, writer);
		} finally {
			reader.close();
		}
	}

	/**
	 * write lines.
	 * 
	 * @param file
	 *            file.
	 * @param lines
	 *            lines.
	 * @throws IOException
	 */
	public static void writeLines(File file, String[] lines) throws IOException {
		if (file == null)
			throw new IOException("File is null.");
		writeLines(new FileOutputStream(file), lines);
	}

	/**
	 * write lines.
	 * 
	 * @param os
	 *            output stream.
	 * @param lines
	 *            lines.
	 * @throws IOException
	 */
	public static void writeLines(OutputStream os, String[] lines)
			throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		try {
			for (String line : lines)
				writer.println(line);
			writer.flush();
		} finally {
			writer.close();
		}
	}
}