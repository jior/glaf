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

import java.util.HashMap;

public class MimeType {
	private static HashMap stringMap = new HashMap();

	private static HashMap contentTypeMap = new HashMap();

	private int value = 0;

	public static final int GIF_TYPE = 1;

	public static final int JPG_TYPE = 2;

	public static final int PNG_TYPE = 3;

	public static final int BMP_TYPE = 4;

	public static final int IND_TYPE = 5;

	public static final int JPEG_TYPE = 6;

	public static final int ICO_TYPE = 7;

	public static final int SWF_TYPE = 8;

	public static final int CSV_TYPE = 1001;

	public static final int EXCEL_TYPE = 1002;

	public static final int PDF_TYPE = 1003;

	public static final int XML_TYPE = 1004;

	private MimeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		return (String) stringMap.get(this);
	}

	public static MimeType stringToType(String type) {
		java.util.Iterator keys = stringMap.keySet().iterator();
		while (keys.hasNext()) {
			MimeType key = (MimeType) keys.next();
			String stringValue = (String) stringMap.get(key);
			if (stringValue.equals(type)) {
				return key;
			}
		}
		return null;

	}

	public static String typeToString(MimeType type) {
		return (String) stringMap.get(type);
	}

	/**
	 * 通过文件名称获得文件的类型
	 * 
	 * @param pFileName
	 * @return
	 */
	public static String getFileType(String filename) {
		String tReturn = "";
		int start = filename.lastIndexOf(".");
		if (start != -1) {
			tReturn = filename.substring(start + 1);
		}
		return tReturn;
	}

	public static boolean isMultimedia(String filename) {
		String tReturn = "";
		int start = filename.lastIndexOf(".");
		if (start != -1) {
			tReturn = filename.substring(start + 1);
		}
		tReturn = tReturn.toLowerCase();
		if (stringMap.containsValue(tReturn)) {
			return true;
		}
		return false;
	}

	public static String getDisplayScript(String ext, String source, String alt) {
		MimeType type = stringToType(ext);
		int value = type.getValue();
		return getDisplayScript(value, source, alt);
	}

	public static String getDisplayScript(int typeCode, String source,
			String alt) {
		if (source == null) {
			return null;
		}
		if (alt == null) {
			alt = "";
		}
		StringBuffer buffer = new StringBuffer();
		switch (typeCode) {
		case GIF_TYPE:
		case JPG_TYPE:
		case PNG_TYPE:
		case BMP_TYPE:
		case IND_TYPE:
		case ICO_TYPE:
		case JPEG_TYPE:
			buffer.append("<img src=\"").append(source).append("\" alt = \"")
					.append(alt).append("\" border=\"0\" align=\"middle\"> ");
			break;
		case SWF_TYPE:
			buffer
					.append(
							"<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"")
					.append(
							"codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\" width=\"100%\" height=\"100%\">")
					.append("\n\t<param name=\"movie\" value=\"")
					.append(source)
					.append("\">")
					.append("\n\t<param name=\"quality\" value=\"high\">")
					.append("\n\t<embed src=\"")
					.append(source)
					.append("\" quality=\"high\" ")
					.append(
							"pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ")
					.append(
							"type=\"application/x-shockwave-flash\" width=\"100%\" height=\"100%\">")
					.append("\n\t></embed>").append("\n</object>");
			break;
		}

		return buffer.toString();

	}

	public static MimeType GIF = new MimeType(GIF_TYPE);

	public static MimeType JPG = new MimeType(JPG_TYPE);

	public static MimeType PNG = new MimeType(PNG_TYPE);

	public static MimeType BMP = new MimeType(BMP_TYPE);

	public static MimeType IND = new MimeType(IND_TYPE);

	public static MimeType JPEG = new MimeType(JPEG_TYPE);

	public static MimeType ICO = new MimeType(ICO_TYPE);

	public static MimeType SWF = new MimeType(SWF_TYPE);

	static {
		stringMap.put(GIF, "gif");
		stringMap.put(JPG, "jpg");
		stringMap.put(PNG, "png");
		stringMap.put(BMP, "bmp");
		stringMap.put(IND, "ind");
		stringMap.put(JPEG, "jpeg");
		stringMap.put(ICO, "ico");
		stringMap.put(SWF, "swf");
		contentTypeMap.put("image", "image/*");
		contentTypeMap.put("audio", "audio/*");
		contentTypeMap.put("video", "video/*");
	}

}
