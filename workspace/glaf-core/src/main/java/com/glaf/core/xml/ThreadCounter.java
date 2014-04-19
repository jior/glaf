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

package com.glaf.core.xml;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounter {

	private static ThreadLocal<AtomicInteger> total = new ThreadLocal<AtomicInteger>();
	private static ThreadLocal<AtomicInteger> finished = new ThreadLocal<AtomicInteger>();
	private static ThreadLocal<AtomicInteger> failed = new ThreadLocal<AtomicInteger>();

	public static void addFailed() {
		failed.get().incrementAndGet();
	}

	public static void addFinished() {
		finished.get().incrementAndGet();
	}

	public static int getFailed() {
		return failed.get().get();
	}

	public static int getFinished() {
		return finished.get().get();
	}

	public static int getTotal() {
		return total.get().get();
	}

	public static void reset() {
		total.get().set(0);
		failed.get().set(0);
		finished.get().set(0);
	}

	public static void setTotal(int t) {
		total.get().set(t);
	}

	private ThreadCounter() {
		total.get().set(0);
		failed.get().set(0);
		finished.get().set(0);
	}

}