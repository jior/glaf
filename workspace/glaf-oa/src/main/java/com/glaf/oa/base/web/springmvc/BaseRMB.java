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
package com.glaf.oa.base.web.springmvc;

import java.util.HashMap;
import java.util.Map;

public class BaseRMB {

	private static String points[] = { "Ω«", "∑÷" };

	private static String zero2nine[] = { "¡„", "“º", "∑°", "»˛", "À¡", "ŒÈ", "¬Ω",
			"∆‚", "∞∆", "æ¡" };

	private static String unit[][] = { { "‘≤", "ÕÚ", "“⁄" }, { "", " ∞", "∞€", "«™" } };

	private static Map<String, Object> map = new HashMap<String, Object>();

	/**
	 *  ˝◊÷◊™»À√Ò±“¥Û–¥
	 * 
	 * @param n
	 * @return
	 */
	public static String upperRMB(double n) {
		String head = n < 0 ? "∏∫" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < points.length; i++) {
			s += (zero2nine[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + points[i])
					.replaceAll("(¡„.)+", "");
		}
		if (s.length() < 1) {
			s = "’˚";
		}
		int integerPart = (int) Math.floor(n);
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = zero2nine[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(¡„.)*¡„$", "").replaceAll("^$", "¡„") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(¡„.)*¡„‘≤", "‘≤").replaceFirst("(¡„.)+", "")
						.replaceAll("(¡„.)+", "¡„").replaceAll("^’˚$", "¡„‘≤’˚");
	}

	public static void main(String[] args) {
		String str = upperRMB(110);
		System.out.println(str);
		String strs = BaseRMB.class.getSimpleName();
		map.put("", "");
		map.clear();
		System.out.println(strs);
	}

}