/*
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

package org.jpage.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UUID32 {
	private StringBuffer uuid = null;

	private static Random r = new Random();

	public UUID32() {
		uuid = new StringBuffer();
		uuid.append(genKey(8));
		uuid.append(genKey(4));
		uuid.append(genKey(4));
		uuid.append(genKey(4));
		uuid.append(genKey(12));
	}

	private String genKey(int j) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < j; i++) {
			byte b = (byte) (Math.abs(r.nextInt()) % 16);
			char c = (char) ((b >= 10) ? (97 + b) : (48 + b));
			buff.append(c);
		}
		return buff.toString();
	}

	public String toString() {
		return uuid.toString();
	}

	public static String getUUID() {
		UUID32 uuid32 = new UUID32();
		return uuid32.toString();
	}

	public static void main(String[] args) {
		Map map = new HashMap();
		for (int i = 0; i < 1000 * 200; i++) {
			String id = UUID32.getUUID();
			map.put(id, id);
		}
		System.out.println(map.size());

	}
}