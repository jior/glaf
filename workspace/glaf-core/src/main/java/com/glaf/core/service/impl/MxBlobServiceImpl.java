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

package com.glaf.core.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.mapper.BlobItemMapper;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

@Service("blobService")
@Transactional(readOnly = true)
public class MxBlobServiceImpl implements IBlobService {

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected BlobItemMapper blobItemMapper;

	protected SqlSession sqlSession;

	public MxBlobServiceImpl() {

	}

	@Transactional
	public void copyBlob(String sourceId, String destId) {
		BlobItem source = this.getBlobWithBytesById(sourceId);
		BlobItem dest = this.getBlobById(destId);
		dest.setLastModified(System.currentTimeMillis());
		dest.setFilename(source.getFilename());
		dest.setData(source.getData());
		dest.setSize(source.getSize());
		dest.setContentType(source.getContentType());
		this.updateBlobFileInfo(dest);
	}

	public int count(BlobItemQuery query) {
		query.ensureInitialized();
		return blobItemMapper.getBlobItemCount(query);
	}

	@Transactional
	public void deleteBlobByFileId(String fileId) {
		blobItemMapper.deleteBlobItemsByFileId(fileId);
	}

	@Transactional
	public void deleteBlobByBusinessKey(String businessKey) {
		blobItemMapper.deleteBlobItemsByBusinessKey(businessKey);
	}

	@Transactional
	public void deleteById(String id) {
		blobItemMapper.deleteBlobItemById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		BlobItemQuery query = new BlobItemQuery();
		query.rowIds(rowIds);
		blobItemMapper.deleteBlobItems(query);
	}

	public BlobItem getBlobByFileId(String fileId) {
		BlobItem blob = null;
		List<BlobItem> list = blobItemMapper.getBlobItemsByFileId(fileId);
		if (list != null && !list.isEmpty()) {
			blob = list.get(0);
		}
		return blob;
	}

	/**
	 * 根据文件名称获取数据(不包含字节流)
	 * 
	 * @param filename
	 * @return
	 */
	public DataFile getBlobByFilename(String filename) {
		BlobItem blob = null;
		List<BlobItem> list = blobItemMapper.getBlobItemsByFilename(filename);
		if (list != null && !list.isEmpty()) {
			blob = list.get(0);
		}
		return blob;
	}

	public BlobItem getBlobById(String id) {
		BlobItem blob = blobItemMapper.getBlobItemById(id);
		return blob;
	}

	public List<DataFile> getBlobList(BlobItemQuery query) {
		List<BlobItem> list = list(query);
		List<DataFile> rows = new java.util.concurrent.CopyOnWriteArrayList<DataFile>();
		for (BlobItem b : list) {
			rows.add(b);
		}
		return rows;
	}

	public List<DataFile> getBlobList(String businessKey) {
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = list(query);
		List<DataFile> rows = new java.util.concurrent.CopyOnWriteArrayList<DataFile>();
		for (BlobItem b : list) {
			rows.add(b);
		}
		return rows;
	}

	public BlobItem getBlobWithBytesByFileId(String fileId) {
		BlobItem blob = this.getBlobByFileId(fileId);
		if (blob != null) {
			byte[] data = this.getBytesById(blob.getId());
			blob.setData(data);
		}
		return blob;
	}

	public BlobItem getBlobWithBytesById(String id) {
		BlobItem blob = blobItemMapper.getBlobItemById(id);
		byte[] data = this.getBytesById(id);
		blob.setData(data);
		return blob;
	}

	public byte[] getBytesByFileId(String fileId) {
		BlobItem blob = null;
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blob = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoByFileId_postgres", fileId);
		} else {
			blob = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoByFileId", fileId);
		}
		if (blob != null && blob.getData() != null) {
			return blob.getData();
		} else if (blob != null && blob.getPath() != null) {
			String rootDir = SystemProperties.getConfigRootPath();
			File file = new File(rootDir + blob.getPath());
			if (file.exists()) {
				return FileUtils.getBytes(file);
			}
		}
		return null;
	}

	public byte[] getBytesById(String id) {
		BlobItem blob = null;
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blob = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoById_postgres", id);
		} else {
			blob = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoById", id);
		}
		if (blob != null && blob.getData() != null) {
			return blob.getData();
		} else if (blob != null && blob.getPath() != null) {
			String rootDir = SystemProperties.getConfigRootPath();
			File file = new File(rootDir + blob.getPath());
			if (file.exists()) {
				return FileUtils.getBytes(file);
			}
		}
		return null;
	}

	public InputStream getInputStreamByFileId(String fileId) {
		byte[] bytes = this.getBytesByFileId(fileId);
		if (bytes != null) {
			return new BufferedInputStream(new ByteArrayInputStream(bytes));
		}
		return null;
	}

	public InputStream getInputStreamById(String id) {
		byte[] bytes = this.getBytesById(id);
		if (bytes != null) {
			return new BufferedInputStream(new ByteArrayInputStream(bytes));
		}
		return null;
	}

	public BlobItem getMaxBlob(BlobItemQuery query) {
		BlobItem blob = null;
		List<BlobItem> list = list(query);
		if (list != null && !list.isEmpty()) {
			blob = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					blob = model;
				}
			}
		}
		return blob;
	}

	public BlobItem getMaxBlob(String businessKey) {
		BlobItem blob = null;
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			blob = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > blob.getLastModified()) {
					blob = model;
				}
			}
		}
		return blob;
	}

	public BlobItem getMaxBlobWithBytes(String businessKey) {
		BlobItem blob = null;
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			blob = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					blob = model;
				}
			}
		}
		if (blob != null) {
			byte[] bytes = this.getBytesById(blob.getId());
			blob.setData(bytes);
		}
		return blob;
	}

	@Transactional
	private void insertBlobItem(BlobItem blobItem) {
		if (StringUtils.isEmpty(blobItem.getId())) {
			blobItem.setId(idGenerator.getNextId());
		}
		blobItem.setCreateDate(new Date());
		blobItem.setDeviceId("DATABASE");
		if (blobItem.getFileId() == null) {
			blobItem.setFileId(UUID32.getUUID());
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blobItemMapper.insertBlobItem_postgres(blobItem);
		} else {
			blobItemMapper.insertBlobItem(blobItem);
		}
	}

	@Transactional
	public void insertBlob(DataFile dataFile) {
		BlobItem model = new BlobItemEntity();
		model.setContentType(dataFile.getContentType());
		model.setCreateBy(dataFile.getCreateBy());
		model.setData(dataFile.getData());
		model.setDeleteFlag(dataFile.getDeleteFlag());
		model.setFileId(dataFile.getFileId());
		model.setFilename(dataFile.getFilename());
		model.setId(dataFile.getId());
		model.setLastModified(dataFile.getLastModified());
		model.setLocked(dataFile.getLocked());
		model.setName(dataFile.getName());
		model.setObjectId(dataFile.getObjectId());
		model.setObjectValue(dataFile.getObjectValue());
		model.setBusinessKey(dataFile.getBusinessKey());
		model.setServiceKey(dataFile.getServiceKey());
		model.setSize(dataFile.getSize());
		model.setStatus(dataFile.getStatus());
		model.setType(dataFile.getType());
		model.setPath(dataFile.getPath());
		this.insertBlobItem(model);
	}

	public List<BlobItem> list(BlobItemQuery query) {
		query.ensureInitialized();
		List<BlobItem> list = blobItemMapper.getBlobItems(query);
		return list;
	}

	@Transactional
	public void makeMark(String createBy, String serviceKey, String businessKey) {
		BlobItemQuery query = new BlobItemQuery();
		query.serviceKey(serviceKey);
		query.createBy(createBy);
		query.status(0);

		List<BlobItem> list = this.list(query);
		if (list != null && list.size() > 0) {
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = (BlobItem) iterator.next();
				if (StringUtils.isNotEmpty(businessKey)) {
					model.setBusinessKey(businessKey);
					model.setStatus(1);
					this.updateBlob(model);
				}
			}
		}
	}

	@Transactional
	public void saveAll(List<DataFile> dataList) {
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile blob = iterator.next();
			if (blob.getData() != null) {
				if (blob.getSize() <= 0) {
					blob.setSize(blob.getData().length);
				}
				this.insertBlob(blob);
			}
		}
	}

	@Transactional
	public void saveAll(Map<String, DataFile> dataMap) {
		BlobItemQuery query = new BlobItemQuery();
		List<String> names = new java.util.concurrent.CopyOnWriteArrayList<String>();
		for (String str : dataMap.keySet()) {
			names.add(str);
		}
		query.names(names);
		List<BlobItem> dataList = this.list(query);
		Map<String, Object> exists = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		if (dataList != null && dataList.size() > 0) {
			Iterator<BlobItem> iterator = dataList.iterator();
			while (iterator.hasNext()) {
				DataFile blob = iterator.next();
				exists.put(blob.getName(), blob);
			}
		}
		Iterator<DataFile> iterator = dataMap.values().iterator();
		while (iterator.hasNext()) {
			DataFile blob = iterator.next();
			if (blob.getData() != null) {
				if (exists.get(blob.getName()) != null) {
					DataFile model = (DataFile) exists.get(blob.getName());
					if (model.getSize() != blob.getData().length) {
						model.setFilename(blob.getFilename());
						model.setData(blob.getData());
						model.setSize(blob.getData().length);
						this.updateBlobFileInfo(model);
					}
				} else {
					if (blob.getSize() <= 0) {
						blob.setSize(blob.getData().length);
					}
					this.insertBlob(blob);
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setBlobItemMapper(BlobItemMapper blobItemMapper) {
		this.blobItemMapper = blobItemMapper;
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

	@Transactional
	public void updateBlob(BlobItem model) {
		blobItemMapper.updateBlobItem(model);
	}

	@Transactional
	public void updateBlob(DataFile dataFile) {
		if (dataFile instanceof BlobItem) {
			BlobItem blobItem = (BlobItem) dataFile;
			this.updateBlob(blobItem);
		} else {
			BlobItem model = new BlobItemEntity();
			model.setContentType(dataFile.getContentType());
			model.setCreateBy(dataFile.getCreateBy());
			model.setData(dataFile.getData());
			model.setDeleteFlag(dataFile.getDeleteFlag());
			model.setFileId(dataFile.getFileId());
			model.setFilename(dataFile.getFilename());
			model.setId(dataFile.getId());
			model.setLastModified(dataFile.getLastModified());
			model.setLocked(dataFile.getLocked());
			model.setName(dataFile.getName());
			model.setObjectId(dataFile.getObjectId());
			model.setObjectValue(dataFile.getObjectValue());
			model.setBusinessKey(dataFile.getBusinessKey());
			model.setServiceKey(dataFile.getServiceKey());
			model.setSize(dataFile.getSize());
			model.setStatus(dataFile.getStatus());
			model.setType(dataFile.getType());
			this.updateBlob(model);
		}
	}

	@Transactional
	public void updateBlobFileInfo(BlobItem model) {
		if (model.getData() == null) {
			throw new RuntimeException("bytes is null");
		}
		if (model.getSize() <= 0) {
			model.setSize(model.getData().length);
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			entityDAO.update("updateBlobItemFileInfo_postgres", model);
		} else {
			entityDAO.update("updateBlobItemFileInfo", model);
		}
	}

	@Transactional
	public void updateBlobFileInfo(DataFile dataFile) {
		if (dataFile instanceof BlobItem) {
			BlobItem blobItem = (BlobItem) dataFile;
			this.updateBlobFileInfo(blobItem);
		} else {
			BlobItem model = new BlobItemEntity();
			model.setContentType(dataFile.getContentType());
			model.setCreateBy(dataFile.getCreateBy());
			model.setData(dataFile.getData());
			model.setDeleteFlag(dataFile.getDeleteFlag());
			model.setFileId(dataFile.getFileId());
			model.setFilename(dataFile.getFilename());
			model.setId(dataFile.getId());
			model.setLastModified(dataFile.getLastModified());
			model.setLocked(dataFile.getLocked());
			model.setName(dataFile.getName());
			model.setObjectId(dataFile.getObjectId());
			model.setObjectValue(dataFile.getObjectValue());
			model.setBusinessKey(dataFile.getBusinessKey());
			model.setServiceKey(dataFile.getServiceKey());
			model.setSize(dataFile.getSize());
			model.setStatus(dataFile.getStatus());
			model.setType(dataFile.getType());
			this.updateBlob(model);
		}
	}

}