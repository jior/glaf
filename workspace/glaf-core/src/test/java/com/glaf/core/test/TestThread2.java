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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.glaf.core.util.DateUtils;

public class TestThread2 extends Thread {

	private int count;

	public TestThread2(int count) {
		this.count = count;
	}

	public void start() {
		System.out.println(count + "-------------start------------");
	}

	public void run() {
		try {
			Thread.sleep(count * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(count + "-------------run------------");
		System.out.println(DateUtils.getDateTime(new java.util.Date()));
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 500; i++) {
			TestThread2 t = new TestThread2(i);
			executorService.execute(t);
		}
	}

}
