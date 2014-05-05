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

public class SecurityContext {

	private String jceProvider = "BC"; // JCE提供者

	private String asymmetryAlgorithm = "RSA"; // 非对称加密算法

	private String signatureAlgorithm = "SHA1withRSA"; // 签名算法

	private String secureRandomAlgorithm = "SHA1PRNG"; // 安全随机数算法

	private String symmetryAlgorithm = "DES/ECB/PKCS5Padding"; // 对称加密算法

	private String symmetryKeyAlgorithm = "DES";

	private int symmetryKeySize = 256; // 对称加密密钥长度

	public SecurityContext() {

	}

	public String getAsymmetryAlgorithm() {
		return asymmetryAlgorithm;
	}

	public void setAsymmetryAlgorithm(String asymmetryAlgorithm) {
		this.asymmetryAlgorithm = asymmetryAlgorithm;
	}

	public String getJceProvider() {
		return jceProvider;
	}

	public void setJceProvider(String jceProvider) {
		this.jceProvider = jceProvider;
	}

	public String getSecureRandomAlgorithm() {
		return secureRandomAlgorithm;
	}

	public void setSecureRandomAlgorithm(String secureRandomAlgorithm) {
		this.secureRandomAlgorithm = secureRandomAlgorithm;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getSymmetryAlgorithm() {
		return symmetryAlgorithm;
	}

	public void setSymmetryAlgorithm(String symmetryAlgorithm) {
		this.symmetryAlgorithm = symmetryAlgorithm;
	}

	public String getSymmetryKeyAlgorithm() {
		return symmetryKeyAlgorithm;
	}

	public void setSymmetryKeyAlgorithm(String symmetryKeyAlgorithm) {
		this.symmetryKeyAlgorithm = symmetryKeyAlgorithm;
	}

	public int getSymmetryKeySize() {
		return symmetryKeySize;
	}

	public void setSymmetryKeySize(int symmetryKeySize) {
		this.symmetryKeySize = symmetryKeySize;
	}
}