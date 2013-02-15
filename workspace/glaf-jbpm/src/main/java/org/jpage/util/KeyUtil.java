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