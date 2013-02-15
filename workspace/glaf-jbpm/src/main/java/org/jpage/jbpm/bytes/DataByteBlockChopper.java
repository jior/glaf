/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
