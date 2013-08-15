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

package com.glaf.core.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public class DigestUtil {

	protected static Configuration conf = BaseConfiguration.create();

	private DigestUtil() {
	}

	public static void digestFile(String filename, String algorithm) {
		byte[] b = new byte[65536];
		int read = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			fis = new FileInputStream(filename);
			while (fis.available() > 0) {
				read = fis.read(b);
				md.update(b, 0, read);
			}
			byte[] digest = md.digest();
			StringBuffer fileNameBuffer = new StringBuffer(256)
					.append(filename).append('.').append(algorithm);
			fos = new FileOutputStream(fileNameBuffer.toString());
			OutputStream encodedStream = MimeUtility.encode(fos, "base64");
			encodedStream.write(digest);
			fos.flush();
		} catch (Exception ex) {
			System.out.println("Error computing Digest: " + ex);
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (Exception ignored) {
			}
		}
	}

	public static String digestString(String password, String algorithm) {
		if (password == null || password.trim().length() == 0) {
			return password;
		}
		if (conf.getBoolean("password.enc", true)) {
			MessageDigest md = null;
			ByteArrayOutputStream bos = null;
			try {
				md = MessageDigest.getInstance(algorithm);
				byte[] digest = md.digest(password.getBytes("UTF-8"));
				bos = new ByteArrayOutputStream();
				OutputStream encodedStream = MimeUtility.encode(bos, "base64");
				encodedStream.write(digest);
				return bos.toString("UTF-8");
			} catch (IOException ioe) {
				throw new RuntimeException("Fatal error: " + ioe);
			} catch (NoSuchAlgorithmException ae) {
				throw new RuntimeException("Fatal error: " + ae);
			} catch (MessagingException me) {
				throw new RuntimeException("Fatal error: " + me);
			}
		}
		return password;
	}

	public static void main(String[] args) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update("111111".getBytes());
	}

}