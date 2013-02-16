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
	 * ���жԳƼ���
	 * 
	 * @param tContent
	 *            ���������ġ�
	 * @param key
	 *            ������Կ
	 * @return byte[] ���ܺ�����
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
	 * ���жԳƽ���
	 * 
	 * @param cipherContent
	 *            ���������ġ�
	 * @param key
	 *            ��Կ
	 * @return byte[] ���ܺ�����
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
	 * ��˽Կ�Դ�ǩ�����ݽ���ǩ�����γ�ǩ������
	 * 
	 * @param content
	 *            ��ǩ������
	 * @param privateKey
	 *            ˽Կ
	 * @return byte[] ǩ����
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
	 * ��Կ��֤ǩ��
	 * 
	 * @param source
	 *            ԭ��
	 * @param signed
	 *            ǩ����Ϣ
	 * @param pubKey
	 *            ��Կ
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean verify(byte[] source, byte[] signed,
			PublicKey publicKey, SecurityContext ctx) throws Exception {
		if (publicKey == null) {
			throw new NullPointerException("��Կ����Ϊ��!");
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
	 * �ӿͻ��˵�keystore�õ�֤��
	 * 
	 * @return X509Certificate ֤��
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
	 * �ӿͻ��˵�keystore�õ�˽Կ
	 * 
	 * @return key ˽Կ
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
	 * ���ɶԳƼ�������Կ
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
	 * �÷��ͷ�����Կ����ǰ���õļ����öԳ���Կ,�γ������ŷ�
	 * 
	 * @param symmetryKey
	 *            �Գ���Կ
	 * @param pubKey
	 *            ��Կ
	 * @return String(��base64����)
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
	 * ���շ����Լ���˽Կ�⿪�����ŷ⣬�õ��Գ���Կ
	 * 
	 * @param envelope
	 *            �����ŷ�
	 * @param privateKey
	 *            ˽Կ
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