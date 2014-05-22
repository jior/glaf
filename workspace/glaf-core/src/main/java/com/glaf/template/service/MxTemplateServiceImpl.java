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

package com.glaf.template.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.template.mapper.TemplateMapper;
import com.glaf.template.query.TemplateQuery;
import com.glaf.template.util.TemplateJsonFactory;
import com.glaf.core.service.IBlobService;
import com.glaf.template.Template;
import com.glaf.template.TemplateReader;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.LogUtils;

@Service("templateService")
@Transactional(readOnly = true)
public class MxTemplateServiceImpl implements ITemplateService {
	private final static Log logger = LogFactory
			.getLog(MxTemplateServiceImpl.class);

	private final static String CONFIG_PATH = "/conf/templates/";

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TemplateMapper templateMapper;

	protected SqlSession sqlSession;

	protected IBlobService blobService;

	public MxTemplateServiceImpl() {

	}

	public int count(TemplateQuery query) {
		query.ensureInitialized();
		return templateMapper.getTemplateCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		templateMapper.deleteTemplateById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		TemplateQuery query = new TemplateQuery();
		query.rowIds(rowIds);
		templateMapper.deleteTemplates(query);
	}

	@Transactional
	public void deleteTemplate(String templateId) {
		if (StringUtils.isNotEmpty(templateId)) {
			templateMapper.deleteTemplateById(templateId);
			String cacheKey = "x_tpl_" + templateId;
			CacheFactory.remove(cacheKey);
		}
	}

	public Map<String, Template> getAllTemplate() {
		Map<String, Template> templateMap = new java.util.HashMap<String, Template>();
		TemplateQuery query = new TemplateQuery();
		query.setLocked(0);
		query.setDeleteFlag(0);
		List<Template> list = this.list(query);
		for (Template t : list) {
			templateMap.put(t.getTemplateId(), t);
		}
		return templateMap;
	}

	public Template getTemplate(String templateId) {
		if (StringUtils.isEmpty(templateId)) {
			return null;
		}
		String cacheKey = "x_tpl_" + templateId;
		if (CacheFactory.getString(cacheKey) != null) {
			logger.debug("load " + templateId + " from cache.");
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			Template tpl = TemplateJsonFactory.jsonToObject(jsonObject);
			return tpl;
		}

		Template template = templateMapper.getTemplateById(templateId);

		if (template != null) {
			DataFile blobData = blobService.getMaxBlobWithBytes(templateId);
			if (blobData != null && blobData.getData() != null) {
				template.setData(blobData.getData());
				logger.debug("blobData:" + blobData.getFilename());
				/**
				 * 当文件类型是文本并且小于512K是才添加到缓存
				 */
				if (template.getFileType() > 20 && template.getFileType() < 100) {
					template.setContent(new String(blobData.getData()));
					if (blobData.getSize() < 512000) {
						CacheFactory.put(cacheKey, template.toJsonObject()
								.toJSONString());
					}
				}
			}
		}
		return template;
	}

	public int getTemplateCountByQueryCriteria(TemplateQuery query) {
		return templateMapper.getTemplateCount(query);
	}

	public List<Template> getTemplates(TemplateQuery query) {
		return list(query);
	}

	public List<Template> getTemplatesByQueryCriteria(int start, int pageSize,
			TemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Template> rows = sqlSessionTemplate.selectList("getTemplates",
				query, rowBounds);
		return rows;
	}

	@Transactional
	public void installAllTemplates() {
		Map<String, Template> templateMap = this.getAllTemplate();
		TemplateReader reader = new TemplateReader();
		try {
			String configPath = SystemProperties.getConfigRootPath()
					+ CONFIG_PATH;
			File file = new File(configPath);
			if (!file.exists() || !file.isDirectory()) {
				return;
			}
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String name = filelist[i];
				if (!name.toLowerCase().endsWith(".xml")) {
					continue;
				}
				String filename = configPath + name;
				Resource resource = new FileSystemResource(filename);
				Map<String, Template> templates = reader.getTemplates(resource
						.getInputStream());
				Set<Entry<String, Template>> entrySet = templates.entrySet();
				for (Entry<String, Template> entry : entrySet) {
					Template template = entry.getValue();
					if (template.getData() == null
							|| template.getFileSize() < 0) {
						continue;
					}
					if (templateMap.get(template.getTemplateId()) != null) {
						Template model = templateMap.get(template
								.getTemplateId());
						if (template.getLastModified() > model
								.getLastModified()) {
							logger.debug("reload template config:" + filename);
							BlobItem blob = new BlobItemEntity();
							blob.setData(template.getData());
							blob.setFileId(template.getTemplateId());
							blob.setBusinessKey(template.getTemplateId());
							blob.setLastModified(template.getLastModified());
							blob.setFilename(FileUtils.getFileName(template
									.getDataFile()));
							blob.setStatus(1);
							model.setLastModified(template.getLastModified());
							model.setFileSize(template.getFileSize());
							model.setDataFile(template.getDataFile());
							model.setName(template.getName());
							model.setTitle(template.getTitle());
							model.setDescription(template.getDescription());
							model.setLocked(template.getLocked());

							templateMapper.updateTemplate(model);
							blobService.insertBlob(blob);
							String cacheKey = "x_tpl_" + model.getTemplateId();
							CacheFactory.remove(cacheKey);
						}
					} else {
						template.setCreateDate(new Date());
						template.setCreateBy("system");
						BlobItem blob = new BlobItemEntity();
						blob.setData(template.getData());
						blob.setFileId(template.getTemplateId());
						blob.setBusinessKey(template.getTemplateId());
						blob.setLastModified(template.getLastModified());
						blob.setFilename(FileUtils.getFileName(template
								.getDataFile()));
						blob.setStatus(1);
						if (template.getNodeId() == 0
								&& StringUtils.isNotEmpty(template
										.getModuleId())) {

						}
						if (StringUtils.isEmpty(template.getTemplateId())) {
							template.setTemplateId(idGenerator.getNextId());
						}
						templateMapper.insertTemplate(template);
						blobService.insertBlob(blob);
						String cacheKey = "x_tpl_" + template.getTemplateId();
						CacheFactory.remove(cacheKey);
					}
				}
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
			throw new RuntimeException(" load templates error:" + ex);
		}
	}

	public List<Template> list(TemplateQuery query) {
		query.ensureInitialized();
		List<Template> list = templateMapper.getTemplates(query);
		return list;
	}

	@Transactional
	public void saveTemplate(Template template) {
		Template model = this.getTemplate(template.getTemplateId());
		if (model != null) {
			model.setLastModified(template.getLastModified());
			model.setFileSize(template.getFileSize());
			model.setFileType(template.getFileType());
			model.setDataFile(template.getDataFile());
			model.setName(template.getName());
			model.setTitle(template.getTitle());
			model.setDescription(template.getDescription());
			model.setLocked(template.getLocked());
			model.setTemplateType(template.getTemplateType());
			templateMapper.updateTemplate(model);
			String cacheKey = "x_tpl_" + model.getTemplateId();
			CacheFactory.remove(cacheKey);
		} else {
			template.setCreateDate(new Date());
			if (template.getData() != null) {
				template.setFileSize(template.getData().length);
			}
			if (StringUtils.isEmpty(template.getTemplateId())) {
				template.setTemplateId(idGenerator.getNextId());
			}
			templateMapper.insertTemplate(template);
			String cacheKey = "x_tpl_" + template.getTemplateId();
			CacheFactory.remove(cacheKey);
		}
		if (template.getData() != null) {
			BlobItem blob = new BlobItemEntity();
			blob.setData(template.getData());
			blob.setFileId(template.getTemplateId());
			blob.setBusinessKey(template.getTemplateId());
			blob.setLastModified(template.getLastModified());
			if (template.getDataFile() != null) {
				blob.setFilename(FileUtils.getFileName(template.getDataFile()));
			} else {
				blob.setFilename(template.getName());
			}
			blob.setStatus(1);
			blobService.insertBlob(blob);
		}
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTemplateMapper(TemplateMapper templateMapper) {
		this.templateMapper = templateMapper;
	}

}