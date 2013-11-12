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

package com.glaf.base.modules.sys.service;

import java.util.List;

import org.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.RealmInfo;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.core.base.TreeModel;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SysApplicationService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean create(SysApplication bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean delete(SysApplication bean);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysApplication findById(long id);
	
	/**
	 * ��������Ҷ���
	 * 
	 * @param code
	 *            String
	 * @return SysApplication
	 */
	SysApplication findByCode(String code);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysApplication
	 */
	SysApplication findByName(String name);

	/**
	 * ��ȡ�û��ܷ��ʵ���ģ���б�
	 * 
	 * @param userId
	 *            int
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getAccessAppList(long parent, SysUser user);

	/**
	 * ��ȡȫ���б�
	 * 
	 * @return List
	 */
	List<SysApplication> getApplicationList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getApplicationList(long parent);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getApplicationList(long parent, int pageNo, int pageSize);

	/**
	 * ��ȡ�˵�
	 * 
	 * @param parent
	 * @param userId
	 * @return
	 */
	String getMenu(long parent, SysUser user);

	List<RealmInfo> getRealmInfos();

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getSysApplicationCountByQueryCriteria(SysApplicationQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<SysApplication> getSysApplicationsByQueryCriteria(int start,
			int pageSize, SysApplicationQuery query);

	/**
	 * ��ȡ�û��˵�֮json����
	 * 
	 * @param parentId
	 *            ���ڵ���
	 * @param userId
	 *            �û���¼�˺�
	 * @return
	 */
	JSONArray getUserMenu(long parentId, String userId);
	
	TreeModel getTreeModelByAppId(long appId);
	
	/**
	 * ��ȡ�û�ĳ�������µ�ȫ������ڵ�
	 * @param parent ���ڵ���
	 * @param userId �û���¼�˺�
	 * @return
	 */
	List<TreeModel> getTreeModels(long parentId, String userId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, SysApplication bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean update(SysApplication bean);
}