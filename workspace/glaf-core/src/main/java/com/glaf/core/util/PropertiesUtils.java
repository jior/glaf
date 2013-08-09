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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class PropertiesUtils {

	private static final String keyValueSeparators = "=: \t\r\n\f";

	private static final String strictKeyValueSeparators = "=:";

	private static final String whiteSpaceChars = " \t\r\n\f";

	private static boolean continueLine(String line) {
		int slashCount = 0;
		int index = line.length() - 1;
		while ((index >= 0) && (line.charAt(index--) == '\\'))
			slashCount++;
		return (slashCount % 2 != 0);
	}

	public static String findValue(Properties properties, String... keys) {
		for (String key : keys) {
			String value = get(properties, key);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public static String get(Properties properties, String key) {
		return properties.getProperty(key);
	}

	public static String get(Properties properties, String key,
			String defaultVal) {
		String val = get(properties, key);
		return val == null ? defaultVal : val;
	}

	public static boolean getBoolean(Properties properties, String key,
			boolean defaultVal) {
		String val = get(properties, key);
		return val == null ? defaultVal : Boolean.parseBoolean(val);
	}

	public static double getDouble(Properties properties, String key,
			double defaultVal) {
		String val = get(properties, key);
		return val == null ? defaultVal : Double.parseDouble(val);
	}

	public static <T extends Enum<T>> T getEnum(Properties properties,
			String key, Class<T> type, T defaultValue) {
		String val = get(properties, key);
		return val == null ? defaultValue : Enum.valueOf(type, val);
	}

	public final static InputStream getInputStream(String resourceName) {
		try {
			Resource resource = new ClassPathResource(resourceName);
			return resource.getInputStream();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final static byte[] getBytes(String resourceName) {
		InputStream inputStream = null;
		try {
			Resource resource = new ClassPathResource(resourceName);
			inputStream = resource.getInputStream();
			return FileUtils.getBytes(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException ex) {
			}
		}
	}

	public static int getInt(Properties properties, String key, int defaultVal) {
		return (int) getLong(properties, key, defaultVal);
	}

	public static long getLong(Properties properties, String key,
			long defaultVal) {
		String val = get(properties, key);
		return val == null ? defaultVal : Long.parseLong(val);
	}

	public static Properties loadClassPathResource(String resourceName) {
		Resource resource = new ClassPathResource(resourceName);
		InputStream in = null;
		try {
			if (resource.exists() && resource.getFile().isFile()) {
				in = resource.getInputStream();
				return loadProperties(in);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(in);
		}
		return null;
	}

	public static Properties loadFilePathResource(String filename) {
		Resource resource = new FileSystemResource(filename);
		InputStream in = null;
		try {
			if (resource.exists() && resource.getFile().isFile()) {
				in = resource.getInputStream();
				return loadProperties(in);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(in);
		}
		return null;
	}

	public static Properties loadFilePathResource(File file) {
		Resource resource = new FileSystemResource(file);
		InputStream in = null;
		try {
			in = resource.getInputStream();
			return loadProperties(in);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(in);
		}
	}

	public static LinkedHashMap<String, String> load(InputStream inputStream)
			throws IOException {
		LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputStream));
		while (true) {
			// Get next line
			String line = in.readLine();
			if (line == null)
				return properties;

			if (line.length() > 0) {

				// Find start of key
				int len = line.length();
				int keyStart;
				for (keyStart = 0; keyStart < len; keyStart++)
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
						break;

				// Blank lines are ignored
				if (keyStart == len)
					continue;

				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(keyStart);
				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();
						if (nextLine == null)
							nextLine = "";
						String loppedLine = line.substring(0, len - 1);
						// Advance beyond whitespace on new line
						int startIndex;
						for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
							if (whiteSpaceChars.indexOf(nextLine
									.charAt(startIndex)) == -1)
								break;
						nextLine = nextLine.substring(startIndex,
								nextLine.length());
						line = loppedLine + nextLine;
						len = line.length();
					}

					// Find separation between key and value
					int separatorIndex;
					for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);
						if (currentChar == '\\')
							separatorIndex++;
						else if (keyValueSeparators.indexOf(currentChar) != -1)
							break;
					}

					// Skip over whitespace after key if any
					int valueIndex;
					for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len)
						if (strictKeyValueSeparators.indexOf(line
								.charAt(valueIndex)) != -1)
							valueIndex++;

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;
						valueIndex++;
					}
					String key = line.substring(keyStart, separatorIndex);
					String value = (separatorIndex < len) ? line.substring(
							valueIndex, len) : "";

					// Convert then store key and value
					key = loadConvert(key);
					value = loadConvert(value);
					properties.put(key, value);
				}
			}
		}
	}

	private static String loadConvert(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static Properties loadProperties(InputStream inputStream)
			throws IOException {
		Properties properties = new Properties();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputStream));
		while (true) {
			// Get next line
			String line = in.readLine();
			if (line == null)
				return properties;

			if (line.length() > 0) {

				// Find start of key
				int len = line.length();
				int keyStart;
				for (keyStart = 0; keyStart < len; keyStart++)
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
						break;

				// Blank lines are ignored
				if (keyStart == len)
					continue;

				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(keyStart);
				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();
						if (nextLine == null)
							nextLine = "";
						String loppedLine = line.substring(0, len - 1);
						// Advance beyond whitespace on new line
						int startIndex;
						for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
							if (whiteSpaceChars.indexOf(nextLine
									.charAt(startIndex)) == -1)
								break;
						nextLine = nextLine.substring(startIndex,
								nextLine.length());
						line = loppedLine + nextLine;
						len = line.length();
					}

					// Find separation between key and value
					int separatorIndex;
					for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);
						if (currentChar == '\\')
							separatorIndex++;
						else if (keyValueSeparators.indexOf(currentChar) != -1)
							break;
					}

					// Skip over whitespace after key if any
					int valueIndex;
					for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len)
						if (strictKeyValueSeparators.indexOf(line
								.charAt(valueIndex)) != -1)
							valueIndex++;

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;
						valueIndex++;
					}
					String key = line.substring(keyStart, separatorIndex);
					String value = (separatorIndex < len) ? line.substring(
							valueIndex, len) : "";

					// Convert then store key and value
					key = loadConvert(key);
					value = loadConvert(value);
					properties.setProperty(key, value);
				}
			}
		}
	}

	public static void save(Resource resource, Map<String, String> dataMap)
			throws IOException {
		StringBuffer writer = new StringBuffer();
		Set<Entry<String, String>> entrySet = dataMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			writer.append(key);
			writer.append('=');
			writer.append(value);
			writer.append('\n');
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(resource.getFile());
			fout.write(writer.toString().getBytes());
			fout.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void save(String filename, Map<String, String> dataMap)
			throws IOException {
		StringBuffer writer = new StringBuffer();
		Set<Entry<String, String>> entrySet = dataMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			writer.append(key);
			writer.append('=');
			writer.append(value);
			writer.append('\n');
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filename);
			fout.write(writer.toString().getBytes());
			fout.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void save(String filename, Properties p) throws IOException {
		StringBuffer writer = new StringBuffer();
		java.util.Enumeration<?> e = p.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = p.getProperty(key);
			writer.append(key);
			writer.append('=');
			writer.append(value);
			writer.append('\n');
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filename);
			fout.write(writer.toString().getBytes());
			fout.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void saveClassPathResource(String resourceName,
			Properties props) {
		Resource resource = new ClassPathResource(resourceName);
		try {
			StringBuffer writer = new StringBuffer();
			Iterator<Object> iterator = props.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = props.get(key);
				writer.append(key);
				writer.append('=');
				writer.append(value);
				writer.append('\n');
			}
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(resource.getFile());
				fout.write(writer.toString().getBytes());
				fout.close();
			} catch (IOException ex) {
				throw ex;
			} finally {
				try {
					if (fout != null) {
						fout.close();
						fout = null;
					}
				} catch (IOException ioe) {
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}