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
import java.util.Map;

public class ProcessActionType {

	public final static int SAVA_DATA_TYPE = 0;

	public final static int START_PROCESS_TYPE = 1;

	public final static int COMPLETE_TASK_TYPE = 3;

	public final static int FINISH_PROCESS_TYPE = 4;

	public final static String START_PROCESS = "START_PROCESS";

	public final static String COMPLETE_TASK = "COMPLETE_TASK";

	public final static String SAVA_DATA = "SAVA_DATA";

	private static Map dataMap = new HashMap();

	static {
		dataMap.put(SAVA_DATA, new Integer(SAVA_DATA_TYPE));
		dataMap.put(START_PROCESS, new Integer(START_PROCESS_TYPE));
		dataMap.put(COMPLETE_TASK, new Integer(COMPLETE_TASK_TYPE));
	}

	public static int getActionType(String actionType) {
		if (dataMap.get(actionType) != null) {
			return ((Integer) dataMap.get(actionType)).intValue();
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(ProcessActionType
				.getActionType(ProcessActionType.START_PROCESS));
	}
}
