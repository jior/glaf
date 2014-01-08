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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.*;
import com.glaf.core.util.ReflectUtils;
import com.glaf.core.util.UUID32;

public class EncryptorFactory {

	private static final Log LOG = LogFactory.getLog(EncryptorFactory.class);

	private static Configuration conf = BaseConfiguration.create();

	private static Encryptor cncryptor;

	private EncryptorFactory() {

	}

	public static Encryptor getEncryptor() {
		if (cncryptor == null) {
			if (conf.get("sys.encryptor") != null) {
				String cncryptorClass = conf.get("sys.encryptor");
				try {
					cncryptor = (Encryptor) ReflectUtils
							.instantiate(cncryptorClass);
				} catch (Exception ex) {
					ex.printStackTrace();
					LOG.error(cncryptorClass + " instantiate error", ex);
				}
			}
			if (cncryptor == null) {
				try {
					cncryptor = new DefaultEncryptor();
				} catch (Exception ex) {
					throw new SecurityException(ex);
				}
			}
		}
		return cncryptor;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10000; i++) {
			long start = System.currentTimeMillis();
			Encryptor cryptor = EncryptorFactory.getEncryptor();
			String encryptedText = cryptor.encrypt(UUID32.getUUID());
			System.out.println("EnCrypted text [" + encryptedText + "]");

			String decryptedText = cryptor.decrypt(encryptedText);
			System.out.println("DeCrypted text " + decryptedText);
			long ms = System.currentTimeMillis()-start;
			System.out.println("总共用时:" + (ms) + " 毫秒.");
		}
	}
}
