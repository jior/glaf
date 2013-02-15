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


package org.jpage.util;

public class HtmlConverter {
  public final static String convert(String pContent) {
    if (pContent == null) {
      return null;
    }
    pContent = escapeHTMLTags(pContent);
    pContent = convertNewlines(pContent);
    return pContent;
  }

  public final static String convertNewlines(String input) {
    char[] chars = input.toCharArray();
    int cur = 0;
    int len = chars.length;
    StringBuffer buf = new StringBuffer(len);
    for (int i = 0; i < len; i++) {
      if (chars[i] == '\n') {
        buf.append(chars, cur, i - cur).append(BR_TAG);
        cur = i + 1;
      }
      else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
        buf.append(chars, cur, i - cur).append(BR_TAG);
        i++;
        cur = i + 1;
      }
    }
    buf.append(chars, cur, len - cur);
    return buf.toString();
  }

  public final static String escapeHTMLTags(String in) {
    if (in == null) {
      return null;
    }
    char ch;
    int i = 0;
    int last = 0;
    char[] input = in.toCharArray();
    int len = input.length;
    StringBuffer out = new StringBuffer( (int) (len * 1.3));
    for (; i < len; i++) {
      ch = input[i];
      if (ch > '>') {
        continue;
      }
      else if (ch == '<') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(LT_ENCODE);
      }
      else if (ch == '>') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(GT_ENCODE);
      }
      else if (ch == '&') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(AMP_ENCODE);
      }
      else if (ch == ' ') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(SPACE_ENCODE);
      }
      else if (ch == 'га') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(SPACE_ENCODE).append(SPACE_ENCODE);
      }
    }
    if (last == 0) {
      return in;
    }
    if (i > last) {
      out.append(input, last, i - last);
    }
    return out.toString();
  }

  private final static char[] BR_TAG = "<BR>".toCharArray();
  private final static char[] AMP_ENCODE = "&amp;".toCharArray();
  private final static char[] LT_ENCODE = "&lt;".toCharArray();
  private final static char[] GT_ENCODE = "&gt;".toCharArray();
  private final static char[] SPACE_ENCODE = "&nbsp;".toCharArray();

}
