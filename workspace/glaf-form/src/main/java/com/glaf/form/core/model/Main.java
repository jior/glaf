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
package com.glaf.form.core.model;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {

	public static void main(String arg[]) throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.glaf.form.core.model");
		Unmarshaller u = jc.createUnmarshaller();
		// System.out.println(jc.createBinder().getXMLNode());
		Marshaller marshaller = jc.createMarshaller();

		ObjectFactory of = new ObjectFactory();
		// 表单全局定义
		FormDefinitionType fdt = of.createFormDefinitionType();
		fdt.setName("GoodsApply");
		fdt.setTitle("物品申购单");
		fdt.setX(0);
		fdt.setY(0);
		fdt.setWidth(775);
		fdt.setHeight(427);
		fdt.setRows(12);
		fdt.setColumns(31);

		// 添加全局事件
		EventType et1 = of.createEventType();
		et1.setType("javascript");
		// 给添加事件脚本
		ScriptType ct1 = of.createScriptType();
		ct1.setType("onload");
		ct1.setExpression("javascript:sumXY('num5','price5','money5');");
		et1.getScript().add(ct1);
		fdt.getEvent().add(et1);

		ScriptType ct2 = of.createScriptType();
		// ct2.setType("onload");
		// ct2.setExpression("javascript:sumXY('num5','price5','money5');");
		ct2.setLang("javascript");
		ct2.setRunat("cilent");
		ct2.setExpression("function firm(){if(confirm(\"你确认要离开？\")){   location.href=\"http:\\\\www.baidu.com\";  }}");
		fdt.getScript().add(ct2);

		// 控件
		NodeType nt = of.createNodeType();
		nt.setNodeType("label");
		nt.setTitle("物品申购单（办公用品、礼品、耗材）");
		nt.setForeground("#3366FF");

		// 给控件添加事件，事件可多个
		EventType et = of.createEventType();
		et.setType("javascript");
		nt.getEvent().add(et);
		// 给添加事件脚本
		ScriptType ct = of.createScriptType();
		ct.setType("onblur");
		ct.setExpression("javascript:sumXY('num5','price5','money5');");
		et.getScript().add(ct);

		// 添加properties

		PropertyType pt = of.createPropertyType();
		pt.setName("num5");
		pt.setValue("$N{N:\"num5\",width:145,onblur:\"javascript:sumXY('num5','price5','money5');\",T:\"数量\",maxLength:4}");

		nt.getProperty().add(pt);

		fdt.getNode().add(nt);

		// 控件
		NodeType nt2 = of.createNodeType();
		nt2.setName("subject");
		nt2.setNodeType("textfield");
		nt2.setTitle("主题");
		nt2.setForeground("#3366FF");
		fdt.getNode().add(nt2);

		// 生成文件
		JAXBElement<?> jaxbElement = of.createFormDefinition(fdt);
		File f = new File("./GoodsApply.fdl.xml");
		if (!f.exists()) {
			if (!f.createNewFile()) {
				throw new RuntimeException();
			}
		}
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(jaxbElement, new FileOutputStream(f));
		// System.out.println("aaaaaa:" + marshaller.JAXB_FORMATTED_OUTPUT);

		// 解析xml文件

		JAXBElement<?> customerE = (JAXBElement<?>) u.unmarshal(new File(
				"./GoodsApply.fdl.xml"));
		FormDefinitionType bo = (FormDefinitionType) customerE.getValue();
		System.out.println("node size:" + bo.getNode().size());
		// System.out.println(jc.createBinder(FormDefinitionType.class));

	}

}