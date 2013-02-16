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

package org.jpage.jbpm.bytes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.JbpmConfiguration;

public abstract class DataByteBlockChopper {

	public static List chopItUp(byte[] byteArray) {
		int blockSize = JbpmConfiguration.Configs
				.getInt("jpage.data.byte.block.size");
		List bytes = null;
		if ((byteArray != null) && (byteArray.length > 0)) {
			bytes = new ArrayList();
			int index = 0;
			while ((byteArray.length - index) > blockSize) {
				byte[] byteBlock = new byte[blockSize];
				System.arraycopy(byteArray, index, byteBlock, 0, blockSize);
				bytes.add(byteBlock);
				index += blockSize;
			}
			byte[] byteBlock = new byte[byteArray.length - index];
			System.arraycopy(byteArray, index, byteBlock, 0, byteArray.length
					- index);
			bytes.add(byteBlock);
		}
		return bytes;
	}

	public static byte[] glueChopsBackTogether(List bytes) {
		byte[] value = null;

		if (bytes != null) {
			Iterator iter = bytes.iterator();
			while (iter.hasNext()) {
				byte[] byteBlock = (byte[]) iter.next();
				if (value == null) {
					value = byteBlock;
				} else {
					byte[] oldValue = value;
					value = new byte[value.length + byteBlock.length];
					System.arraycopy(oldValue, 0, value, 0, oldValue.length);
					System.arraycopy(byteBlock, 0, value, oldValue.length,
							byteBlock.length);
				}
			}
		}
		return value;
	}
}
