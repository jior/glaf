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
 * �ֽ������� ���������ṩ�ֽ����Ĵ������޸ġ�ɾ�������Ƽ���ѯ����
 */
@Transactional(readOnly = true)
public interface IBlobService {

	/**
	 * �����ֽ���
	 * 
	 * @param sourceId
	 * @param destId
	 */
	@Transactional
	void copyBlob(String sourceId, String destId);

	/**
	 * �����ļ����ɾ������
	 * 
	 * @param fileId
	 */
	@Transactional
	void deleteBlobByFileId(String fileId);

	/**
	 * ������Դ���ɾ������
	 * 
	 * @param businessKey
	 */
	@Transactional
	void deleteBlobByBusinessKey(String businessKey);

	/**
	 * ��������ɾ������
	 * 
	 * @param id
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * �����ļ���Ż�ȡ����(�������ֽ���)
	 * 
	 * @param fileId
	 * @return
	 */
	DataFile getBlobByFileId(String fileId);

	/**
	 * �����ļ����ƻ�ȡ����(�������ֽ���)
	 * 
	 * @param filename
	 * @return
	 */
	DataFile getBlobByFilename(String filename);

	/**
	 * �����ļ���Ż�ȡ����(�����ֽ���)
	 * 
	 * @param fileId
	 * @return
	 */
	DataFile getBlobWithBytesByFileId(String fileId);

	/**
	 * ����������ȡ����(�������ֽ���)
	 * 
	 * @param id
	 * @return
	 */
	DataFile getBlobById(String id);

	/**
	 * ���ݲ�����ȡ����(�������ֽ���)
	 * 
	 * @param paramMap
	 * @return
	 */
	List<DataFile> getBlobList(BlobItemQuery query);

	/**
	 * ������Դ��Ż�ȡ����(�������ֽ���)
	 * 
	 * @param businessKey
	 * @return
	 */
	List<DataFile> getBlobList(String businessKey);

	/**
	 * �����ļ���Ż�ȡ�ֽ���
	 * 
	 * @param fileId
	 * @return
	 */
	InputStream getInputStreamByFileId(String fileId);

	/**
	 * ����������ȡ�ֽ���
	 * 
	 * @param id
	 * @return
	 */
	InputStream getInputStreamById(String id);

	/**
	 * ���ݲ�����ȡ�������(�������ֽ���)
	 * 
	 * @param paramMap
	 * @return
	 */
	DataFile getMaxBlob(BlobItemQuery query);

	/**
	 * ������Դ��Ż�ȡ�������(�������ֽ���)
	 * 
	 * @param businessKey
	 * @return
	 */
	DataFile getMaxBlob(String businessKey);

	/**
	 * ������Դ��Ż�ȡ�������
	 * 
	 * @param businessKey
	 * @return
	 */
	DataFile getMaxBlobWithBytes(String businessKey);

	/**
	 * ������¼
	 * 
	 * @param blobData
	 */
	@Transactional
	void insertBlob(DataFile blobData);

	/**
	 * ����¼���Ϊ��ʽ
	 * 
	 * @param createBy ������
	 * @param serviceKey �����ʶ
	 * @param businessKey ҵ���ʶ
	 */
	@Transactional
	void makeMark(String createBy, String serviceKey, String businessKey);

	/**
	 * ���������¼
	 * 
	 * @param dataList
	 */
	@Transactional
	void saveAll(List<DataFile> dataList);

	/**
	 * ���������¼
	 * 
	 * @param dataMap
	 */
	@Transactional
	void saveAll(Map<String, DataFile> dataMap);

	/**
	 * �޸ļ�¼���������ֽ�����
	 * 
	 * @param model
	 */
	@Transactional
	void updateBlob(DataFile model);

	/**
	 * �޸ļ�¼�е��ļ����ݣ����޸��ֽ�����
	 * 
	 * @param model
	 */
	@Transactional
	void updateBlobFileInfo(DataFile model);
}