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

package com.glaf.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;

public class SerializationUtils {

	public static Object fstdeserialize(byte[] bytes) {
		FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeStream(in);
		}
	}

	public static byte[] fstserialize(Object obj) {
		ByteArrayOutputStream out = null;
		FSTObjectOutput fout = null;
		try {
			out = new ByteArrayOutputStream();
			fout = new FSTObjectOutput(out);
			fout.writeObject(obj);
			fout.flush();
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeStream(out);
			IOUtils.closeStream(fout);
		}
	}

	public static Object javadeserialize(byte[] bits) {
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeStream(ois);
		}
	}

	public static byte[] javaserialize(Object obj) {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeStream(oos);
		}
	}

	public static byte[] serialize(Object obj) {
		return fstserialize(obj);
	}

	public static Object unserialize(byte[] bytes) {
		return fstdeserialize(bytes);
	}

}
