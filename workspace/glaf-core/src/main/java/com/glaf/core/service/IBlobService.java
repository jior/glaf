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

package com.glaf.core.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataFile;
import com.glaf.core.query.BlobItemQuery;

/**
 * 
 * 字节流服务 本服务类提供字节流的创建、修改、删除、复制及查询服务
 */
@Transactional(readOnly = true)
public interface IBlobService {

	/**
	 * 复制字节流
	 * 
	 * @param sourceId
	 * @param destId
	 */
	@Transactional
	void copyBlob(String sourceId, String destId);

	/**
	 * 根据文件编号删除数据
	 * 
	 * @param fileId
	 */
	@Transactional
	void deleteBlobByFileId(String fileId);

	/**
	 * 根据资源编号删除数据
	 * 
	 * @param businessKey
	 */
	@Transactional
	void deleteBlobByBusinessKey(String businessKey);

	/**
	 * 根据主键删除数据
	 * 
	 * @param id
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据文件编号获取数据(不包含字节流)
	 * 
	 * @param fileId
	 * @return
	 */
	DataFile getBlobByFileId(String fileId);

	/**
	 * 根据文件名称获取数据(不包含字节流)
	 * 
	 * @param filename
	 * @return
	 */
	DataFile getBlobByFilename(String filename);

	/**
	 * 根据文件编号获取数据(包含字节流)
	 * 
	 * @param fileId
	 * @return
	 */
	DataFile getBlobWithBytesByFileId(String fileId);

	/**
	 * 根据主键获取数据(不包含字节流)
	 * 
	 * @param id
	 * @return
	 */
	DataFile getBlobById(String id);

	/**
	 * 根据参数获取数据(不包含字节流)
	 * 
	 * @param paramMap
	 * @return
	 */
	List<DataFile> getBlobList(BlobItemQuery query);

	/**
	 * 根据资源编号获取数据(不包含字节流)
	 * 
	 * @param businessKey
	 * @return
	 */
	List<DataFile> getBlobList(String businessKey);

	/**
	 * 根据文件编号获取字节流
	 * 
	 * @param fileId
	 * @return
	 */
	InputStream getInputStreamByFileId(String fileId);

	/**
	 * 根据主键获取字节流
	 * 
	 * @param id
	 * @return
	 */
	InputStream getInputStreamById(String id);

	/**
	 * 根据参数获取最大数据(不包含字节流)
	 * 
	 * @param paramMap
	 * @return
	 */
	DataFile getMaxBlob(BlobItemQuery query);

	/**
	 * 根据资源编号获取最大数据(不包含字节流)
	 * 
	 * @param businessKey
	 * @return
	 */
	DataFile getMaxBlob(String businessKey);

	/**
	 * 根据资源编号获取最大数据
	 * 
	 * @param businessKey
	 * @return
	 */
	DataFile getMaxBlobWithBytes(String businessKey);

	/**
	 * 新增记录
	 * 
	 * @param blobData
	 */
	@Transactional
	void insertBlob(DataFile blobData);

	/**
	 * 将记录标记为正式
	 * 
	 * @param createBy 创建者
	 * @param serviceKey 服务标识
	 * @param businessKey 业务标识
	 */
	@Transactional
	void makeMark(String createBy, String serviceKey, String businessKey);

	/**
	 * 批量保存记录
	 * 
	 * @param dataList
	 */
	@Transactional
	void saveAll(List<DataFile> dataList);

	/**
	 * 批量保存记录
	 * 
	 * @param dataMap
	 */
	@Transactional
	void saveAll(Map<String, DataFile> dataMap);

	/**
	 * 修改记录（不包含字节流）
	 * 
	 * @param model
	 */
	@Transactional
	void updateBlob(DataFile model);

	/**
	 * 修改记录中的文件内容（仅修改字节流）
	 * 
	 * @param model
	 */
	@Transactional
	void updateBlobFileInfo(DataFile model);
}