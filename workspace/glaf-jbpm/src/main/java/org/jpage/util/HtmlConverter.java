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
