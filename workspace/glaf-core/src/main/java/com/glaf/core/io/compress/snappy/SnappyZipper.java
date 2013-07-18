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

package com.glaf.core.io.compress.snappy;

import java.io.*;

import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

public class SnappyZipper {

	private final static int BUFFER_SIZE = 1024 * 256;

	public static byte[] compress(byte[] input) throws IOException {
		return Snappy.compress(input);
	}

	public static void compress(InputStream is, OutputStream os)
			throws Exception {
		SnappyOutputStream sos = new SnappyOutputStream(os);

		byte[] buf = new byte[BUFFER_SIZE];

		int count = 0;
		while ((count = is.read(buf, 0, BUFFER_SIZE)) != -1) {
			sos.write(buf, 0, count);
		}

		sos.flush();
		sos.close();
	}

	public static void decompress(InputStream is, OutputStream os)
			throws Exception {
		SnappyInputStream sis = new SnappyInputStream(is);

		byte[] buf = new byte[BUFFER_SIZE];

		int count = 0;
		while ((count = sis.read(buf, 0, BUFFER_SIZE)) != -1) {
			os.write(buf, 0, count);
		}

		sis.close();
	}

	public static void main(String args[]) {
		if (args.length < 2) {
			System.err
					.println("Usage: SnappyZipper compress|decompress srcFile dstFile");
			return;
		}

		String cmd = args[0];
		String srcFile = args[1];
		String dstFile = args[2];

		try {
			if (cmd.equals("compress")) {
				if (dstFile != null) {
					System.out.println("compress");
					long start = System.currentTimeMillis();
					compress(new FileInputStream(srcFile),
							new FileOutputStream(dstFile));
					System.out.println((System.currentTimeMillis() - start)
							+ " MS");
				}
			} else if (cmd.equals("decompress")) {
				if (dstFile != null) {
					System.out.println("uncompress");
					long start = System.currentTimeMillis();
					decompress(new FileInputStream(srcFile),
							new FileOutputStream(dstFile));
					System.out.println((System.currentTimeMillis() - start)
							+ " MS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] uncompress(byte[] input) throws IOException {
		return Snappy.uncompress(input);
	}

	private SnappyZipper() {

	}

}