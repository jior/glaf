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

package com.glaf.core.xml;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.*;

import com.glaf.core.base.DatasetModel;
import com.glaf.core.base.FieldController;
import com.glaf.core.config.Environment;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.ReflectUtils;
import com.glaf.core.util.StringTools;

public class XmlBuilder {
	protected static final Log LOG = LogFactory.getLog(XmlBuilder.class);

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String systemName = "default";

		XmlBuilder builder = new XmlBuilder();
		InputStream in = new FileInputStream("./template/user.template.xml");
		String filename = "user.xml";
		byte[] bytes = FileUtils.getBytes(in);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("now", new Date());
		Document doc = builder.process(systemName, new ByteArrayInputStream(
				bytes), dataMap);
		FileUtils.save(filename, com.glaf.core.util.Dom4jUtils
				.getBytesFromPrettyDocument(doc, "GBK"));
		// net.sf.json.xml.XMLSerializer xmlSerializer = new
		// net.sf.json.xml.XMLSerializer();
		// net.sf.json.JSON json = xmlSerializer.read(doc.asXML());
		// System.out.println(json.toString(1));

		long time = (System.currentTimeMillis() - start) / 1000;
		System.out.println("times:" + time + " seconds");
	}

	protected EntityService entityService;

	protected QueryHelper queryHelper = new QueryHelper();

	public EntityService getEntityService() {
		return entityService;
	}

	public Document process(String systemName, InputStream inputStream,
			Map<String, Object> params) {
		ResultThreadLocal.clear();
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		try {
			doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			processElement(systemName, root, params);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			ResultThreadLocal.clear();
		}
		return doc;
	}

	public void processElement(String systemName, Element root,
			Map<String, Object> params) {
		/**
		 * 获取节点的数据集定义
		 */
		Map<String, DatasetModel> dataSetMap = new HashMap<String, DatasetModel>();
		List<?> elements = root.elements("DataSet");
		Iterator<?> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			String id = element.attributeValue("id");
			String sql = element.attributeValue("sql");
			String queryId = element.attributeValue("queryId");
			String title = element.attributeValue("title");
			String single = element.attributeValue("single");
			String splits = element.attributeValue("splits");
			String foreachPerRow = element.attributeValue("foreachPerRow");
			if (sql == null) {
				sql = element.elementText("sql");
			}

			DatasetModel dsm = new DatasetModel();
			dsm.setId(id);
			dsm.setSql(sql);
			dsm.setQueryId(queryId);
			dsm.setTitle(title);
			if (StringUtils.equalsIgnoreCase(single, "true")) {
				dsm.setSingle(true);
			}

			if (StringUtils.equalsIgnoreCase(foreachPerRow, "true")) {
				dsm.setForeachPerRow(true);
			}

			if (StringUtils.isNotEmpty(splits)) {
				dsm.setSplitList(StringTools.split(splits));
			}

			List<?> attrs = element.attributes();
			if (attrs != null && !attrs.isEmpty()) {
				Iterator<?> iter = attrs.iterator();
				while (iter.hasNext()) {
					Attribute attr = (Attribute) iter.next();
					dsm.addAttribute(attr.getName(), attr.getStringValue());
				}
			}

			List<?> providers = element.elements("FieldConverter");
			if (providers != null && !providers.isEmpty()) {
				Iterator<?> iter = providers.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					String fromName = elem.attributeValue("fromName");
					String toName = elem.attributeValue("toName");
					String provider = elem.attributeValue("provider");
					FieldController c = new FieldController();
					c.setFromName(fromName);
					c.setToName(toName);
					c.setProvider(provider);
					dsm.addController(c);
				}
			}
			dataSetMap.put(dsm.getId(), dsm);
		}

		elements = root.elements();
		iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			if (StringUtils.equals(element.getName(), "DataSet")) {
				continue;
			}
			this.processNode(element, dataSetMap, systemName, params);
		}

		elements = root.elements("DataSet");
		iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			root.remove(element);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void processForEachNode(Element element,
			Map<String, DatasetModel> dataSetMap,
			Map<String, Object> myDataMap, String systemName,
			Map<String, Object> params) {
		//LOG.debug("---------------------processForEachNode-----------------------");
		Element parent = element.getParent();
		String dsId = element.attributeValue("DataSetId");
		if (StringUtils.isNotEmpty(dsId)) {
			if (myDataMap != null && !myDataMap.isEmpty()) {
				params.putAll(myDataMap);
			}
			DatasetModel dsm = dataSetMap.get(dsId);
			String sql = dsm.getSql();
			String queryId = dsm.getQueryId();
			List<Map<String, Object>> rows = null;
			if (StringUtils.isNotEmpty(queryId)) {
				Environment.setCurrentSystemName(systemName);
				List<Object> list = entityService.getList(queryId, params);
				if (list != null && !list.isEmpty()) {
					rows = new ArrayList<Map<String, Object>>();
					for (Object object : list) {
						if (object instanceof Map) {
							Map dataMap = (Map) object;
							rows.add(dataMap);
						} else {
							try {
								Map dataMap = BeanUtils.describe(object);
								rows.add(dataMap);
							} catch (Exception e) {
							}
						}
					}
				}
			} else if (StringUtils.isNotEmpty(sql)) {
				LOG.debug("sql:" + sql);
				LOG.debug("params:" + params);
				rows = queryHelper.getResultList(systemName, sql, params);
				// ITablePageService tablePageService =
				// ContextFactory.getBean("tablePageService");
				// rows = queryHelper.getResultList(systemName, sql, params);
				// Environment.setCurrentSystemName(systemName);
				// rows = tablePageService.getListData(sql, params);
				LOG.debug("#1 rows:" + rows.size());
			}
			if (rows != null && !rows.isEmpty()) {
				int sortNo = 0;
				List<?> elements = element.elements();
				if (elements != null && elements.size() == 1) {
					Element elem = (Element) elements.get(0);
					LOG.debug("name:" + elem.getName());
					for (Map<String, Object> dataMap : rows) {
						sortNo = sortNo + 1;
						dataMap.put("sortNo", sortNo);
						Element e = elem.createCopy();
						if (dsm.getControllers() != null
								&& !dsm.getControllers().isEmpty()) {
							List<FieldController> controllers = dsm
									.getControllers();
							for (FieldController c : controllers) {
								Class<?> x = ClassUtils.classForName(c
										.getProvider());
								FieldConverter fp = (FieldConverter) ReflectUtils
										.newInstance(x);
								fp.convert(c.getFromName(), c.getToName(),
										dataMap);
							}
						}

						if (e.isTextOnly()) {
							String value = e.getStringValue();
							if (StringUtils.contains(value, "#{")
									&& StringUtils.contains(value, "}")) {
								String text = QueryUtils.replaceBlankParas(
										value, dataMap);
								e.setText(text);
							}
						}

						List<?> attrs = e.attributes();
						Iterator<?> iter = attrs.iterator();
						while (iter.hasNext()) {
							Attribute attr = (Attribute) iter.next();
							String value = attr.getValue();
							if (StringUtils.contains(value, "#{")
									&& StringUtils.contains(value, "}")) {
								String text = QueryUtils.replaceBlankParas(
										value, dataMap);
								attr.setValue(text);
							}
						}

						e.setParent(null);
						parent.add(e);
					}
				}
			}
		}

		parent.remove(element);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void processNode(Element element,
			Map<String, DatasetModel> dataSetMap, String systemName,
			Map<String, Object> params) {
		//LOG.debug("---------------------processNode-----------------------");
		String dsId = element.attributeValue("DataSetId");

		if (StringUtils.isNotEmpty(dsId) && dataSetMap.get(dsId) != null) {
			//LOG.debug(element.getName() + " ds->" + dsId);
			element.remove(element.attribute("DataSetId"));
			Element parent = element.getParent();
			DatasetModel dsm = dataSetMap.get(dsId);
			String sql = dsm.getSql();
			String queryId = dsm.getQueryId();
			boolean single = dsm.isSingle();
			boolean foreachPerRow = dsm.isForeachPerRow();
			List<Map<String, Object>> rows = null;
			List<String> splits = dsm.getSplitList();
			if (foreachPerRow) {
				LOG.debug("sql:" + sql);
				LOG.debug("params:" + params);
				rows = queryHelper.getResultList(systemName, sql, params);
				// ITablePageService tablePageService =
				// ContextFactory.getBean("tablePageService");
				// rows = queryHelper.getResultList(systemName, sql, params);
				// Environment.setCurrentSystemName(systemName);
				// rows = tablePageService.getListData(sql, params);
				LOG.debug("#2 rows:" + rows.size());
			} else {
				rows = ResultThreadLocal.getResultList(dsId);
				if (rows == null) {
					if (StringUtils.isNotEmpty(queryId)) {
						Environment.setCurrentSystemName(systemName);
						List<Object> list = entityService.getList(queryId,
								params);
						if (list != null && !list.isEmpty()) {
							rows = new ArrayList<Map<String, Object>>();
							for (Object object : list) {
								if (object instanceof Map) {
									Map dataMap = (Map) object;
									rows.add(dataMap);
								} else {
									try {
										Map dataMap = BeanUtils
												.describe(object);
										rows.add(dataMap);
									} catch (Exception e) {
									}
								}
							}
						}
					} else if (StringUtils.isNotEmpty(sql)) {
						LOG.debug("sql:" + sql);
						LOG.debug("params:" + params);
						rows = queryHelper.getResultList(systemName, sql,
								params);
						// ITablePageService tablePageService =
						// ContextFactory.getBean("tablePageService");
						// rows = queryHelper.getResultList(systemName, sql,
						// params);
						// Environment.setCurrentSystemName(systemName);
						// rows = tablePageService.getListData(sql, params);
						LOG.debug("#3 rows:" + rows.size());
					}
					ResultThreadLocal.setResultList(dsId, rows);
				}
			}
			if (rows != null && !rows.isEmpty()) {
				if (single) {
					Map<String, Object> dataMap = rows.get(0);
					if (dsm.getControllers() != null
							&& !dsm.getControllers().isEmpty()) {
						List<FieldController> controllers = dsm
								.getControllers();
						for (FieldController c : controllers) {
							Class<?> x = ClassUtils.classForName(c
									.getProvider());
							FieldConverter fp = (FieldConverter) ReflectUtils
									.newInstance(x);
							fp.convert(c.getFromName(), c.getToName(), dataMap);
						}
					}
					this.processTextNode(element, dataSetMap, dataMap,
							systemName, params);
				} else {
					int sortNo = 0;
					for (Map<String, Object> dataMap : rows) {
						if (splits != null && !splits.isEmpty()) {
							int splitCnt = 0;
							Iterator<String> it = splits.iterator();
							while (it.hasNext()) {
								String str = it.next();
								if (str != null
										&& ObjectUtils.equals(dataMap.get(str),
												params.get(str))) {
									splitCnt = splitCnt + 1;
								} else if (str != null
										&& ObjectUtils.equals(
												dataMap.get(str.toLowerCase()),
												params.get(str.toLowerCase()))) {
									splitCnt = splitCnt + 1;
								}
							}
							/**
							 * 如果不符合切分条件
							 */
							if (splitCnt != splits.size()) {
								// LOG.debug("dsId="+dsId);
								// LOG.debug("params:"+params );
								// LOG.debug(dataMap + " 不符合切分条件");
								continue;
							}
						}
						sortNo = sortNo + 1;
						dataMap.put("sortNo", sortNo);
						if (dsm.getControllers() != null
								&& !dsm.getControllers().isEmpty()) {
							List<FieldController> controllers = dsm
									.getControllers();
							for (FieldController c : controllers) {
								Class<?> x = ClassUtils.classForName(c
										.getProvider());
								FieldConverter fp = (FieldConverter) ReflectUtils
										.newInstance(x);
								fp.convert(c.getFromName(), c.getToName(),
										dataMap);
							}
						}

						Element e = element.createCopy();
						this.processTextNode(e, dataSetMap, dataMap,
								systemName, params);
						parent.add(e);

					}
					parent.remove(element);
				}
			} else {
				parent.remove(element);
			}
		} else {
			this.processTextNode(element, dataSetMap, null, systemName, params);
		}
	}

	protected void processTextNode(Element element,
			Map<String, DatasetModel> dataSetMap, Map<String, Object> dataMap,
			String systemName, Map<String, Object> params) {
		//LOG.debug("---------------------processTextNode-----------------------");
		if (StringUtils.equals(element.getName(), "foreach")) {
			this.processForEachNode(element, dataSetMap, dataMap, systemName,
					params);
			return;
		}
		String dsId = element.attributeValue("DataSetId");
		if (StringUtils.isNotEmpty(dsId)) {
			if (dataMap != null && !dataMap.isEmpty()) {
				params.putAll(dataMap);
			}
			this.processNode(element, dataSetMap, systemName, params);
			return;
		}

		if (element.isTextOnly()) {
			String value = element.getStringValue();
			if (StringUtils.contains(value, "#{")
					&& StringUtils.contains(value, "}")) {
				String text = QueryUtils.replaceBlankParas(value, dataMap);
				element.setText(text);
			}
		}

		List<?> attrs = element.attributes();
		Iterator<?> iter = attrs.iterator();
		while (iter.hasNext()) {
			Attribute attr = (Attribute) iter.next();
			String value = attr.getValue();
			if (StringUtils.contains(value, "#{")
					&& StringUtils.contains(value, "}")) {
				String text = QueryUtils.replaceBlankParas(value, dataMap);
				attr.setValue(text);
			}
		}

		List<?> elements = element.elements();
		Iterator<?> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element elem = (Element) iterator.next();
			if (StringUtils.equals(elem.getName(), "foreach")) {
				this.processForEachNode(elem, dataSetMap, dataMap, systemName,
						params);
			} else {
				String dsId2 = elem.attributeValue("DataSetId");
				if (StringUtils.isNotEmpty(dsId2)) {
					if (dataMap != null && !dataMap.isEmpty()) {
						params.putAll(dataMap);
					}
					this.processNode(elem, dataSetMap, systemName, params);
				} else {
					this.processTextNode(elem, dataSetMap, dataMap, systemName,
							params);
				}
			}
		}
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

}