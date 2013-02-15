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

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public final class Dom4jToolkit {
	/**
	 * ����XML�ĵ����ļ�
	 * 
	 * @param doc
	 *            XML�ĵ�
	 * @param filename
	 *            �ļ���
	 * @throws IOException
	 */
	public static void saveDoument(Document doc, String filename) {
		saveDoument(doc, filename, "UTF-8");
	}

	/**
	 * ����XML�ĵ����ļ���
	 * 
	 * @param doc
	 *            XML�ĵ�
	 * @param filename
	 *            �ļ���
	 * @param encoding
	 *            ���뷽ʽ
	 * @throws IOException
	 */
	public static void saveDoument(Document doc, String filename,
			String encoding) {
		createDoument(doc, filename, encoding, false);
	}

	/**
	 * ����XML�ĵ����ļ�
	 * 
	 * @param doc
	 *            XML�ĵ�
	 * @param filename
	 *            �ļ���
	 * @throws IOException
	 */
	public static void savePrettyDoument(Document doc, String filename) {
		savePrettyDoument(doc, filename, "UTF-8");
	}

	/**
	 * ����XML�ĵ����ļ���ע�⣺��������������ڸ���������XML�ĵ��е������
	 * 
	 * @param doc
	 *            XML�ĵ�
	 * @param filename
	 *            �ļ���
	 * @param encoding
	 *            ���뷽ʽ
	 * @throws IOException
	 */
	public static void savePrettyDoument(Document doc, String filename,
			String encoding) {
		createDoument(doc, filename, encoding, true);
	}

	public static void createDoument(Document doc, String filename,
			String encoding, boolean createPrettyPrint) {
		OutputFormat format = null;
		if (createPrettyPrint) {
			format = OutputFormat.createPrettyPrint();
		} else {
			format = new OutputFormat();
		}
		format.setEncoding(encoding);
		createDoument(doc, filename, format);
	}

	public static void createDoument(Document doc, String filename,
			OutputFormat format) {
		FileWriter out = null;
		XMLWriter writer = null;
		try {
			out = new FileWriter(filename);
			writer = new XMLWriter(out, format);
			writer.write(doc);
			out.close();
		} catch (IOException ex) {
			writer = null;
			out = null;
			throw new RuntimeException(ex.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * װ��XML�ĵ�
	 * 
	 * @param filename
	 *            �ļ���
	 * @return XML�ĵ�����
	 * @throws Exception
	 */
	public static Document loadDocument(String filename) throws Exception {
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(filename);
		return doc;
	}

	/**
	 * ��XML�ĵ�ת�����ֽ����顣ע�⣺��������������ڸ���������XML�ĵ��е������
	 * 
	 * @param doc
	 * @return �ֽ���
	 * @throws Exception
	 */
	public static byte[] getBytesFromPrettyDocument(Document doc) {
		return getBytesFromPrettyDocument(doc, "UTF-8");
	}

	/**
	 * ��XML�ĵ�ת�����ֽ����顣ע�⣺��������������ڸ���������XML�ĵ��е������
	 * 
	 * @param doc
	 * @param encoding
	 * @return �ֽ���
	 * @throws Exception
	 */
	public static byte[] getBytesFromPrettyDocument(Document doc,
			String encoding) {
		if (doc == null) {
			return null;
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		return getBytesFromDocument(doc, format);
	}

	/**
	 * ��ȡ�ĵ��ֽ���
	 * 
	 * @param doc
	 *            Document
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getBytesFromDocument(Document doc) {
		return getBytesFromDocument(doc, "UTF-8");
	}

	/**
	 * ��ȡ�ĵ��ֽ���
	 * 
	 * @param doc
	 *            Document
	 * @param encoding
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getBytesFromDocument(Document doc, String encoding) {
		OutputFormat format = new OutputFormat();
		format.setEncoding(encoding);
		return getBytesFromDocument(doc, format);
	}

	/**
	 * ��ȡ�ĵ��ֽ���
	 * 
	 * @param doc
	 *            Document
	 * @param format
	 *            OutputFormat
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getBytesFromDocument(Document doc, OutputFormat format) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		XMLWriter writer = null;
		byte[] data = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			writer = new XMLWriter(bos, format);
			writer.write(doc);
			if (baos != null) {
				data = baos.toByteArray();
			}
			bos.close();
			baos.close();
			baos = null;
			return data;
		} catch (IOException ex) {
			bos = null;
			writer = null;
			throw new RuntimeException(ex);
		} finally {
			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * ��ָ������ʽ���ļ�ת���ĵ�
	 * 
	 * @param document
	 *            Document
	 * @param stylesheet
	 *            String
	 * @return Document
	 * @throws Exception
	 */
	public static Document styleDocument(Document document, String stylesheet)
			throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(
				stylesheet));
		DocumentSource source = new DocumentSource(document);
		DocumentResult result = new DocumentResult();
		transformer.transform(source, result);
		Document doc = result.getDocument();
		return doc;
	}

	/**
	 * �ֽ���ת�����ĵ�
	 * 
	 * @param bytes
	 *            byte[]
	 * @return Document
	 * @throws Exception
	 */
	public static Document toDocument(byte[] bytes) {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			SAXReader xmlReader = new SAXReader();
			Document doc = xmlReader.read(inputStream);
			return doc;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * �ֽ���ת�����ĵ�
	 * 
	 * @param inputStream
	 * 
	 * @return Document
	 * @throws Exception
	 */
	public static Document toDocument(java.io.InputStream inputStream) {
		try {
			SAXReader xmlReader = new SAXReader();
			Document doc = xmlReader.read(inputStream);
			return doc;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * �ֽ���ת���ɸ��ڵ�
	 * 
	 * @param bytes
	 *            byte[]
	 * @return Element
	 * @throws Exception
	 */
	public static Element toElement(byte[] bytes) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return toElement(inputStream);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * ������ת���ɸ��ڵ�
	 * 
	 * @param inputStream
	 *            InputStream
	 * @return Element
	 * @throws Exception
	 */
	public static Element toElement(InputStream inputStream) throws Exception {
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(inputStream);
		Element root = doc.getRootElement();
		return root;
	}

	/**
	 * ��ȡָ��·���Ľڵ㿽��
	 * 
	 * @param bytes
	 *            byte[]
	 * @param xpath
	 *            String
	 * @return Element
	 * @throws Exception
	 */
	public static Element getElementCopy(byte[] bytes, String xpath)
			throws Exception {
		if (xpath == null) {
			return toElement(bytes);
		}
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return getElementCopy(inputStream, xpath);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * ��ȡָ��·���Ľڵ㿽��
	 * 
	 * @param inputStream
	 *            InputStream
	 * @param xpath
	 *            String
	 * @return Element
	 * @throws Exception
	 */
	public static Element getElementCopy(InputStream inputStream, String xpath)
			throws Exception {
		if (xpath == null) {
			return toElement(inputStream);
		}
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(inputStream);
		Element root = doc.getRootElement();
		Element element = (Element) root.selectSingleNode(xpath);
		return element;
	}
}
