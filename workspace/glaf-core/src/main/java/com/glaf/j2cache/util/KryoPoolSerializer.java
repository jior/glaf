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

package com.glaf.j2cache.util;

import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoPoolSerializer implements Serializer {

	private static class KryoHolder {
		private Kryo kryo;
		static final int BUFFER_SIZE = 1024;
		private Output output = new Output(BUFFER_SIZE, -1);
		private Input input = new Input();

		KryoHolder(Kryo kryo) {
			this.kryo = kryo;
		}

	}

	interface KryoPool {

		/**
		 * get o kryo object
		 *
		 * @return
		 */
		KryoHolder get();

		/**
		 * return object
		 *
		 * @param kryo
		 */
		void offer(KryoHolder kryo);
	}

	/**
	 * 由于kryo创建的代价相对较高 ，这里使用空间换时间 对KryoHolder对象进行重用
	 */
	public static class KryoPoolImpl implements KryoPool {
		/**
		 * default is 1500 online server limit 3K
		 */

		/**
		 * creat a Singleton
		 */
		private static class Singleton {
			private static final KryoPool pool = new KryoPoolImpl();
		}

		/**
		 * @return
		 */
		public static KryoPool getInstance() {
			return Singleton.pool;
		}

		/**
		 * thread safe list
		 */
		private final Deque<KryoHolder> kryoHolderDeque = new ConcurrentLinkedDeque<KryoHolder>();

		/**
         *
         */
		private KryoPoolImpl() {

		}

		/**
		 * create a new kryo object to application use
		 *
		 * @return
		 */
		public KryoHolder creatInstnce() {
			Kryo kryo = new Kryo();
			kryo.setReferences(false);//
			return new KryoHolder(kryo);
		}

		/**
		 * get o KryoHolder object
		 *
		 * @return
		 */
		@Override
		public KryoHolder get() {
			KryoHolder kryoHolder = kryoHolderDeque.pollFirst(); // Retrieves
																	// and
																	// removes
																	// the head
																	// of the
																	// queue
																	// represented
																	// by this
																	// table
			return kryoHolder == null ? creatInstnce() : kryoHolder;
		}

		/**
		 * return object Inserts the specified element at the tail of this
		 * queue.
		 *
		 * @param kryoHolder
		 */
		@Override
		public void offer(KryoHolder kryoHolder) {
			kryoHolderDeque.addLast(kryoHolder);
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException {
		KryoHolder kryoHolder = null;
		if (bytes == null)
			throw new RuntimeException("bytes can not be null");
		try {
			kryoHolder = KryoPoolImpl.getInstance().get();
			kryoHolder.input.setBuffer(bytes, 0, bytes.length);
			return kryoHolder.kryo.readClassAndObject(kryoHolder.input);
		} catch (Exception ex) {
			throw new RuntimeException("Deserialize bytes exception");
		} finally {
			KryoPoolImpl.getInstance().offer(kryoHolder);
			bytes = null;
		}
	}

	@Override
	public String name() {
		return "kryo_pool";
	}

	@Override
	public byte[] serialize(Object obj) throws IOException {
		KryoHolder kryoHolder = null;
		if (obj == null)
			throw new RuntimeException("object can not be null");
		try {
			kryoHolder = KryoPoolImpl.getInstance().get();
			kryoHolder.output.clear();
			kryoHolder.kryo.writeClassAndObject(kryoHolder.output, obj);
			return kryoHolder.output.toBytes();
		} catch (Exception ex) {
			throw new RuntimeException("Serialize object exception");
		} finally {
			KryoPoolImpl.getInstance().offer(kryoHolder);
			obj = null;
		}
	}
}
