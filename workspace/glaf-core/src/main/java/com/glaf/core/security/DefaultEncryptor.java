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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DefaultEncryptor implements Encryptor {

	private PBEKeySpec pbeKeySpec;

	private PBEParameterSpec pbeParamSpec;

	private SecretKeyFactory keyFac;

	private SecretKey pbeKey;

	private byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
			(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

	private int count = 20;

	static {
		try {
			String provider = "org.bouncycastle.jce.provider.BouncyCastleProvider";
			java.security.Security.addProvider((Provider) Class.forName(
					provider).newInstance());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public DefaultEncryptor() throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		pbeParamSpec = new PBEParameterSpec(salt, count);
		pbeKeySpec = new PBEKeySpec("saagar".toCharArray());
		keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		pbeKey = keyFac.generateSecret(pbeKeySpec);
	}

	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			int newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = (byte) newByte;
			byteCount++;
		}
		return bytes;
	}

	public static final String encodeHex(byte[] bytes) {
		StringBuffer buff = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buff.append("0");
			}
			buff.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buff.toString();
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	private byte[] crypt(int cipherMode, byte[] bytes)
			throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(cipherMode, pbeKey, pbeParamSpec);
		byte[] cryptext = pbeCipher.doFinal(bytes);
		return cryptext;
	}

	public byte[] decrypt(byte[] bytes) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return crypt(Cipher.DECRYPT_MODE, bytes);
	}

	public String decrypt(String str) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] bytes = decodeHex(str);
		byte[] decrypted = Base64.decodeBase64(new String(bytes));
		byte[] decs = crypt(Cipher.DECRYPT_MODE, decrypted);
		String decryptedText = new String(decs);
		return decryptedText;
	}

	public byte[] encrypt(byte[] bytes) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return crypt(Cipher.ENCRYPT_MODE, bytes);
	}

	public String encrypt(String str) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encs = crypt(Cipher.ENCRYPT_MODE, str.getBytes());
		String encryptedText = Base64.encodeBase64String(encs);
		encryptedText = encodeHex(encryptedText.getBytes());
		return encryptedText;
	}
}