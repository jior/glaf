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
package com.glaf.form.core.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.IOUtils;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.FormEvent;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.def.FormProperty;
import com.glaf.form.core.graph.def.FormScript;
import com.glaf.form.core.graph.node.NodeTypes;
import com.glaf.form.core.model.EventType;
import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.model.NodeType;
import com.glaf.form.core.model.ObjectFactory;
import com.glaf.form.core.model.PropertyType;
import com.glaf.form.core.model.ScriptType;

public class FdlConverter {
	private static final Log logger = LogFactory.getLog(FdlConverter.class);

	public static byte[] toXml(FormDefinitionType fdt, String encoding) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		try {
			JAXBContext jc = JAXBContext
					.newInstance("com.glaf.form.core.model");
			Marshaller marshaller = jc.createMarshaller();
			ObjectFactory factory = new ObjectFactory();
			JAXBElement<?> jaxbElement = factory.createFormDefinition(fdt);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			marshaller.marshal(jaxbElement, bos);
			bos.flush();
			baos.flush();
			return baos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(baos);
			IOUtils.closeStream(bos);
		}
	}

	public static FormDefinitionType toFormDefinitionType(
			FormDefinition formDefinition) {
		FormDefinitionType fdt = new FormDefinitionType();
		fdt.setName(formDefinition.getName());
		fdt.setTitle(formDefinition.getTitle());
		fdt.setRows(formDefinition.getRows());
		fdt.setColumns(formDefinition.getColumns());

		Collection<FormProperty> props = formDefinition.getProperties()
				.values();

		for (FormProperty p : props) {
			PropertyType pt = new PropertyType();
			pt.setName(p.getName());
			pt.setValue(p.getValue());
			fdt.getProperty().add(pt);
		}

		List<FormScript> scripts = formDefinition.getScripts();
		for (FormScript script : scripts) {
			ScriptType st = new ScriptType();
			st.setExpression(script.getExpression());
			st.setType(script.getType());
			st.setRunat(script.getRunat());
			st.setLang(script.getLang());

			Collection<FormProperty> props2 = script.getProperties().values();

			for (FormProperty p : props2) {
				PropertyType pt = new PropertyType();
				pt.setName(p.getName());
				pt.setValue(p.getValue());
				st.getProperty().add(pt);
			}

			fdt.getScript().add(st);
		}

		Collection<FormEvent> events = formDefinition.getEvents().values();
		for (FormEvent event : events) {
			EventType et = new EventType();
			et.setType(event.getType());

			Collection<FormProperty> props3 = event.getProperties().values();

			for (FormProperty p : props3) {
				PropertyType pt = new PropertyType();
				pt.setName(p.getName());
				pt.setValue(p.getValue());
				et.getProperty().add(pt);
			}

			List<FormScript> scripts2 = event.getScripts();
			for (FormScript script : scripts2) {
				ScriptType st = new ScriptType();
				st.setExpression(script.getExpression());
				st.setType(script.getType());
				st.setRunat(script.getRunat());
				st.setLang(script.getLang());

				Collection<FormProperty> props4 = script.getProperties()
						.values();

				for (FormProperty p : props4) {
					PropertyType pt = new PropertyType();
					pt.setName(p.getName());
					pt.setValue(p.getValue());
					st.getProperty().add(pt);
				}

				event.addScript(script);
			}
			fdt.getEvent().add(et);
		}

		List<FormNode> nodes = formDefinition.getNodes();
		for (FormNode node : nodes) {
			NodeType nt = new NodeType();
			Collection<FormEvent> events2 = node.getEvents().values();
			for (FormEvent event : events2) {
				EventType et = new EventType();
				et.setType(event.getType());

				Collection<FormProperty> props6 = event.getProperties()
						.values();

				for (FormProperty p : props6) {
					PropertyType pt = new PropertyType();
					pt.setName(p.getName());
					pt.setValue(p.getValue());
					et.getProperty().add(pt);
				}

				List<FormScript> nscripts = event.getScripts();
				for (FormScript script : nscripts) {
					ScriptType st = new ScriptType();
					st.setExpression(script.getExpression());
					st.setType(script.getType());
					st.setRunat(script.getRunat());
					st.setLang(script.getLang());

					Collection<FormProperty> props7 = script.getProperties()
							.values();

					for (FormProperty p : props7) {
						PropertyType pt = new PropertyType();
						pt.setName(p.getName());
						pt.setValue(p.getValue());
						st.getProperty().add(pt);
					}

					et.getScript().add(st);
				}
				nt.getEvent().add(et);
			}

			Collection<FormProperty> props9 = node.getProperties().values();

			for (FormProperty p : props9) {
				PropertyType pt = new PropertyType();
				pt.setName(p.getName());
				pt.setValue(p.getValue());
				nt.getProperty().add(pt);
			}

			nt.setAccessLevel(node.getAccessLevel());

			nt.setBackground(node.getBackground());

			nt.setColIndex(node.getColIndex());

			nt.setColSpan(node.getColSpan());

			nt.setDataField(node.getDataField());

			nt.setDescription(node.getDescription());
			nt.setDisplay(node.getDisplay());

			nt.setDisplayType(node.getDisplayType());

			nt.setExpression(node.getExpression());
			nt.setFontName(node.getFontName());

			nt.setFontSize(node.getFontSize());

			nt.setFontStyle(node.getFontStyle());
			nt.setForeground(node.getForeground());
			nt.setFormula(node.getFormula());

			nt.setHeight(node.getHeight());

			nt.setInitialValue(node.getInitialValue());

			nt.setMaxLength(node.getMaxLength());

			nt.setMinLength(node.getMinLength());

			nt.setName(node.getName());
			nt.setNodeType(node.getNodeType());
			nt.setObjectId(node.getObjectId());
			nt.setObjectValue(node.getObjectValue());
			nt.setPattern(node.getPattern());
			nt.setQueryId(node.getQueryId());
			nt.setRegex(node.getRegex());
			nt.setRenderer(node.getRenderer());
			nt.setRowIndex(node.getRowIndex());
			nt.setRowSpan(node.getRowSpan());

			nt.setTextAlignment(node.getTextAlignment());
			nt.setTitle(node.getTitle());
			nt.setToolTip(node.getToolTip());
			nt.setVerticalAlignment(node.getVerticalAlignment());

			nt.setWidth(node.getWidth());

			nt.setX(node.getX());

			nt.setY(node.getY());

			fdt.getNode().add(nt);
		}
		return fdt;
	}

	public static FormDefinition toFormDefinition(FormDefinitionType fd) {
		FormDefinition formDefinition = new FormDefinition();
		formDefinition.setName(fd.getName());
		formDefinition.setTitle(fd.getTitle());
		formDefinition.setRows(fd.getRows());
		formDefinition.setColumns(fd.getColumns());
		formDefinition.setTemplateType("fdl");

		List<PropertyType> props = fd.getProperty();

		for (PropertyType pt : props) {
			FormProperty p = new FormProperty();
			p.setGraphElement(formDefinition);
			p.setName(pt.getName());
			p.setValue(pt.getValue());
			formDefinition.addProperty(p);
		}

		List<ScriptType> scripts = fd.getScript();
		for (ScriptType st : scripts) {
			FormScript script = new FormScript();
			script.setExpression(st.getExpression());
			script.setFormEvent(null);
			script.setType(st.getType());
			script.setRunat(st.getRunat());
			script.setGraphElement(formDefinition);

			List<PropertyType> props2 = st.getProperty();

			for (PropertyType pt : props2) {
				FormProperty p = new FormProperty();
				p.setGraphElement(formDefinition);
				p.setName(pt.getName());
				p.setValue(pt.getValue());
				script.addProperty(p);
			}
			formDefinition.addScript(script);
		}

		List<EventType> events = fd.getEvent();
		for (EventType et : events) {
			FormEvent event = new FormEvent();
			event.setType(et.getType());
			event.setGraphElement(formDefinition);

			List<PropertyType> props3 = et.getProperty();

			for (PropertyType pt : props3) {
				FormProperty p = new FormProperty();
				p.setGraphElement(formDefinition);
				p.setName(pt.getName());
				p.setValue(pt.getValue());
				event.addProperty(p);
			}

			List<ScriptType> scripts2 = et.getScript();
			for (ScriptType st : scripts2) {
				FormScript script = new FormScript();
				script.setExpression(st.getExpression());
				script.setFormEvent(event);
				script.setType(st.getType());
				script.setRunat(st.getRunat());
				script.setGraphElement(formDefinition);

				List<PropertyType> props4 = st.getProperty();

				for (PropertyType pt : props4) {
					FormProperty p = new FormProperty();
					p.setGraphElement(formDefinition);
					p.setName(pt.getName());
					p.setValue(pt.getValue());
					script.addProperty(p);
				}

				event.addScript(script);
			}
			formDefinition.addEvent(event);
		}

		List<NodeType> nodes = fd.getNode();
		for (NodeType nt : nodes) {
			FormNode node = new FormNode();
			String nodeTypeName = nt.getNodeType();
			if (StringUtils.isNotEmpty(nodeTypeName)) {
				Class<?> nodeType = NodeTypes.getNodeType(nodeTypeName);
				if (nodeType != null) {
					try {
						node = (FormNode) nodeType.newInstance();
					} catch (Exception ex) {
						logger.error(
								"couldn't instantiate node '" + nodeTypeName
										+ "', of type '" + nodeType.getName()
										+ "'", ex);
					}
				}
			}
			if (nt.getAccessLevel() != null) {
				node.setAccessLevel(nt.getAccessLevel());
			}
			node.setBackground(nt.getBackground());
			node.setBold(nt.isBold());
			if (nt.getColIndex() != null) {
				node.setColIndex(nt.getColIndex());
			}
			node.setColSpan(nt.getColSpan());
			node.setDataCode("");
			node.setDataField(nt.getDataField());

			node.setColumnName("");

			node.setDescription(nt.getDescription());
			node.setDisplay(nt.getDisplay());
			if (nt.getDisplayType() != null) {
				node.setDisplayType(nt.getDisplayType());
			}
			node.setExpression(nt.getExpression());
			node.setFontName(nt.getFontName());

			if (nt.getFontSize() != null) {
				node.setFontSize(nt.getFontSize());
			}

			node.setFontStyle(nt.getFontStyle());
			node.setForeground(nt.getForeground());
			node.setFormula(nt.getFormula());
			if (nt.getHeight() != null) {
				node.setHeight(nt.getHeight());
			}
			node.setInitialValue(nt.getInitialValue());
			node.setItalic(nt.isItalic());
			if (nt.getMaxLength() != null) {
				node.setMaxLength(nt.getMaxLength());
			}
			if (nt.getMinLength() != null) {
				node.setMinLength(nt.getMinLength());
			}
			node.setName(nt.getName());
			node.setNodeType(nt.getNodeType());
			node.setObjectId(nt.getObjectId());
			node.setObjectValue(nt.getObjectValue());
			node.setPattern(nt.getPattern());
			node.setQueryId(nt.getQueryId());
			node.setRegex(nt.getRegex());
			node.setRenderer(nt.getRenderer());
			node.setRowIndex(nt.getRowIndex());
			node.setRowSpan(nt.getRowSpan());
			node.setSize(-1);
			node.setStyleClass("");
			node.setTextAlignment(nt.getTextAlignment());
			node.setTitle(nt.getTitle());
			node.setToolTip(nt.getToolTip());
			node.setVerticalAlignment(nt.getVerticalAlignment());
			if (nt.getWidth() != null) {
				node.setWidth(nt.getWidth());
			}
			if (nt.getX() != null) {
				node.setX(nt.getX());
			}
			if (nt.getY() != null) {
				node.setY(nt.getY());
			}

			List<EventType> events2 = nt.getEvent();
			for (EventType et : events2) {
				FormEvent event = new FormEvent();
				event.setType(et.getType());
				event.setGraphElement(node);

				List<PropertyType> props3 = et.getProperty();

				for (PropertyType pt : props3) {
					FormProperty p = new FormProperty();
					p.setGraphElement(node);
					p.setName(pt.getName());
					p.setValue(pt.getValue());
					event.addProperty(p);
				}

				List<ScriptType> scripts2 = et.getScript();
				for (ScriptType st : scripts2) {
					FormScript script = new FormScript();
					script.setExpression(st.getExpression());
					script.setFormEvent(event);
					script.setType(st.getType());
					script.setRunat(st.getRunat());
					script.setGraphElement(node);

					List<PropertyType> props4 = st.getProperty();

					for (PropertyType pt : props4) {
						FormProperty p = new FormProperty();
						p.setGraphElement(node);
						p.setName(pt.getName());
						p.setValue(pt.getValue());
						script.addProperty(p);
					}

					event.addScript(script);
				}
				node.addEvent(event);
			}

			List<PropertyType> nprops = nt.getProperty();

			for (PropertyType pt : nprops) {
				FormProperty p = new FormProperty();
				p.setGraphElement(node);
				p.setName(pt.getName());
				p.setValue(pt.getValue());
				node.addProperty(p);
			}

			formDefinition.addNode(node);
		}
		return formDefinition;
	}

}