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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheManager;

/**
 * 对象序列化工具包
 * 
 */
public class SerializationUtils {

	private final static Logger log = LoggerFactory
			.getLogger(SerializationUtils.class);

	private static Serializer serializer = null;

	public static Object deserialize(byte[] bytes) throws IOException {
		return getSerializer().deserialize(bytes);
	}

	private static Serializer getSerializer() {
		if (serializer == null) {
			String ser = CacheManager.getSerializer();
			if (StringUtils.equals(ser, "java")) {
				serializer = java_ser;
			} else if (StringUtils.equals(ser, "fst")) {
				serializer = fst_ser;
			} else if (StringUtils.equals(ser, "kryo")) {
				serializer = kryo_ser;
			} else {
				if (StringUtils.isNotEmpty(ser)) {
					try {
						serializer = (Serializer) Class.forName(ser)
								.newInstance();
					} catch (Exception ex) {
						throw new CacheException(
								"Cannot initialize Serializer named [" + ser
										+ ']', ex);
					}
				}
			}

			if (serializer == null) {
				serializer = java_ser;
			}

			log.info("Using Serializer -> [" + serializer.name() + ":"
					+ serializer.getClass().getName() + ']');
		}

		return serializer;
	}

	public static byte[] serialize(Object object) throws IOException {
		return getSerializer().serialize(object);
	}

	private final static Serializer fst_ser = new Serializer() {

		@Override
		public Object deserialize(byte[] bytes) throws IOException {
			if (bytes == null || bytes.length == 0) {
				return null;
			}
			FSTObjectInput in = null;
			try {
				in = new FSTObjectInput(new ByteArrayInputStream(bytes));
				return in.readObject();
			} catch (ClassNotFoundException e) {
				throw new CacheException(e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}

		@Override
		public String name() {
			return "fst";
		}

		@Override
		public byte[] serialize(Object obj) throws IOException {
			ByteArrayOutputStream out = null;
			FSTObjectOutput fout = null;
			try {
				out = new ByteArrayOutputStream();
				fout = new FSTObjectOutput(out);
				fout.writeObject(obj);
				return out.toByteArray();
			} finally {
				if (fout != null)
					try {
						fout.close();
					} catch (IOException e) {
					}
			}
		}

	};

	private final static Serializer java_ser = new Serializer() {

		@Override
		public Object deserialize(byte[] bits) throws IOException {
			if (bits == null || bits.length == 0) {
				return null;
			}
			ObjectInputStream ois = null;
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(bits);
				ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (ClassNotFoundException ex) {
				throw new CacheException(ex);
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
					}
				}
			}
		}

		@Override
		public String name() {
			return "java";
		}

		@Override
		public byte[] serialize(Object obj) throws IOException {
			ObjectOutputStream oos = null;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(obj);
				return baos.toByteArray();
			} finally {
				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
					}
				}
			}
		}

	};

	private final static Serializer kryo_ser = new Serializer() {

		Kryo kryo = new Kryo();

		@Override
		public Object deserialize(byte[] bits) throws IOException {
			if (bits == null || bits.length == 0) {
				return null;
			}
			Input input = null;
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(bits);
				input = new Input(bais);
				return kryo.readClassAndObject(input);
			} catch (Exception ex) {
				throw new CacheException(ex);
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}

		@Override
		public String name() {
			return "kryo";
		}

		@Override
		public byte[] serialize(Object obj) throws IOException {
			Output output = null;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				output = new Output(baos);
				kryo.writeClassAndObject(output, obj);
				output.flush();
				return baos.toByteArray();
			} finally {
				if (output != null) {
					output.close();
				}
			}
		}

	};

}
