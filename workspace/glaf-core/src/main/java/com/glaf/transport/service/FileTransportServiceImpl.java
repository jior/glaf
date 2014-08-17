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

package com.glaf.transport.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.dao.*;
import com.glaf.transport.mapper.*;
import com.glaf.transport.domain.*;
import com.glaf.transport.query.*;
import com.glaf.transport.util.FileTransportUtils;

@Service("fileTransportService")
@Transactional(readOnly = true)
public class FileTransportServiceImpl implements FileTransportService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FileTransportMapper fileTransportMapper;

	public FileTransportServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			fileTransportMapper.deleteFileTransportById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				fileTransportMapper.deleteFileTransportById(id);
			}
		}
	}

	public int count(FileTransportQuery query) {
		return fileTransportMapper.getFileTransportCount(query);
	}

	public List<FileTransport> list(FileTransportQuery query) {
		List<FileTransport> list = fileTransportMapper.getFileTransports(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFileTransportCountByQueryCriteria(FileTransportQuery query) {
		return fileTransportMapper.getFileTransportCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FileTransport> getFileTransportsByQueryCriteria(int start,
			int pageSize, FileTransportQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FileTransport> rows = sqlSessionTemplate.selectList(
				"getFileTransports", query, rowBounds);
		return rows;
	}

	public FileTransport getFileTransport(Long id) {
		if (id == null) {
			return null;
		}
		FileTransport fileTransport = fileTransportMapper
				.getFileTransportById(id);
		return fileTransport;
	}
	
	/**
	 * 根据编码获取一条记录
	 * 
	 * @return
	 */
	public FileTransport getFileTransportByCode(String code){
		FileTransportQuery query = new FileTransportQuery();
		
		List<FileTransport> list = fileTransportMapper.getFileTransports(query);
		if(list != null && !list.isEmpty()){
			for(FileTransport t:list){
				if(StringUtils.equals(t.getActive(), "1")){
					return t;
				}
			}
		}
		return null;
	}

	@Transactional
	public void save(FileTransport fileTransport) {
		String password = fileTransport.getPassword();
		if (fileTransport.getId() == null) {
			if (!"88888888".equals(password)) {
				String key = FileTransportUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				fileTransport.setKey(key);
				fileTransport.setPassword(pass);
			}
			fileTransport.setId(idGenerator.nextId("SYS_FILE_TRANSPORT"));
			fileTransportMapper.insertFileTransport(fileTransport);
		} else {
			FileTransport model = this.getFileTransport(fileTransport.getId());
			model.setId(fileTransport.getId());
			model.setTitle(fileTransport.getTitle());
			model.setCode(fileTransport.getCode());
			model.setType(fileTransport.getType());
			model.setProviderClass(fileTransport.getProviderClass());
			model.setHost(fileTransport.getHost());
			model.setPort(fileTransport.getPort());
			model.setPath(fileTransport.getPath());
			model.setActive(fileTransport.getActive());
			model.setNodeId(fileTransport.getNodeId());
			if (!"88888888".equals(password)) {
				String key = FileTransportUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				model.setKey(key);
				model.setPassword(pass);
			}
			fileTransportMapper.updateFileTransport(model);
		}
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
	public void setFileTransportMapper(FileTransportMapper fileTransportMapper) {
		this.fileTransportMapper = fileTransportMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
