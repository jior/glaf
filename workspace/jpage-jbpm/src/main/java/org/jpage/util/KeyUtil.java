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

package org.jpage.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtil {

	private static KeyFactory keyFactory;

	private static KeyPairGenerator keyPairGenerator;

	static {
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("DSA");
			SecureRandom securerandom = new SecureRandom();
			keyPairGenerator.initialize(1024, new SecureRandom(securerandom
					.generateSeed(8)));
			keyFactory = KeyFactory.getInstance("DSA");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	}

	private KeyUtil() {
	}

	public static PublicKey getPublic(byte[] encodedKey)
			throws InvalidKeySpecException {
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	public static PrivateKey getPrivate(byte[] encodedKey)
			throws InvalidKeySpecException {
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	public static KeyPair getKeyPair() {
		return keyPairGenerator.genKeyPair();
	}

	public static void main(String[] args) throws Exception {

	}
}