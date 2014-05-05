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

package com.glaf.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class CaptchaUtils {

	// 随机产生的字符串
	private static final String RANDOM_STRS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

	private static final String FONT_NAME = "Consolas";// 字体

	private static final int FONT_SIZE = 24;// 字体大小

	private static int width = 85;// 图片宽

	private static int height = 25;// 图片高

	private static int lineNum = 155;// 干扰线数量

	private static int strNum = 4;// 随机产生字符数量

	private static Random random = new Random();

	private CaptchaUtils() {

	}

	/**
	 * 绘制干扰线
	 */
	private static void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int x0 = random.nextInt(12);
		int y0 = random.nextInt(12);
		g.drawLine(x, y, x + x0, y + y0);
	}

	/**
	 * 绘制字符串
	 */
	private static String drowString(Graphics g, int i) {
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));
		String rand = String.valueOf(getRandomString(random.nextInt(RANDOM_STRS
				.length())));
		g.drawString(rand, 20 * i + 4, 19);
		return rand;
	}

	/**
	 * 生成随机图片
	 */
	public static BufferedImage genRandomCodeImage(StringBuffer randomCode) {
		// BufferedImage类是具有缓冲区的Image类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取Graphics对象,便于对图像进行各种绘制操作
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setColor(getRandColor(160, 200));

		// 绘制干扰线
		for (int i = 0; i <= lineNum; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		g.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

		for (int i = 0; i < strNum; i++) {
			randomCode.append(drowString(g, i));
		}
		g.dispose();
		return image;
	}

	/**
	 * 给定范围获得随机颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 获取随机的字符
	 */
	private static String getRandomString(int num) {
		return String.valueOf(RANDOM_STRS.charAt(num));
	}

	public static void main(String[] args) {
		StringBuffer code = new StringBuffer();
		BufferedImage image = CaptchaUtils.genRandomCodeImage(code);
		System.out.println("random code = " + code);
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", new FileOutputStream(new File(
					"random-code.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
