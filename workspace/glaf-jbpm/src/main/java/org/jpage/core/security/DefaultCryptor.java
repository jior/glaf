/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.core.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.CertificateFactory;
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

public class DefaultCryptor implements Cryptor {
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
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			System.out.println(cf.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public DefaultCryptor() throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		pbeParamSpec = new PBEParameterSpec(salt, count);
		pbeKeySpec = new PBEKeySpec("saagar".toCharArray());
		keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		pbeKey = keyFac.generateSecret(pbeKeySpec);
	}

	private byte[] crypt(int cipherMode, byte[] bytes)
			throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException

	{
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(cipherMode, pbeKey, pbeParamSpec);
		byte[] cryptext = pbeCipher.doFinal(bytes);
		return cryptext;
	}

	public String encrypt(String str) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encs = crypt(Cipher.ENCRYPT_MODE, str.getBytes());
		String encryptedText = Base64.encodeBase64String(encs);
		encryptedText = encodeHex(encryptedText.getBytes());
		return encryptedText;
	}

	public byte[] encrypt(byte[] bytes) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return crypt(Cipher.ENCRYPT_MODE, bytes);
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

	public byte[] decrypt(byte[] bytes) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return crypt(Cipher.DECRYPT_MODE, bytes);
	}

	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
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

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		DefaultCryptor cryptor = new DefaultCryptor();
		String encryptedText = cryptor.encrypt(args[0]);
		System.out.println("EnCrypted text [" + encryptedText + "]");
		DefaultCryptor cryptor2 = new DefaultCryptor();
		String decryptedText = cryptor2.decrypt(encryptedText);
		System.out.println("DeCrypted text " + decryptedText);
		long end = System.currentTimeMillis();
		System.out.println("�ܹ���ʱ:" + (end - start) + " ����.");
	}
}