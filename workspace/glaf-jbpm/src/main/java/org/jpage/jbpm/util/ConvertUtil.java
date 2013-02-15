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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;

public class ConvertUtil {
	
	public static List toList(Collection rows){
		List list = new ArrayList();
		Iterator iterator =rows.iterator();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		return list;
	}

	public static Object getValue(String value, String typeName) {
		java.util.Date date = null;
		Object obj = value;
		int type = FieldType.getFieldType(typeName);
		switch (type) {
		case FieldType.DATE_TYPE:
			if (StringUtils.isNotBlank(value)) {
				date = DateTools.toDate(value);
				obj = date;
			}
			break;
		case FieldType.TIMESTAMP_TYPE:
			if (StringUtils.isNotBlank(value)) {
				date = DateTools.toDate(value);
			}
			obj = DateTools.toTimestamp(date);
			break;
		case FieldType.DOUBLE_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Double d = new Double(value);
				obj = d;
			}
			break;
		case FieldType.BOOLEAN_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Boolean b = new Boolean(value);
				obj = b;
			}
			break;
		case FieldType.SHORT_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Short s = new Short(value);
				obj = s;
			}
			break;
		case FieldType.INTEGER_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Integer integer = new Integer(value);
				obj = integer;
			}
			break;
		case FieldType.LONG_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Long l = new Long(value);
				obj = l;
			}
			break;
		default:
			break;
		}
		return obj;
	}

}
