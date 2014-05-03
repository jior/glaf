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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {

	public static void main(String[] args) throws Exception {

	}

	public static Element element(Element element) {
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}

	public static Element element(Element element, String name) {
		Element childElement = null;
		NodeList nodeList = element.getElementsByTagName(name);
		if (nodeList.getLength() > 0) {
			childElement = (Element) nodeList.item(0);
		}
		return childElement;
	}

	public static Iterator<Element> elementIterator(Element element) {
		return elements(element).iterator();
	}

	public static Iterator<Element> elementIterator(Element element,
			String tagName) {
		return elements(element, tagName).iterator();
	}

	public static List<Element> elements(Element element) {
		List<Element> elements = new java.util.ArrayList<Element>();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				elements.add((Element) node);
			}
		}
		return elements;
	}

	public static List<Element> elements(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		List<Element> elements = new java.util.ArrayList<Element>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getParentNode() == element) {
				elements.add((Element) child);
			}
		}
		return elements;
	}

	public static String getContentText(Element element) {
		StringBuilder text = new StringBuilder();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof CharacterData) {
				CharacterData characterData = (CharacterData) node;
				text.append(characterData.getData());
			}
		}
		return text.toString();
	}

	public static DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("could not create document builder", e);
		}
	}

	public static Document parseXmlInputStream(InputStream inputStream) {
		Document document = null;
		try {
			document = getDocumentBuilder().parse(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException("could not read xml stream", e);
		} catch (SAXException e) {
			throw new RuntimeException("could not parse xml document", e);
		}
		return document;
	}

	public static String toString(Element element) {
		if (element == null)
			return "null";

		Source source = new DOMSource(element);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		Result result = new StreamResult(printWriter);

		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new RuntimeException("couldn't write element '"
					+ element.getTagName() + "' to string", e);
		}

		printWriter.close();

		return stringWriter.toString();
	}
}