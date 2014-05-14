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

public class Authentication {

	static ThreadLocal<String> authenticatedActorThreadLocal = new ThreadLocal<String>();
	
	static ThreadLocal<LoginContext> loginContextThreadLocal = new ThreadLocal<LoginContext>();

	private Authentication() {

	}
	
	public static void clear(){
		authenticatedActorThreadLocal.remove();
		loginContextThreadLocal.remove();
	}
	
	public static LoginContext getLoginContext() {
		return loginContextThreadLocal.get();
	}

	public static void setLoginContext(LoginContext loginContext) {
		loginContextThreadLocal.set(loginContext);
	}

	public static String getAuthenticatedActorId() {
		return authenticatedActorThreadLocal.get();
	}

	public static void setAuthenticatedActorId(String authenticatedAccount) {
		authenticatedActorThreadLocal.set(authenticatedAccount);
	}

}
