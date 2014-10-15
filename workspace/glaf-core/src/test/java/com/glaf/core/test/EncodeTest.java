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

package com.glaf.core.test;

import org.apache.commons.codec.digest.DigestUtils;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.UUID32;

public class EncodeTest {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			String str = RequestUtils.encodeString(String.valueOf(i) + "_"
					+ UUID32.getUUID());
			System.out.println(RequestUtils.decodeString(str));
		}
		System.out.println();
		System.out.println(RequestUtils.encodeString("20140805/lfm-0000001"));
		System.out.println(RequestUtils.decodeString("4b6a68716c64614e75724a4448353538514b57323065724536556d65614a6d324e497665594f6d493452614232446a415353656969734b526d426a41306b697175365536393150716264513d"));
		String salt = DigestUtils.md5Hex(SystemConfig.getToken());
		System.out.println(salt);
	}

}
