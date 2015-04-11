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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysKey;
import com.glaf.core.service.SysKeyService;
import com.glaf.core.util.UUID32;

/**
 * RSA算法加密/解密工具类。
 * 
 */
public final class RSAUtils {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RSAUtils.class);

	/** 算法名称 */
	private static final String ALGORITHOM = "RSA";

	/** 保存生成的密钥对的文件名称。 */
	private static final String RSA_PAIR_FILENAME = "/RSAKey";

	/** 密钥大小 */
	private static final int KEY_SIZE = 1024;

	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	private static KeyPairGenerator keyPairGen = null;

	private static KeyFactory keyFactory = null;

	/** 缓存的密钥对。 */
	private static KeyPair oneKeyPair = null;

	private static File rsaPairFile = null;

	static {
		try {
			keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM,
					DEFAULT_PROVIDER);
			keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error(ex.getMessage());
		}
		rsaPairFile = new File(getRSAPairFilePath());
	}

	private RSAUtils() {

	}

	/**
	 * 使用指定的私钥解密数据。
	 * 
	 * @param privateKey
	 *            给定的私钥。
	 * @param data
	 *            要解密的数据。
	 * @return 原数据。
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data)
			throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.DECRYPT_MODE, privateKey);
		return ci.doFinal(data);
	}

	/**
	 * 使用给定的私钥解密给定的字符串。
	 * <p />
	 * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回
	 * {@code null}。 私钥不匹配时，返回 {@code null}。
	 * 
	 * @param privateKey
	 *            给定的私钥。
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(PrivateKey privateKey, String encrypttext) {
		if (privateKey == null || StringUtils.isEmpty(encrypttext)) {
			return null;
		}
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt(privateKey, en_data);
			return new String(data);
		} catch (Exception ex) {
			LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s",
					encrypttext, ex.getCause().getMessage()));
		}
		return null;
	}

	/**
	 * 使用默认的私钥解密给定的字符串。
	 * <p />
	 * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。 私钥不匹配时，返回
	 * {@code null}。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(String encrypttext) {
		if (StringUtils.isEmpty(encrypttext)) {
			return null;
		}
		KeyPair keyPair = getKeyPair();
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt((RSAPrivateKey) keyPair.getPrivate(), en_data);
			return new String(data);
		} catch (Exception ex) {
			LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s",
					encrypttext, ex.getMessage()));
		}
		return null;
	}

	/**
	 * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return {@code encrypttext} 的原文字符串。
	 */
	public static String decryptStringByJs(String encrypttext) {
		String text = decryptString(encrypttext);
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	/**
	 * 使用指定的公钥加密数据。
	 * 
	 * @param publicKey
	 *            给定的公钥。
	 * @param data
	 *            要加密的数据。
	 * @return 加密后的数据。
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data)
			throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.ENCRYPT_MODE, publicKey);
		return ci.doFinal(data);
	}

	/**
	 * 使用给定的公钥加密给定的字符串。
	 * <p />
	 * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null}
	 * 则返回 {@code null}。
	 * 
	 * @param publicKey
	 *            给定的公钥。
	 * @param plaintext
	 *            字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(PublicKey publicKey, String plaintext) {
		if (publicKey == null || plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		try {
			byte[] en_data = encrypt(publicKey, data);
			return new String(Hex.encodeHex(en_data));
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	/**
	 * 使用默认的公钥加密给定的字符串。
	 * <p />
	 * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
	 * 
	 * @param plaintext
	 *            字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(String plaintext) {
		if (plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		KeyPair keyPair = getKeyPair();
		try {
			byte[] en_data = encrypt((RSAPublicKey) keyPair.getPublic(), data);
			return new String(Hex.encodeHex(en_data));
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	/**
	 * 生成并返回RSA密钥对。
	 */
	private static synchronized KeyPair generateKeyPair() {
		try {
			keyPairGen.initialize(KEY_SIZE, new SecureRandom(UUID32.getUUID()
					.getBytes()));
			oneKeyPair = keyPairGen.generateKeyPair();
			saveKeyPair(oneKeyPair);
			return oneKeyPair;
		} catch (InvalidParameterException ex) {
			LOGGER.error("KeyPairGenerator does not support a key length of "
					+ KEY_SIZE + ".", ex);
		} catch (Exception ex) {
			LOGGER.error(
					"RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.",
					ex);
		}
		return null;
	}

	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param privateExponent
	 *            专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) {
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(
				new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
		} catch (Exception ex) {
			LOGGER.error(
					"RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.",
					ex);
		}
		return null;
	}

	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param publicExponent
	 *            专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
		} catch (Exception ex) {
			LOGGER.error(
					"RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.",
					ex);
		}
		return null;
	}

	/** 返回已初始化的默认的私钥。 */
	public static RSAPrivateKey getDefaultPrivateKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPrivateKey) keyPair.getPrivate();
		}
		return null;
	}

	/** 返回已初始化的默认的公钥。 */
	public static RSAPublicKey getDefaultPublicKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPublicKey) keyPair.getPublic();
		}
		return null;
	}

	/**
	 * 返回RSA密钥对。
	 */
	public static KeyPair getKeyPair() {
		// 首先判断是否需要重新生成新的密钥对文件
		if (isCreateKeyPairFile()) {
			// 直接强制生成密钥对文件，并存入缓存。
			return generateKeyPair();
		}
		if (oneKeyPair != null) {
			return oneKeyPair;
		}
		return readKeyPair();
	}

	/**
	 * 返回生成/读取的密钥对文件的路径。
	 */
	private static String getRSAPairFilePath() {
		String urlPath = RSAUtils.class.getResource("/").getPath();
		return (new File(urlPath).getParent() + RSA_PAIR_FILENAME);
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param privateExponent
	 *            专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey getRSAPrivateKey(String hexModulus,
			String hexPrivateExponent) {
		if (StringUtils.isEmpty(hexModulus)
				|| StringUtils.isEmpty(hexPrivateExponent)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] privateExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
		}
		if (modulus != null && privateExponent != null) {
			return generateRSAPrivateKey(modulus, privateExponent);
		}
		return null;
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param publicExponent
	 *            专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey getRSAPublidKey(String hexModulus,
			String hexPublicExponent) {
		if (StringUtils.isEmpty(hexModulus)
				|| StringUtils.isEmpty(hexPublicExponent)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] publicExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
		}
		if (modulus != null && publicExponent != null) {
			return generateRSAPublicKey(modulus, publicExponent);
		}
		return null;
	}

	/**
	 * 若需要创建新的密钥对文件，则返回 {@code true}，否则 {@code false}。
	 */
	private static boolean isCreateKeyPairFile() {
		InputStream in = null;
		ObjectInputStream ois = null;
		try {
			SysKeyService sysKeyService = ContextFactory
					.getBean("sysKeyService");
			SysKey sysKey = sysKeyService.getSysKey("RSAKey");
			if (sysKey != null && sysKey.getData() != null) {
				in = new ByteArrayInputStream(sysKey.getData());
				ois = new ObjectInputStream(in);
				oneKeyPair = (KeyPair) ois.readObject();
				// 数据库已经保存了密锁文件，就不用重新生成了
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(in);
		}
		// 是否创建新的密钥对文件
		boolean createNewKeyPair = false;
		if (!rsaPairFile.exists() || rsaPairFile.isDirectory()) {
			createNewKeyPair = true;
		}
		return createNewKeyPair;
	}

	private static KeyPair readKeyPair() {
		InputStream in = null;
		ObjectInputStream ois = null;
		try {
			SysKeyService sysKeyService = ContextFactory
					.getBean("sysKeyService");
			SysKey sysKey = sysKeyService.getSysKey("RSAKey");
			if (sysKey != null && sysKey.getData() != null) {
				in = new ByteArrayInputStream(sysKey.getData());
				ois = new ObjectInputStream(in);
				oneKeyPair = (KeyPair) ois.readObject();
				return oneKeyPair;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(in);
		}
		try {
			in = FileUtils.openInputStream(rsaPairFile);
			ois = new ObjectInputStream(in);
			oneKeyPair = (KeyPair) ois.readObject();
			return oneKeyPair;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(in);
		}
		return null;
	}

	/**
	 * 将指定的RSA密钥对以文件形式保存。
	 * 
	 * @param keyPair
	 *            要保存的密钥对。
	 */
	private static void saveKeyPair(KeyPair keyPair) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = FileUtils.openOutputStream(rsaPairFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(keyPair);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(fos);
		}
		FileInputStream fin = null;
		try {
			fin = FileUtils.openInputStream(rsaPairFile);
			SysKey sysKey = new SysKey();
			sysKey.setId("RSAKey");
			sysKey.setCreateBy("system");
			sysKey.setName("RSAKey");
			sysKey.setType("RSA");
			sysKey.setTitle("系统默认的RSA密锁");
			sysKey.setPath(rsaPairFile.getName());
			sysKey.setData(IOUtils.toByteArray(fin));
			SysKeyService sysKeyService = ContextFactory
					.getBean("sysKeyService");
			sysKeyService.save(sysKey);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fin);
		}
	}

	public static void main(String[] args) {
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		System.out.println(new String(Hex.encodeHex(publicKey.getModulus()
				.toByteArray())));
		System.out.println(new String(Hex.encodeHex(publicKey
				.getPublicExponent().toByteArray())));
		System.out
				.println(RSAUtils
						.decryptStringByJs("2d7754804ecfb3c3e6fb7d12cdf439036f1d8e2ad34c5a6467bd6f1c165bb47f0fa57134b013aba49be5edf5231c2f7b611af5e974b521ea715b1a6bad6cfbf4ba8e0886c5fe1ce903d30ae0c8cd5f422860d67fa4fd3e2a8fc7872c6b052a6c8f480cfde5e147d959f3db5032767c393ff271742f66be7657290a5de218e375"));
	}

}