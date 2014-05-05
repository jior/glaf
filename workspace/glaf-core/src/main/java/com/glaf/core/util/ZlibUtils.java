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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * ZLib压缩工具
 * 
 */
public class ZlibUtils {
	private static final int BUFFER = 8192;

	/**
	 * 压缩字节流
	 * 
	 * @param data
	 *            待压缩数据
	 * @return byte[] 压缩后的数据
	 */
	public static byte[] compress(byte[] data) {
		byte[] output = null;
		Deflater compresser = new Deflater();
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream(data.length);
			byte[] buff = new byte[BUFFER];
			while (!compresser.finished()) {
				int i = compresser.deflate(buff);
				baos.write(buff, 0, i);
			}
			output = baos.toByteArray();
			compresser.end();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
		return output;
	}

	/**
	 * 压缩字节流
	 * 
	 * @param data
	 *            待压缩数据
	 * 
	 * @param os
	 *            输出流
	 */
	public static void compress(byte[] data, OutputStream os) {
		DeflaterOutputStream dos = null;
		try {
			dos = new DeflaterOutputStream(os);
			dos.write(data, 0, data.length);
			dos.finish();
			dos.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(dos);
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param data
	 *            已经压缩的数据
	 * @return byte[] 解压缩后的数据
	 */
	public static byte[] uncompress(byte[] data) {
		byte[] output = null;
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream(data.length);
			byte[] buf = new byte[BUFFER];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				baos.write(buf, 0, i);
			}
			output = baos.toByteArray();
			decompresser.end();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
		return output;
	}

	/**
	 * 解压缩
	 * 
	 * @param inputStream
	 *            已经压缩的数据输入流
	 * @return byte[] 解压缩后的数据
	 */
	public static byte[] uncompress(InputStream inputStream) {
		byte[] bytes = null;
		InflaterInputStream iis = null;
		ByteArrayOutputStream baos = null;
		try {
			iis = new InflaterInputStream(inputStream);
			baos = new ByteArrayOutputStream(BUFFER);
			int i = BUFFER;
			byte[] buff = new byte[i];
			while ((i = iis.read(buff, 0, i)) > 0) {
				baos.write(buff, 0, i);
			}
			bytes = baos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(iis);
			IOUtils.closeStream(baos);
		}
		return bytes;
	}

	public static void main(String[] args) {
		String filename = "user.xml";
		if (args != null && args.length == 1) {
			filename = args[0];
		}
		byte[] bytes = FileUtils.getBytes(filename);
		byte[] input = null;
		byte[] zipBytes = null;
		try {
			zipBytes = ZlibUtils.compress(bytes);
			FileUtils.save("output", zipBytes);
			input = FileUtils.getBytes("output");
			bytes = ZlibUtils.uncompress(input);
			FileUtils.save("input", bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
