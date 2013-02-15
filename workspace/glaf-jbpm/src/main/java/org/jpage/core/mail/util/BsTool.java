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

package org.jpage.core.mail.util;

public class BsTool {

  public BsTool() {
  }

  public static boolean isNull(String s) {
    if (s == null) {
      return true;
    }
    if (s.length() == 0) {
      return true;
    }
    return s.equalsIgnoreCase("null");
  }

  public static boolean isNotNull(String s) {
    return!isNull(s);
  }

  public static int charCount(String s, char c) {
    int i = 0;
    for (int j = 0; j < s.length(); j++) {
      if (s.charAt(j) == c) {
        i++;
      }
    }

    return i;
  }

  public static String getValue(String s, String s1) {
    if (s != null) {
      return s;
    }
    else {
      return s1;
    }
  }

  public static int getValue(String s, int i) {
    if (s != null) {
      return Integer.valueOf(s).intValue();
    }
    else {
      return i;
    }
  }

  public static double getValue(String s, double d) {
    if (s != null) {
      return Double.valueOf(s).doubleValue();
    }
    else {
      return d;
    }
  }

  public static float getValue(String s, float f) {
    if (s != null) {
      return Float.valueOf(s).floatValue();
    }
    else {
      return f;
    }
  }

  public static boolean getValue(String s, boolean flag) {
    if (s != null) {
      return Boolean.valueOf(s).booleanValue();
    }
    else {
      return flag;
    }
  }

}
