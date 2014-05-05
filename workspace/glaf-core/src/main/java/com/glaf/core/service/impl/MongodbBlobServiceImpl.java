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
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.mapper.BlobItemMapper;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.UUID32;

@Transactional(readOnly = true)
public class MongodbBlobServiceImpl implements IBlobService {

	protected final static Log logger = LogFactory
			.getLog(MongodbBlobServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected BlobItemMapper blobItemMapper;

	protected SqlSession sqlSession;

	public MongodbBlobServiceImpl() {

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
		return blobItemMapper.getBlobItemCount(query);
	}

	@Transactional
	public void deleteBlobByBusinessKey(String businessKey) {
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = blobItemMapper.getBlobItems(query);
		if (list != null && !list.isEmpty()) {
			try {
				for (BlobItem blobItem : list) {
					String path = blobItem.getPath();
					String filename = SystemProperties.getFileStorageRootPath()
							+ path;
					FileUtils.deleteFile(filename);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
			if (SystemConfig.getBoolean("fs_storage_mongodb")
					&& ContextFactory.hasBean("mongo")) {
				try {
					String dbname = "gridfs_"
							+ Environment.getCurrentSystemName();
					Mongo mongo = ContextFactory.getBean("mongo");
					DB db = mongo.getDB(dbname);
					for (BlobItem blobItem : list) {
						GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
						gridFS.remove(blobItem.getFileId());
						logger.debug("remove file from mongodb.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}
		blobItemMapper.deleteBlobItemsByBusinessKey(businessKey);
	}

	@Transactional
	public void deleteBlobByFileId(String fileId) {
		BlobItem blobItem = this.getBlobByFileId(fileId);
		String path = blobItem.getPath();
		try {
			String filename = SystemProperties.getFileStorageRootPath() + path;
			FileUtils.deleteFile(filename);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		if (SystemConfig.getBoolean("fs_storage_mongodb")
				&& ContextFactory.hasBean("mongo")) {
			try {
				String dbname = "gridfs_" + Environment.getCurrentSystemName();
				Mongo mongo = ContextFactory.getBean("mongo");
				DB db = mongo.getDB(dbname);
				GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
				gridFS.remove(blobItem.getFileId());
				logger.debug("remove file from mongodb.");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		blobItemMapper.deleteBlobItemsByFileId(fileId);
	}

	@Transactional
	public void deleteById(String id) {
		BlobItem blobItem = blobItemMapper.getBlobItemById(id);
		String path = blobItem.getPath();
		try {
			String filename = SystemProperties.getFileStorageRootPath() + path;
			FileUtils.deleteFile(filename);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (SystemConfig.getBoolean("fs_storage_mongodb")
				&& ContextFactory.hasBean("mongo")) {
			try {
				String dbname = "gridfs_" + Environment.getCurrentSystemName();
				Mongo mongo = ContextFactory.getBean("mongo");
				DB db = mongo.getDB(dbname);
				GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
				gridFS.remove(blobItem.getFileId());
				logger.debug("remove file from mongodb.");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		blobItemMapper.deleteBlobItemById(id);
	}

	public BlobItem getBlobByFileId(String fileId) {
		BlobItem blobItem = null;
		List<BlobItem> list = blobItemMapper.getBlobItemsByFileId(fileId);
		if (list != null && !list.isEmpty()) {
			blobItem = list.get(0);
		}
		return blobItem;
	}

	/**
	 * 根据文件名称获取数据(不包含字节流)
	 * 
	 * @param filename
	 * @return
	 */
	public DataFile getBlobByFilename(String filename) {
		BlobItem blobItem = null;
		List<BlobItem> list = blobItemMapper.getBlobItemsByFilename(filename);
		if (list != null && !list.isEmpty()) {
			blobItem = list.get(0);
		}
		return blobItem;
	}

	public BlobItem getBlobById(String id) {
		BlobItem blobItem = blobItemMapper.getBlobItemById(id);
		return blobItem;
	}

	public List<DataFile> getBlobList(BlobItemQuery query) {
		List<BlobItem> list = list(query);
		List<DataFile> rows = new java.util.ArrayList<DataFile>();
		for (BlobItem blobItem : list) {
			rows.add(blobItem);
		}
		return rows;
	}

	public List<DataFile> getBlobList(String businessKey) {
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = list(query);
		List<DataFile> rows = new java.util.ArrayList<DataFile>();
		for (BlobItem blobItem : list) {
			rows.add(blobItem);
		}
		return rows;
	}

	public BlobItem getBlobWithBytesByFileId(String fileId) {
		BlobItem blobItem = this.getBlobByFileId(fileId);
		if (blobItem != null) {
			byte[] data = this.getBytesById(blobItem.getId());
			blobItem.setData(data);
		}
		return blobItem;
	}

	public BlobItem getBlobWithBytesById(String id) {
		BlobItem blobItem = blobItemMapper.getBlobItemById(id);
		byte[] data = this.getBytesById(id);
		blobItem.setData(data);
		return blobItem;
	}

	public byte[] getBytesByFileId(String fileId) {
		BlobItem blobItem = this.getBlobByFileId(fileId);
		if (blobItem != null) {
			if (SystemConfig.getBoolean("fs_storage_mongodb")
					&& ContextFactory.hasBean("mongo")) {
				InputStream inputStream = null;
				try {
					String dbname = "gridfs_"
							+ Environment.getCurrentSystemName();
					Mongo mongo = ContextFactory.getBean("mongo");
					DB db = mongo.getDB(dbname);
					GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
					GridFSDBFile file = gridFS.findOne(blobItem.getFileId());
					inputStream = file.getInputStream();
					logger.debug("get file from mongodb.");
					return FileUtils.getBytes(inputStream);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				} finally {
					IOUtils.closeStream(inputStream);
				}
			}
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blobItem = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoByFileId_postgres", fileId);
		} else {
			blobItem = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoByFileId", fileId);
		}
		if (blobItem != null && blobItem.getData() != null) {
			return blobItem.getData();
		} else if (blobItem != null && blobItem.getPath() != null) {
			String rootDir = SystemProperties.getFileStorageRootPath();
			File file = new File(rootDir + blobItem.getPath());
			if (file.exists() && file.isFile()) {
				return FileUtils.getBytes(file);
			}
		}
		return null;
	}

	public byte[] getBytesById(String id) {
		BlobItem blobItem = this.getBlobById(id);
		if (blobItem != null) {
			if (SystemConfig.getBoolean("fs_storage_mongodb")
					&& ContextFactory.hasBean("mongo")) {
				InputStream inputStream = null;
				try {
					String dbname = "gridfs_"
							+ Environment.getCurrentSystemName();
					Mongo mongo = ContextFactory.getBean("mongo");
					DB db = mongo.getDB(dbname);
					GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
					GridFSDBFile file = gridFS.findOne(blobItem.getFileId());
					inputStream = file.getInputStream();
					logger.debug("get file from mongodb.");
					return FileUtils.getBytes(inputStream);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				} finally {
					IOUtils.closeStream(inputStream);
				}
			}
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blobItem = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoById_postgres", id);
		} else {
			blobItem = (BlobItem) entityDAO.getSingleObject(
					"getBlobItemFileInfoById", id);
		}
		if (blobItem != null && blobItem.getData() != null) {
			return blobItem.getData();
		} else if (blobItem != null && blobItem.getPath() != null) {
			String rootDir = SystemProperties.getFileStorageRootPath();
			File file = new File(rootDir + blobItem.getPath());
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
		BlobItem blobItem = null;
		List<BlobItem> list = list(query);
		if (list != null && !list.isEmpty()) {
			blobItem = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					blobItem = model;
				}
			}
		}
		return blobItem;
	}

	public BlobItem getMaxBlob(String businessKey) {
		BlobItem blobItem = null;
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			blobItem = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > blobItem.getLastModified()) {
					blobItem = model;
				}
			}
		}
		return blobItem;
	}

	public BlobItem getMaxBlobWithBytes(String businessKey) {
		BlobItem blobItem = null;
		BlobItemQuery query = new BlobItemQuery();
		query.businessKey(businessKey);
		List<BlobItem> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			blobItem = list.get(0);
			Iterator<BlobItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				BlobItem model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					blobItem = model;
				}
			}
		}
		if (blobItem != null) {
			byte[] bytes = this.getBytesById(blobItem.getId());
			blobItem.setData(bytes);
		}
		return blobItem;
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

	@Transactional
	private void insertBlobItem(BlobItem blobItem) {
		if (StringUtils.isEmpty(blobItem.getId())) {
			blobItem.setId(idGenerator.getNextId());
		}
		String deviceId = SystemConfig.getString("fs_storage_strategy",
				"DATABASE");
		blobItem.setCreateDate(new Date());
		blobItem.setDeviceId(deviceId);
		if (blobItem.getFileId() == null) {
			blobItem.setFileId(UUID32.getUUID());
		}
		byte[] bytes = blobItem.getData();
		if (StringUtils.equals(deviceId, "DISK")) {
			String path = "/upload/files/" + DateUtils.getNowYearMonthDay();
			try {
				String filename = SystemProperties.getFileStorageRootPath()
						+ path + "/" + blobItem.getFileId();
				FileUtils.mkdirs(SystemProperties.getFileStorageRootPath()
						+ path);
				FileUtils.save(filename, bytes);
				blobItem.setPath(path + blobItem.getFileId());
			} catch (IOException ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blobItem.setPath(null);
			blobItem.setData(null);
			blobItemMapper.insertBlobItem_postgres(blobItem);
		} else {
			blobItem.setPath(null);
			blobItem.setData(null);
			blobItemMapper.insertBlobItem(blobItem);
		}
		if (SystemConfig.getBoolean("fs_storage_mongodb")
				&& ContextFactory.hasBean("mongo")) {
			GridFSInputFile file = null;
			try {
				String dbname = "gridfs_" + Environment.getCurrentSystemName();
				Mongo mongo = ContextFactory.getBean("mongo");
				DB db = mongo.getDB(dbname);
				GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
				if (bytes != null) {
					file = gridFS.createFile(bytes);
				} else {
					file = gridFS.createFile(blobItem.getInputStream());
				}
				file.setId(blobItem.getFileId());
				file.setFilename(blobItem.getFileId());// 指定唯一文件名称
				file.save();// 保存
				logger.debug("save file to mongodb.");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	public List<BlobItem> list(BlobItemQuery query) {
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
			DataFile blobItem = iterator.next();
			if (blobItem.getData() != null) {
				if (blobItem.getSize() <= 0) {
					blobItem.setSize(blobItem.getData().length);
				}
				this.insertBlob(blobItem);
			}
		}
	}

	@Transactional
	public void saveAll(Map<String, DataFile> dataMap) {
		BlobItemQuery query = new BlobItemQuery();
		List<String> names = new java.util.ArrayList<String>();
		for (String str : dataMap.keySet()) {
			names.add(str);
		}
		query.names(names);
		List<BlobItem> dataList = this.list(query);
		Map<String, Object> exists = new java.util.HashMap<String, Object>();
		if (dataList != null && dataList.size() > 0) {
			Iterator<BlobItem> iterator = dataList.iterator();
			while (iterator.hasNext()) {
				DataFile blobItem = iterator.next();
				exists.put(blobItem.getName(), blobItem);
			}
		}
		Iterator<DataFile> iterator = dataMap.values().iterator();
		while (iterator.hasNext()) {
			DataFile blobItem = iterator.next();
			if (blobItem.getData() != null) {
				if (exists.get(blobItem.getName()) != null) {
					DataFile model = (DataFile) exists.get(blobItem.getName());
					if (model.getSize() != blobItem.getData().length) {
						model.setFilename(blobItem.getFilename());
						model.setData(blobItem.getData());
						model.setSize(blobItem.getData().length);
						this.updateBlobFileInfo(model);
					}
				} else {
					if (blobItem.getSize() <= 0) {
						blobItem.setSize(blobItem.getData().length);
					}
					this.insertBlob(blobItem);
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
	public void updateBlobFileInfo(BlobItem blobItem) {
		if (blobItem.getData() == null) {
			throw new RuntimeException("bytes is null");
		}
		if (blobItem.getSize() <= 0) {
			blobItem.setSize(blobItem.getData().length);
		}

		String deviceId = blobItem.getDeviceId();
		byte[] bytes = blobItem.getData();

		if (StringUtils.equals(deviceId, "DISK")) {
			String path = "/upload/files/" + DateUtils.getNowYearMonthDay();
			try {
				String filename = SystemProperties.getFileStorageRootPath()
						+ path + "/" + blobItem.getFileId();
				FileUtils.mkdirs(SystemProperties.getFileStorageRootPath()
						+ path);
				FileUtils.save(filename, bytes);
				blobItem.setPath(path + blobItem.getFileId());
				blobItem.setData(null);
			} catch (IOException ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			blobItem.setPath(null);
			blobItem.setData(null);
			entityDAO.update("updateBlobItemFileInfo_postgres", blobItem);
		} else {
			blobItem.setPath(null);
			blobItem.setData(null);
			entityDAO.update("updateBlobItemFileInfo", blobItem);
		}

		if (SystemConfig.getBoolean("fs_storage_mongodb")
				&& ContextFactory.hasBean("mongo")) {
			GridFSInputFile file = null;
			try {
				String dbname = "gridfs_" + Environment.getCurrentSystemName();
				Mongo mongo = ContextFactory.getBean("mongo");
				DB db = mongo.getDB(dbname);
				GridFS gridFS = new GridFS(db, blobItem.getDeviceId());
				if (bytes != null) {
					file = gridFS.createFile(bytes);
				} else {
					file = gridFS.createFile(blobItem.getInputStream());
				}
				file.setId(blobItem.getFileId());
				file.setFilename(blobItem.getFileId());// 指定唯一文件名称
				file.save();// 保存
				logger.debug("save file to mongodb.");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
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