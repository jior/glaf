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

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import com.glaf.core.util.threads.*;

public class MasterWorkerTest {

	public class PlusWorker extends Worker {
		@Override
		public Object process(Object input) {
			Integer i = (Integer) input;
			return i * i * i;
		}
	}

	@Test
	public void testMasterWorker() {
		Master m = new Master(new PlusWorker(), 10);
		for (int i = 0; i < 100000; i++) {
			m.submit(i);
		}
		m.execute();
		long ret = 0;
		Map<String, Object> resultMap = m.getResultMap();
		while (resultMap.size() > 0 || !m.isComplete()) {
			Set<String> keys = resultMap.keySet();
			String key = null;
			for (String k : keys) {
				key = k;
				break;
			}
			Integer i = null;
			if (key != null) {
				i = (Integer) resultMap.get(key);
			}
			if (i != null) {
				ret += i;
			}
			if (key != null) {
				resultMap.remove(key);
			}
		}

		System.out.println("testMasterWorker:" + ret);
	}

	@Test
	public void testPlus() {
		long re = 0;
		for (int i = 0; i < 100000; i++) {
			re += i * i * i;
		}
		System.out.println("testPlus:" + re);
	}

}
