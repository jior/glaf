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

public class SecurityContext {

	private String jceProvider = "BC"; // JCE提供者

	private String asymmetryAlgorithm = "RSA"; // 不对称加密算法

	private String signatureAlgorithm = "SHA1withRSA"; // 签名算法

	private String secureRandomAlgorithm = "SHA1PRNG"; // 安全随机数算法

	private String symmetryAlgorithm = "DES/ECB/PKCS5Padding"; // 对称加密算法

	private String symmetryKeyAlgorithm = "DES";

	private int symmetryKeySize = 56; // 对称加密密钥长度

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