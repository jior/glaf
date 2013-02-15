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

public class SecurityContext {

	private String jceProvider = "BC"; // JCE�ṩ��

	private String asymmetryAlgorithm = "RSA"; // ���ԳƼ����㷨

	private String signatureAlgorithm = "SHA1withRSA"; // ǩ���㷨

	private String secureRandomAlgorithm = "SHA1PRNG"; // ��ȫ������㷨

	private String symmetryAlgorithm = "DES/ECB/PKCS5Padding"; // �ԳƼ����㷨

	private String symmetryKeyAlgorithm = "DES";

	private int symmetryKeySize = 56; // �ԳƼ�����Կ����

	public SecurityContext() {

	}

	/**
	 * @return Returns the asymmetryAlgorithm.
	 */
	public String getAsymmetryAlgorithm() {
		return asymmetryAlgorithm;
	}

	/**
	 * @param asymmetryAlgorithm
	 *            The asymmetryAlgorithm to set.
	 */
	public void setAsymmetryAlgorithm(String asymmetryAlgorithm) {
		this.asymmetryAlgorithm = asymmetryAlgorithm;
	}

	/**
	 * @return Returns the jceProvider.
	 */
	public String getJceProvider() {
		return jceProvider;
	}

	/**
	 * @param jceProvider
	 *            The jceProvider to set.
	 */
	public void setJceProvider(String jceProvider) {
		this.jceProvider = jceProvider;
	}

	/**
	 * @return Returns the secureRandomAlgorithm.
	 */
	public String getSecureRandomAlgorithm() {
		return secureRandomAlgorithm;
	}

	/**
	 * @param secureRandomAlgorithm
	 *            The secureRandomAlgorithm to set.
	 */
	public void setSecureRandomAlgorithm(String secureRandomAlgorithm) {
		this.secureRandomAlgorithm = secureRandomAlgorithm;
	}

	/**
	 * @return Returns the signatureAlgorithm.
	 */
	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	/**
	 * @param signatureAlgorithm
	 *            The signatureAlgorithm to set.
	 */
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	/**
	 * @return Returns the symmetryAlgorithm.
	 */
	public String getSymmetryAlgorithm() {
		return symmetryAlgorithm;
	}

	/**
	 * @param symmetryAlgorithm
	 *            The symmetryAlgorithm to set.
	 */
	public void setSymmetryAlgorithm(String symmetryAlgorithm) {
		this.symmetryAlgorithm = symmetryAlgorithm;
	}

	/**
	 * @return Returns the symmetryKeyAlgorithm.
	 */
	public String getSymmetryKeyAlgorithm() {
		return symmetryKeyAlgorithm;
	}

	/**
	 * @param symmetryKeyAlgorithm
	 *            The symmetryKeyAlgorithm to set.
	 */
	public void setSymmetryKeyAlgorithm(String symmetryKeyAlgorithm) {
		this.symmetryKeyAlgorithm = symmetryKeyAlgorithm;
	}

	/**
	 * @return Returns the symmetryKeySize.
	 */
	public int getSymmetryKeySize() {
		return symmetryKeySize;
	}

	/**
	 * @param symmetryKeySize
	 *            The symmetryKeySize to set.
	 */
	public void setSymmetryKeySize(int symmetryKeySize) {
		this.symmetryKeySize = symmetryKeySize;
	}
}