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


package org.jpage.core.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jpage.util.Tools;

public class SecurityUtil {

	static {
		try {
			String provider = "org.bouncycastle.jce.provider.BouncyCastleProvider";
			java.security.Security.addProvider((Provider) Class.forName(
					provider).newInstance());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			System.out.println(cf.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private SecurityUtil() {
	}

	/**
	 * 进行对称加密
	 * 
	 * @param tContent
	 *            待加密明文。
	 * @param key
	 *            加密密钥
	 * @return byte[] 加密后密文
	 * @throws Exception
	 */
	public static byte[] symmetryEncrypt(byte[] tContent, Key key,
			SecurityContext ctx) throws Exception {
		byte[] cipherContent = null;
		Cipher cipher = Cipher.getInstance(ctx.getSymmetryAlgorithm(), ctx
				.getJceProvider());
		SecureRandom secureRandom = SecureRandom.getInstance(ctx
				.getSecureRandomAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
		cipherContent = cipher.doFinal(tContent);
		return cipherContent;
	}

	/**
	 * 进行对称解密
	 * 
	 * @param cipherContent
	 *            待解密密文。
	 * @param key
	 *            密钥
	 * @return byte[] 解密后明文
	 * 
	 * @throws Exception
	 */
	public static byte[] symmetryDecrypt(byte[] cipherContent, Key key,
			SecurityContext ctx) throws Exception {
		byte[] tContent = null;
		Cipher cipher = Cipher.getInstance(ctx.getSymmetryAlgorithm(), ctx
				.getJceProvider());
		SecureRandom secureRandom = SecureRandom.getInstance(ctx
				.getSecureRandomAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
		tContent = cipher.doFinal(cipherContent);
		return tContent;
	}

	/**
	 * 用私钥对待签名内容进行签名，形成签名流。
	 * 
	 * @param content
	 *            待签名内容
	 * @param privateKey
	 *            私钥
	 * @return byte[] 签名流
	 * @throws Exception
	 */
	public static byte[] sign(byte[] content, Key privateKey,
			SecurityContext ctx) throws Exception {
		Signature sign = Signature.getInstance(ctx.getSignatureAlgorithm(), ctx
				.getJceProvider());
		PrivateKey pk = (PrivateKey) privateKey;
		sign.initSign(pk);
		sign.update(content);
		byte[] signed = sign.sign();
		return signed;
	}

	/**
	 * 公钥验证签名
	 * 
	 * @param source
	 *            原文
	 * @param signed
	 *            签名信息
	 * @param pubKey
	 *            公钥
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean verify(byte[] source, byte[] signed,
			PublicKey publicKey, SecurityContext ctx) throws Exception {
		if (publicKey == null) {
			throw new NullPointerException("公钥不能为空!");
		}
		boolean verify = false;
		Signature sign = Signature.getInstance(ctx.getSignatureAlgorithm(), ctx
				.getJceProvider());
		sign.initVerify(publicKey);
		sign.update(source);
		verify = sign.verify(signed);
		return verify;
	}

	/**
	 * 从客户端的keystore得到证书
	 * 
	 * @return X509Certificate 证书
	 * @throws Exception
	 */

	public static X509Certificate getCertFromKeystore(
			InputStream ksInputStream, String password, String alias)
			throws Exception {
		X509Certificate x509cert = null;
		KeyStore ks = KeyStore.getInstance("JKS", "SUN");
		ks.load(ksInputStream, password.toCharArray());
		x509cert = (X509Certificate) ks.getCertificate(alias);
		return x509cert;
	}

	/**
	 * 从客户端的keystore得到私钥
	 * 
	 * @return key 私钥
	 * @throws Exception
	 */
	public static Key getPrivateKeyFromKeystore(InputStream ksInputStream,
			String password, String alias) throws Exception {
		Key privateKey = null;
		KeyStore ks = KeyStore.getInstance("JKS", "SUN");
		ks.load(ksInputStream, password.toCharArray());
		privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
		return privateKey;
	}

	/**
	 * 生成对称加密用密钥
	 * 
	 * @return key
	 * @throws Exception
	 */
	public static Key generateSecretKey(SecurityContext ctx) throws Exception {
		SecretKey key = null;
		KeyGenerator skg = null;
		SecureRandom secureRandom = null;
		skg = KeyGenerator.getInstance(ctx.getSymmetryKeyAlgorithm(), ctx
				.getJceProvider());
		secureRandom = SecureRandom.getInstance(ctx.getSecureRandomAlgorithm());
		skg.init(ctx.getSymmetryKeySize(), secureRandom);
		key = skg.generateKey();
		return key;
	}

	/**
	 * 用发送发方公钥加密前面用的加密用对称密钥,形成数字信封
	 * 
	 * @param symmetryKey
	 *            对称密钥
	 * @param pubKey
	 *            公钥
	 * @return String(经base64编码)
	 * @throws Exception
	 */
	public static String generateDigitalEnvelope(Key symmetryKey,
			byte[] pubKey, SecurityContext ctx) throws Exception {
		String result = null;
		InputStream inputStream = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			inputStream = new ByteArrayInputStream(pubKey);
			java.security.cert.Certificate cert = cf
					.generateCertificate(inputStream);
			inputStream.close();
			PublicKey publicKey = cert.getPublicKey();
			Cipher cipher = Cipher.getInstance(ctx.getAsymmetryAlgorithm());

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			result = Tools.encodeBASE64(cipher
					.doFinal(symmetryKey.getEncoded()));

			return result;

		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * 接收方用自己的私钥解开数字信封，得到对称密钥
	 * 
	 * @param envelope
	 *            数字信封
	 * @param privateKey
	 *            私钥
	 * @return key
	 * @throws Exception
	 */
	public static Key openDigitalEnvelope(String envelope, Key privateKey,
			SecurityContext ctx) throws Exception {
		Key symmetryKey = null;
		Cipher cipher = Cipher.getInstance(ctx.getAsymmetryAlgorithm(), ctx
				.getJceProvider());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		envelope = Tools.replaceIgnoreCase(envelope, " ", "");
		byte[] key = cipher.doFinal(Tools.decodeBASE64(envelope));

		SecretKeyFactory skf = SecretKeyFactory.getInstance(ctx
				.getSymmetryKeyAlgorithm(), ctx.getJceProvider());
		DESKeySpec keySpec = new DESKeySpec(key);
		symmetryKey = skf.generateSecret(keySpec);

		return symmetryKey;
	}

}