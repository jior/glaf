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
