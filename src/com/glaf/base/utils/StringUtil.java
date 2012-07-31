package com.glaf.base.utils;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: StringUtil.java
 * </p>
 * <p>
 * Description: 字符串处理工具
 * </p>
 */

public class StringUtil {
	private static Object initLock = new Object();

	private static Random randGen = null;

	private static char[] numbersAndLetters = null;

	static {
		// 初始化随机数字生成器
		if (randGen == null) {
			synchronized (initLock) {
				randGen = new Random();
				// 初始化数字、字母数组
				numbersAndLetters = ("0123456789").toCharArray();
			}
		}
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            长度
	 * @return String 随机字符串
	 */
	public static final String randomNumString(int length) {
		if (length < 1) {
			return null;
		}

		// 创建字符缓存数组装入字母和数字
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
		}
		return new String(randBuffer);
	}

	/**
	 * 替换字符串
	 * 
	 * @param line
	 *            String 输入串
	 * @param oldString
	 *            String 被替换的串
	 * @param newString
	 *            String 要替换的串
	 * @return String 替换后的字符串
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 校验一段字符串是否包含汉字
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isGB2312(String str) {
		char[] chars = str.toCharArray();
		boolean isGB2312 = false;

		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
						&& ints[1] <= 0xFE) {
					isGB2312 = true;
					break;
				}
			}
		}
		return isGB2312;
	}

	/**
	 * 生成随机目录
	 * 
	 * @param root
	 * @return
	 */
	public static String createDir(String root) {
		String path = randomNumString(4);
		String slash = File.separator;
		String first = path.substring(0, 2);
		String second = path.substring(2, 4);
		File dir = new File(root + slash + first);
		if (!dir.isDirectory())
			dir.mkdir();
		dir = new File(root + slash + first + slash + second);
		if (!dir.isDirectory())
			dir.mkdir();
		return first + slash + second + slash;
	}

	/**
	 * * 取字符串的前toCount个字符
	 * 
	 * @param str
	 * @param toCount
	 * @return
	 */
	public static String substring(String str, int toCount) {
		int reInt = 0;
		String reStr = "";
		if (str == null) {
			return "";
		}
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1)) {
			reStr += "...";
		}
		return reStr;
	}

	/**
	 * if input is a single-dimension primitive array, return a new array
	 * consisting of wrapped elements, else just return input argument
	 */
	public static Object toArray(Object vec) {
		// if null, return
		if (vec == null) {
			return vec;
		}

		// if not an array or elements not primitive, return
		Class clasz = vec.getClass();
		if (!clasz.isArray()) {
			return vec;
		}
		if (!clasz.getComponentType().isPrimitive()) {
			return vec;
		}

		// get array length and create Object output array
		int length = Array.getLength(vec);
		Object newvec[] = new Object[length];

		// wrap and copy elements
		for (int i = 0; i < length; i++) {
			newvec[i] = Array.get(vec, i);
		}
		return newvec;
	}

	/**
	 * 转换成List对象
	 * 
	 * @param vec
	 * @return
	 */
	public static List toList(Object[] vec) {
		List list = Arrays.asList(vec);
		return list;
	}

	/**
	 * String[] 数组转换为 String 对象 用分割符分开
	 * 
	 * @param vec
	 * @return
	 */
	public static String toString(String[] vec, String delima) {
		String retStr = "";
		String defaultDelima = ",";
		if (delima != null && !"".equals(delima)) {
			defaultDelima = delima;
		}
		if (vec != null) {
			for (int i = 0; i < vec.length; i++) {
				retStr += vec[i].trim() + defaultDelima;
			}
			retStr = retStr.endsWith(defaultDelima) ? retStr.substring(0,
					retStr.length() - 1) : retStr;
		}
		return retStr;
	}

	public static String toString(String[] vec) {
		return toString(vec, null);
	}

	/**
	 * String 数组转换为 String[] 对象
	 * 
	 * @param vec
	 * @return
	 */
	public static String[] stringTo(String vec, String delima) {
		String[] retStr = null;
		String defaultDelima = ",";
		if (delima != null && !"".equals(delima)) {
			defaultDelima = delima;
		}
		if (vec != null && !"".equals(vec)) {
			retStr = vec.split(defaultDelima);
		}
		return retStr;
	}

	public static String[] stringTo(String vec) {
		return stringTo(vec, null);
	}

	/**
	 * 格式化数字
	 * 
	 * @param number
	 *            数字
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String getFormatNumber(double number, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(number);
	}

	/**
	 * 格式化金额数字，小数位四舍五入(并返回以逗号分隔的字符串)
	 * 
	 * @param number
	 *            double 数字
	 * @param digit
	 *            int 小数位
	 * @return String
	 */
	public static String getFormatCurrency(double number, int digit) {
		String pattern = "#,##0";
		if (digit > 0) {
			pattern += ".";
			for (int i = 0; i < digit; i++) {
				pattern += "0";
			}
		}
		return getFormatNumber(number, pattern);
	}

	/**
	 * 格式化金额数字，小数位四舍五入(并返回以逗号分隔的字符串,保留2位数)
	 * 
	 * @param number
	 * @param digit
	 * @return
	 */
	public static String getFormatCurrency(double number) {
		return getFormatCurrency(number, 2);
	}

	/**
	 * 格式化金额数字，小数位四舍五入(不带逗号分隔的字符串)
	 * 
	 * @param number
	 *            double 数字
	 * @param digit
	 *            int 小数位
	 * @return String
	 */
	public static String getFormatCurrencyNumber(double number, int digit) {
		String pattern = "###0";
		if (digit > 0) {
			pattern += ".";
			for (int i = 0; i < digit; i++) {
				pattern += "0";
			}
		}
		return getFormatNumber(number, pattern);
	}

	/**
	 * 格式化金额数字，小数位四舍五入(不带逗号分隔的字符串,保留2位数)
	 * 
	 * @param number
	 * @param digit
	 * @return
	 */
	public static String getFormatCurrencyNumber(double number) {
		return getFormatCurrencyNumber(number, 2);
	}

	/**
	 * 格式化金额数字由pattern 得到所需要的格式
	 * 
	 * @param number
	 * @param digit
	 * @param pattern
	 * @return
	 */
	public static String getFormatCurrency(double number, String pattern) {
		return getFormatNumber(number, pattern);
	}

	/**
	 * 格式化百分比(保留小数后2位)
	 * 
	 * @param number
	 *            double 数字
	 * @return
	 */
	public static String getFormatPercent(double number) {
		return getFormatPercent(number, 2);
	}

	/**
	 * 格式化百分比
	 * 
	 * @param number
	 *            double 数字
	 * @param digit
	 *            int 小数位
	 * @return
	 */
	public static String getFormatPercent(double number, int digit) {
		String pattern = "0";
		if (digit > 0) {
			pattern += ".";
			for (int i = 0; i < digit; i++) {
				pattern += "0";
			}
		}
		pattern += "%";
		return getFormatNumber(number, pattern);
	}

	/**
	 * 格式化百分比(不带%)
	 * 
	 * @param number
	 * @param digit
	 * @return
	 */
	public static String getFormatPercentNumber(double number, int digit) {
		String pattern = "###0";
		if (digit > 0) {
			pattern += ".";
			for (int i = 0; i < digit; i++) {
				pattern += "0";
			}
		}
		return getFormatNumber(number * 100d, pattern);
	}

	/**
	 * 格式化百分比(不带%)
	 * 
	 * @param number
	 * @return
	 */
	public static String getFormatPercentNumber(double number) {
		return getFormatPercentNumber(number, 2);
	}

	/**
	 * 判断是否有小数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean hasDigitNum(double num) {
		return num % 1.0 != 0.0;
	}

	/**
	 * 将数字转换为人民币大写
	 * 
	 */

	static char pp(char c) {
		switch (c) {
		case '1':
			return '壹';
		case '2':
			return '贰';
		case '3':
			return '叁';
		case '4':
			return '肆';
		case '5':
			return '伍';
		case '6':
			return '陆';
		case '7':
			return '柒';
		case '8':
			return '捌';
		case '9':
			return '玖';
		case '0':
			return '零';
		}
		return c;
	}

	public static StringBuffer ZhuanHuan(String iin) {
		String in = iin;

		boolean xb = false; // 默认没有小数
		if (in.indexOf(".") != -1) {
			xb = true;
		}

		// 如果有小数点就将其按小数点分割,并分别存入s1和s2中
		StringBuffer out = new StringBuffer("");
		String s1 = new String("");
		String s2 = new String("");
		if (in.indexOf('.') != -1) {
			in = in.replace('.', '#');
			String[] s = in.split("#");
			s1 = s[0];
			s2 = s[1];
		} else {
			s1 = in;
		}

		// 输出小数点左边的数
		if (s1.length() >= 9) { // 亿
			// System.out.println("亿区间");
			String yi = s1.substring(0, s1.length() - 8);
			String wan = s1.substring(s1.length() - 8, s1.length() - 4);
			String qian = s1.substring(s1.length() - 4, s1.length());

			int len = yi.length();
			boolean b = true;

			// 处理亿区间
			for (int i = 0; i < yi.length(); i++) {
				if (len == 4) {
					out.append(pp(yi.charAt(i)));
					out.append(" 仟 ");
					len--;
				} else if (len == 3) {
					/*
					 * if (yi.charAt(i) == '0' ) { b = false ; out.append( " 零 "
					 * ); len -- ; continue ; } else {
					 */
					out.append(pp(yi.charAt(i)));
					out.append(" 佰 ");
					len--;
					continue;
					// }
				} else if (len == 2) {
					/*
					 * if (yi.charAt(i) == '0' && ! b) { len -- ; continue ; }
					 * if (yi.charAt(i) == '0' && b) { b = false ; out.append( "
					 * 零 " ); len -- ; continue ; } else {
					 */
					out.append(pp(yi.charAt(i)));
					out.append(" 拾 ");
					len--;
					continue;
					// }
				} else if (len == 1) {
					/*
					 * if (yi.charAt(i) == '0' ) { out.append( " 亿 " ); len -- ;
					 * continue ; } else {
					 */
					out.append(pp(yi.charAt(i)));
					out.append(" 亿 ");
					len--;
					// }
				}
			}
			/*
			 * if (out.charAt(out.indexOf( " 亿 " ) - 1 ) == '零' ) {
			 * out.deleteCharAt(out.indexOf( " 亿 " ) - 1 ); out.append( " 零 " );
			 * } if (yi.charAt(yi.length() - 1 ) == '0' ) { out.append( " 零 " );
			 * }
			 * 
			 * if (out.charAt(out.length() - 2 ) == out.charAt(out.length() - 1
			 * )) { // 删除最后多余的零 out.deleteCharAt(out.lastIndexOf( " 零 " )); }
			 */
			// 处理万区间
			len = wan.length();
			b = true;
			if (wan.charAt(0) == '0' && wan.charAt(1) == '0'
					&& wan.charAt(2) == '0' && wan.charAt(3) == '0' && false) {
				// 如果万区间全为0就什么也不做
			} else {
				for (int i = 0; i < wan.length(); i++) {
					if (len == 4) {
						/*
						 * if (wan.charAt(i) == '0' ) { out.append( " 零 " ); len
						 * -- ; continue ; }
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 仟 ");
						len--;
						continue;
					} else if (len == 3) {
						/*
						 * if (wan.charAt(i) == '0' ) { b = false ; out.append(
						 * " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 佰 ");
						len--;
						continue;
						// }
					} else if (len == 2) {
						/*
						 * if (wan.charAt(i) == '0' && ! b) { len -- ; continue
						 * ; } if (wan.charAt(i) == '0' && b) { b = false ;
						 * out.append( " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 拾 ");
						len--;
						continue;
						// }
					} else if (len == 1) {
						/*
						 * if (wan.charAt(i) == '0' ) { out.append( " 万 " ); len
						 * -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 万 ");
						len--;
						continue;
						// }
					}
				}
				/*
				 * if (out.charAt(out.indexOf( " 万 " ) - 1 ) == '零' ) {
				 * out.deleteCharAt(out.indexOf( " 万 " ) - 1 ); out.append(
				 * " 零 " ); } if (wan.charAt(wan.length() - 1 ) == '0' ) {
				 * out.append( " 零 " ); } if (out.charAt(out.length() - 2 ) ==
				 * out .charAt(out.length() - 1 )) {
				 * out.deleteCharAt(out.lastIndexOf( " 零 " )); }
				 */
			}

			// 千区间
			len = qian.length();
			b = true;
			if (qian.charAt(0) == '0' && qian.charAt(1) == '0'
					&& qian.charAt(2) == '0' && qian.charAt(3) == '0' && false) {
				// 如果千区间全为0就什么也不做
			} else {
				for (int i = 0; i < qian.length(); i++) {
					if (len == 4) {
						/*
						 * if (qian.charAt(i) == '0' ) { out.append( " 零 " );
						 * len -- ; continue ; }
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 仟 ");
						len--;
						continue;
					} else if (len == 3) {
						/*
						 * if (qian.charAt(i) == '0' ) { b = false ; out.append(
						 * " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 佰 ");
						len--;
						continue;
						// }
					} else if (len == 2) {
						/*
						 * if (qian.charAt(i) == '0' && ! b) { len -- ; continue
						 * ; } if (qian.charAt(i) == '0' && b) { b = false ;
						 * out.append( " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 拾 ");
						len--;
						continue;
						// }
					} else if (len == 1) {
						/*
						 * if (qian.charAt(i) == '0' ) { out.append( " 元 " );
						 * len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 元 ");
						len--;
						continue;
						// }
					}
				}
			}
			/*
			 * if (out.charAt(out.length() - 2 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 2 ); }
			 * 
			 * if (out.charAt(out.length() - 1 ) == '零' ) { // 删除多余的零 while
			 * (out.charAt(out.length() - 1 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 1 ); } out.append( " 元 " ); } for
			 * ( int i = out.length() - 1 ; i >= 0 ; i -- ) { // 删除多余的零 if
			 * (out.indexOf( " 零 " ) !=- 1 ) { if (out.charAt(out.lastIndexOf( "
			 * 零 " ) - 1 ) == '零' ) { out.deleteCharAt(out.lastIndexOf( " 零 " )
			 * - 1 ); } } }
			 */

			// 处理小数
			if (xb) {
				int llen = s2.length();
				if (llen >= 2) {
					/*
					 * if (s2.charAt( 0 ) == '0' && s2.charAt( 1 ) == '0' ) { //
					 * 小数为两个0就什么也不做 } else if (s2.charAt( 1 ) == '0' ) {
					 * out.append( pp(s2.charAt( 0 ))); out.append( " 角 " ); }
					 * else if (s2.charAt( 0 ) == '0' ) { out.append( " 零 " );
					 * out.append( pp(s2.charAt( 1 ))); out.append( " 分 " ); }
					 * else {
					 */
					out.append(pp(s2.charAt(0)));
					out.append(" 角 ");
					out.append(pp(s2.charAt(1)));
					out.append(" 分 ");
					// }
				}
				if (llen == 1) {
					if (s2.charAt(0) == '0') {
						// 小数为两个0就什么也不做
						/* } else { */
						out.append(pp(s2.charAt(0)));
						out.append(" 角 ");
					}
				}
				return out;
			} else {
				return out;
			}

		} else if (s1.length() >= 5) { // 万
			// System.out.println("万区间");
			String wan = s1.substring(0, s1.length() - 4);
			String qian = s1.substring(s1.length() - 4, s1.length());
			// 处理万区间
			int len = wan.length();
			boolean b = true;
			if (wan.charAt(0) == '0' && wan.charAt(1) == '0'
					&& wan.charAt(2) == '0' && wan.charAt(3) == '0' && false) {
				// 如果万区间全为0就什么也不做
			} else {
				for (int i = 0; i < wan.length(); i++) {
					if (len == 4) {
						out.append(pp(wan.charAt(i)));
						out.append(" 仟 ");
						len--;
					} else if (len == 3) {
						/*
						 * if (wan.charAt(i) == '0' ) { b = false ; out.append(
						 * " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 佰 ");
						len--;
						continue;
						// }
					} else if (len == 2) {
						/*
						 * if (wan.charAt(i) == '0' && ! b) { len -- ; continue
						 * ; } if (wan.charAt(i) == '0' && b) { b = false ;
						 * out.append( " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 拾 ");
						len--;
						continue;
						// }
					} else if (len == 1) {
						/*
						 * if (wan.charAt(i) == '0' ) { out.append( " 万 " ); len
						 * -- ; continue ; } else {
						 */
						out.append(pp(wan.charAt(i)));
						out.append(" 万 ");
						len--;
						// }
					}
				}
				/*
				 * if (out.charAt(out.indexOf( " 万 " ) - 1 ) == '零' ) {
				 * out.deleteCharAt(out.indexOf( " 万 " ) - 1 ); out.append(
				 * " 零 " ); } if (wan.charAt(wan.length() - 1 ) == '0' ) {
				 * out.append( " 零 " ); } if (out.charAt(out.length() - 2 ) ==
				 * out .charAt(out.length() - 1 )) {
				 * out.deleteCharAt(out.lastIndexOf( " 零 " )); }
				 */
			}

			// 千区间
			len = qian.length();
			b = true;
			if (qian.charAt(0) == '0' && qian.charAt(1) == '0'
					&& qian.charAt(2) == '0' && qian.charAt(3) == '0' && false) {
				// 如果千区间全为0就什么也不做
			} else {
				for (int i = 0; i < qian.length(); i++) {
					if (len == 4) {
						/*
						 * if (qian.charAt(i) == '0' && out.charAt(out.length()
						 * - 1 ) != '零' ) { out.append( " 零 " ); len -- ;
						 * continue ; } else if (qian.charAt(i) == '0' &&
						 * out.charAt(out.length() - 1 ) == '零' ) { len -- ;
						 * continue ; }
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 仟 ");
						len--;
					} else if (len == 3) {
						/*
						 * if (qian.charAt(i) == '0' ) { b = false ; out.append(
						 * " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 佰 ");
						len--;
						continue;
						// }
					} else if (len == 2) {
						/*
						 * if (qian.charAt(i) == '0' && ! b) { len -- ; continue
						 * ; } if (qian.charAt(i) == '0' && b) { b = false ;
						 * out.append( " 零 " ); len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 拾 ");
						len--;
						continue;
						// }
					} else if (len == 1) {
						/*
						 * if (qian.charAt(i) == '0' ) { out.append( " 元 " );
						 * len -- ; continue ; } else {
						 */
						out.append(pp(qian.charAt(i)));
						out.append(" 元 ");
						len--;
						// }
					}
				}
			}
			/*
			 * if (out.charAt(out.length() - 2 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 2 ); } if
			 * (out.charAt(out.length() - 1 ) == '零' ) { // 删除多余的零 while
			 * (out.charAt(out.length() - 1 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 1 ); } out.append( " 元 " ); } for
			 * ( int i = out.length() - 1 ; i >= 0 ; i -- ) { // 删除多余的零 if
			 * (out.indexOf( " 零 " ) !=- 1 ) { if (out.charAt(out.lastIndexOf( "
			 * 零 " ) - 1 ) == '零' ) { out.deleteCharAt(out.lastIndexOf( " 零 " )
			 * - 1 ); } } }
			 */

			// 处理小数点右边的数
			if (xb) {
				int llen = s2.length();
				if (llen >= 2) {
					/*
					 * if (s2.charAt( 0 ) == '0' && s2.charAt( 1 ) == '0' ) { //
					 * 小数为两个0就什么也不做 } else if (s2.charAt( 1 ) == '0' ) {
					 * out.append( pp(s2.charAt( 0 ))); out.append( " 角 " ); }
					 * else if (s2.charAt( 0 ) == '0' ) { out.append( " 零 " );
					 * out.append( pp(s2.charAt( 1 ))); out.append( " 分 " ); }
					 * else {
					 */
					out.append(pp(s2.charAt(0)));
					out.append(" 角 ");
					out.append(pp(s2.charAt(1)));
					out.append(" 分 ");
					// }
				}
				if (llen == 1) {
					if (s2.charAt(0) == '0') {
						// 小数为两个0就什么也不做
					} else {
						out.append(pp(s2.charAt(0)));
						out.append(" 角 ");
					}
				}
				return out;
			} else {
				return out;
			}

		} else { // 千
			// System.out.println("千区间");
			String qian = s1;
			// 千区间
			int len = qian.length();
			boolean b = true;
			if (qian.charAt(0) == '0' && len == 1) {
				out.append(pp(qian.charAt(0)) + " 元 ");
			} else if (qian.charAt(0) == '0' && qian.charAt(1) == '0'
					&& qian.charAt(2) == '0' && qian.charAt(3) == '0') {

				out.append(pp(qian.charAt(0)) + " 元 ");
				// 如果千区间全为0就什么也不做
			} else {
				for (int i = 0; i < qian.length(); i++) {
					if (len == 4) {
						out.append(pp(qian.charAt(i)));
						out.append(" 仟 ");
						len--;
					} else if (len == 3) {
						// if (qian.charAt(i) == '0' ) {
						// b = false ;
						// out.append( " 零 " );
						// out.append( " 拾 " );
						// len -- ;
						// continue ;
						// } else {
						out.append(pp(qian.charAt(i)));
						out.append(" 佰 ");
						len--;
						continue;
						// }
					} else if (len == 2) {
						// if (qian.charAt(i) == '0' && ! b) {
						// len -- ;
						// continue ;
						// }
						// if (qian.charAt(i) == '0' && b) {
						// b = false ;
						// out.append( pp(qian.charAt(i)));
						// out.append( " 拾 " );
						// len -- ;
						// continue ;
						// } else {
						out.append(pp(qian.charAt(i)));
						out.append(" 拾 ");
						len--;
						continue;
						// }
					} else if (len == 1) {
						// if (qian.charAt(i) == '0' ) {
						// out.append( " 元 " );
						// len -- ;
						// continue ;
						// } else {
						out.append(pp(qian.charAt(i)));
						out.append(" 元 ");
						len--;
						// }
					}
				}
			}
			/*
			 * if (out.charAt(out.length() - 2 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 2 ); }
			 * 
			 * if (out.charAt(out.length() - 1 ) == '零' ) { // 删除多余的零 while
			 * (out.charAt(out.length() - 1 ) == '零' ) {
			 * out.deleteCharAt(out.length() - 1 ); } out.append( " 元 " ); }
			 */

			// 处理小数点右边的数
			if (xb) {
				int llen = s2.length();
				if (llen >= 2) {
					/*
					 * if (s2.charAt( 0 ) == '0' && s2.charAt( 1 ) == '0' ) { //
					 * 小数为两个0就什么也不做 } else if (s2.charAt( 1 ) == '0' ) {
					 * out.append( pp(s2.charAt( 0 ))); out.append( " 角 " ); }
					 * else if (s2.charAt( 0 ) == '0' ) { out.append( " 零 " );
					 * out.append( pp(s2.charAt( 1 ))); out.append( " 分 " ); }
					 * else {
					 */
					out.append(pp(s2.charAt(0)));
					out.append(" 角 ");
					out.append(pp(s2.charAt(1)));
					out.append(" 分 ");
					// }
				}
				if (llen == 1) {
					if (s2.charAt(0) == '0') {
						// 小数为两个0就什么也不做
					} else {
						out.append(pp(s2.charAt(0)));
						out.append(" 角 ");
					}
				}
				return out;
			} else {
				return out;
			}
		}
	}

	/**
	 * 转换成HTML代码
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeHtml(String s) {
		if (s == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (c == '"') {
				sb.append("&quot;");
			} else if (c == '\n') {
				sb.append("<br />");
			} else {
				sb.append(c);
			}
		}
		return StringUtils.replace(sb.toString(), "  ", "&nbsp;");
	}

	/**
	 * 将字符串转换成int数组
	 * 
	 * @param str
	 * @return
	 */
	public static int[] toIntArray(String str) {
		if (str == null) {
			return null;
		}
		String[] s = StringUtils.split(str, ",");
		int[] r = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			r[i] = Integer.parseInt(s[i]);
		}
		return r;
	}

	/**
	 * 取最后一个字母的下一个字母 author:key createDate:2010-9-19
	 * 
	 * @param str
	 * @return
	 */
	public static char getLastChar(String str) {
		if (str == null || str.length() <= 0) {
			return 'A';
		}
		char s = (char) (str.charAt(str.length() - 1) + 1);
		return s;
	}

	public static String getContent(String s) {
		if (s == null)
			return "";

		s = s.replaceAll("&ensp;", " ");
		s = s.replaceAll("&nbsp;", " ");
		s = s.replaceAll("&emsp;", "　");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&ldquo;", "“");
		s = s.replaceAll("&rdquo;", "”");
		s = s.replaceAll("&quot;", "“");
		s = s.replaceAll("&rsquo;", "’");
		s = s.replaceAll("&lsquo;", "‘");
		s = s.replaceAll("&mdash;", "—");
		s = s.replaceAll("&ndash;", "–");
		s = s.replaceAll("&middot;", "·");
		s = s.replaceAll("&hellip;", "…");
		s = s.replaceAll("<br>", "\r\n");
		s = s.replaceAll("<br/>", "\r\n");
		s = s.replaceAll("<br />", "\r\n");
		s = s.replaceAll("  ", "　");
		s = s.replaceAll("&amp;", "&");

		return s;
	}

	public static String getHtmlContent(String s) {
		if (s == null)
			return "";
		s = s.replaceAll("&", "&amp;");
		s = s.replaceAll("　", " ");
		s = s.replaceAll("…", "&hellip;");
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("\r\n", "<br>");
		s = s.replaceAll("\r\n", "<br/>");
		s = s.replaceAll("\r\n", "<br />");
		s = s.replaceAll("“", "&ldquo;");
		s = s.replaceAll("”", "&rdquo;");
		s = s.replaceAll("“", "&quot;");
		s = s.replaceAll("’", "&rsquo;");
		s = s.replaceAll("‘", "&lsquo;");
		s = s.replaceAll("—", "&mdash;");
		s = s.replaceAll("–", "&ndash;");
		s = s.replaceAll("·", "&middot;");

		return s;
	}

	public static void main(String[] args) {
		double d = 21322.1;
		System.out.println(getFormatCurrency(d, 2));
		System.out.println(hasDigitNum(d));

		System.out.println(getFormatPercent(0.50426, 2));
		System.out.println(getFormatPercent(0.501));
		System.out.println(getFormatPercentNumber(0.50));

		System.out.println(getFormatCurrency(d));
		System.out.println(getFormatCurrencyNumber(d));

		System.out.println(encodeHtml("1122\n 3  3534 <@@#>"));
	}
}
