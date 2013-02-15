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

package org.jpage.jbpm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jpage.util.ElementType;

public class InputType {

	private static Map dataMap = new HashMap();

	static {
		dataMap.put("input@hidden", new Integer(ElementType.HIDDEN_TYPE));
		dataMap.put("input@radio", new Integer(ElementType.RADIO_TYPE));
		dataMap.put("input@radio.pass",
				new Integer(ElementType.PASS_RADIO_TYPE));
		dataMap.put("input@checkbox", new Integer(ElementType.CHECKBOX_TYPE));
		dataMap.put("input@select", new Integer(ElementType.SELECT_TYPE));
		dataMap.put("input@password", new Integer(ElementType.PASSWORD_TYPE));
		dataMap.put("input@textfield", new Integer(ElementType.TEXT_TYPE));
		dataMap.put("input@textarea", new Integer(ElementType.TEXTAREA_TYPE));
	}

	public static int getInputType(String variableName) {
		if (variableName == null) {
			return -1;
		}
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (variableName.startsWith(key)) {
				Integer value = (Integer) dataMap.get(key);
				return value.intValue();
			}
		}
		return -1;
	}

}
