/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.security;

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
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import com.glaf.core.util.StringTools;

public class SecurityUtils {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	static {
		try {
			String provider = "org.bouncycastle.jce.provider.BouncyCastleProvider";
			java.security.Security.addProvider((Provider) Class.forName(
					provider).newInstance());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ������ת�ַ���
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append('0');
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * DES�㷨������
	 * 
	 * @param data
	 *            �������ַ���
	 * @param key
	 *            ����˽Կ�����Ȳ��ܹ�С��8λ
	 * @return ���ܺ���ֽ�����
	 */
	public static String decode(String key, String data) {
		if (data == null) {
			return null;
		}
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(data.getBytes())));
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * DES�㷨������
	 * 
	 * @param data
	 *            �������ַ���
	 * @param key
	 *            ����˽Կ�����Ȳ��ܹ�С��8λ
	 * @return ���ܺ���ֽ����飬һ����Base64����ʹ��
	 */
	public static String encode(String key, String data) {
		if (data == null) {
			return null;
		}
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return byte2hex(bytes);
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * �÷��ͷ�����Կ����ǰ���õļ����öԳ���Կ,�γ������ŷ�
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param symmetryKey
	 *            �Գ���Կ
	 * @param pubKey
	 *            ��Կ
	 * @return String(��base64����)
	 */
	public static String generateDigitalEnvelope(SecurityContext ctx,
			Key symmetryKey, byte[] pubKey) {
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
			result = Base64.encodeBase64String(cipher.doFinal(symmetryKey
					.getEncoded()));
			return result;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * ���ɶԳƼ�������Կ
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @return key
	 */
	public static Key generateSecretKey(SecurityContext ctx) {
		try {
			KeyGenerator skg = KeyGenerator.getInstance(
					ctx.getSymmetryKeyAlgorithm(), ctx.getJceProvider());
			SecureRandom secureRandom = SecureRandom.getInstance(ctx
					.getSecureRandomAlgorithm());
			skg.init(ctx.getSymmetryKeySize(), secureRandom);
			SecretKey key = skg.generateKey();
			return key;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * �ӿͻ��˵�keystore�õ�֤��
	 * 
	 * @return X509Certificate ֤��
	 */
	public static X509Certificate getCertFromKeystore(
			InputStream keystoreInputStream, String alias, String password) {
		try {
			X509Certificate x509cert = null;
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			ks.load(keystoreInputStream, password.toCharArray());
			x509cert = (X509Certificate) ks.getCertificate(alias);
			return x509cert;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * �ӿͻ��˵�keystore�õ�˽Կ
	 * 
	 * @return key ˽Կ
	 */
	public static Key getPrivateKeyFromKeystore(InputStream ksInputStream,
			String password, String alias) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			ks.load(ksInputStream, password.toCharArray());
			Key privateKey = (PrivateKey) ks.getKey(alias,
					password.toCharArray());
			return privateKey;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException();
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * ���շ����Լ���˽Կ�⿪�����ŷ⣬�õ��Գ���Կ
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param envelope
	 *            �����ŷ�
	 * @param privateKey
	 *            ˽Կ
	 * @return key �Գ���Կ
	 */
	public static Key openDigitalEnvelope(SecurityContext ctx, String envelope,
			Key privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(ctx.getAsymmetryAlgorithm(),
					ctx.getJceProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			envelope = StringTools.replaceIgnoreCase(envelope, " ", "");
			byte[] key = cipher.doFinal(Base64.decodeBase64(envelope));

			SecretKeyFactory skf = SecretKeyFactory.getInstance(
					ctx.getSymmetryKeyAlgorithm(), ctx.getJceProvider());
			DESKeySpec keySpec = new DESKeySpec(key);
			Key symmetryKey = skf.generateSecret(keySpec);

			return symmetryKey;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * ��˽Կ�Դ�ǩ�����ݽ���ǩ�����γ�ǩ������
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param content
	 *            ��ǩ������
	 * @param privateKey
	 *            ˽Կ
	 * @return byte[] ǩ����
	 */
	public static byte[] sign(SecurityContext ctx, byte[] content,
			Key privateKey) {
		try {
			Signature sign = Signature.getInstance(ctx.getSignatureAlgorithm(),
					ctx.getJceProvider());
			PrivateKey pk = (PrivateKey) privateKey;
			sign.initSign(pk);
			sign.update(content);
			byte[] signed = sign.sign();
			return signed;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * ���жԳƽ���
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param cipherContent
	 *            ���������ġ�
	 * @param key
	 *            ��Կ
	 * @return byte[] ���ܺ�����
	 * 
	 */
	public static byte[] symmetryDecrypt(SecurityContext ctx,
			byte[] cipherContent, Key key) {
		try {
			byte[] tContent = null;
			Cipher cipher = Cipher.getInstance(ctx.getSymmetryAlgorithm(),
					ctx.getJceProvider());
			SecureRandom secureRandom = SecureRandom.getInstance(ctx
					.getSecureRandomAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
			tContent = cipher.doFinal(cipherContent);
			return tContent;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * ���жԳƼ���
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param content
	 *            ���������ġ�
	 * @param key
	 *            ������Կ
	 * @return byte[] ���ܺ�����
	 */
	public static byte[] symmetryEncrypt(SecurityContext ctx, byte[] content,
			Key key) {
		try {
			byte[] cipherContent = null;
			Cipher cipher = Cipher.getInstance(ctx.getSymmetryAlgorithm(),
					ctx.getJceProvider());
			SecureRandom secureRandom = SecureRandom.getInstance(ctx
					.getSecureRandomAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
			cipherContent = cipher.doFinal(content);
			return cipherContent;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	/**
	 * ��Կ��֤ǩ��
	 * 
	 * @param ctx
	 *            �����Ļ���
	 * @param source
	 *            ԭ��
	 * @param signed
	 *            ǩ����Ϣ
	 * @param pubKey
	 *            ��Կ
	 * @return boolean
	 */
	public static boolean verify(SecurityContext ctx, byte[] source,
			byte[] signed, PublicKey publicKey) {
		try {
			boolean verify = false;
			Signature sign = Signature.getInstance(ctx.getSignatureAlgorithm(),
					ctx.getJceProvider());
			sign.initVerify(publicKey);
			sign.update(source);
			verify = sign.verify(signed);
			return verify;
		} catch (Exception ex) {
			throw new SecurityException(ex);
		}
	}

	private SecurityUtils() {

	}

}