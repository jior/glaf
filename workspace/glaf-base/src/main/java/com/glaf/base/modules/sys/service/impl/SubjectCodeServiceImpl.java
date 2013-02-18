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

package com.glaf.base.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.utils.PageResult;

public class SubjectCodeServiceImpl implements SubjectCodeService {
	private static final Log logger = LogFactory
			.getLog(SubjectCodeServiceImpl.class);
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	public boolean create(SubjectCode bean) {
		return abstractDao.create(bean);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	public boolean update(SubjectCode bean) {
		return abstractDao.update(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	public boolean delete(SubjectCode bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            long
	 * @return boolean
	 */
	public boolean delete(long id) {
		SubjectCode bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id) {
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			SubjectCode bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public SubjectCode findById(long id) {
		return (SubjectCode) abstractDao.find(SubjectCode.class, new Long(id));
	}

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param code
	 *            String
	 * @return SubjectCode
	 */
	public SubjectCode findByCode(String code) {
		SubjectCode bean = null;
		Object[] values = new Object[] { code };
		String query = "from SubjectCode a where a.subjectCode=? order by a.subjectCode asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SubjectCode) list.get(0);
		}
		return bean;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	public List getSubjectCodeList() {
		String query = "from SubjectCode a order by a.subjectCode asc";
		return abstractDao.getList(query, null, null);
	}

	/**
	 * ��ȡ�����������б�
	 * 
	 * @param parent
	 *            long
	 * @return List
	 */
	public List getSysSubjectCodeList(long parent) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SubjectCode a where a.parent=? order by a.subjectCode asc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡ���÷�ҳ�б�
	 * 
	 * @param filter
	 * @return
	 */
	public PageResult getFeePage(Map filter) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(SubjectCode.class);
		// ���ñ��
		String subjectCode = (String) filter.get("subjectCode");
		logger.info("subjectCode:" + subjectCode);
		if (subjectCode != null) {
			criteria.add(Property.forName("subjectCode").like(
					"%" + subjectCode + "%"));
		}
		// ��������
		String subjectName = (String) filter.get("subjectName");
		logger.info("subjectName:" + subjectName);
		if (subjectName != null) {
			criteria.add(Property.forName("subjectName").like(
					"%" + subjectName + "%"));
		}

		if (subjectCode == null && subjectName == null) {
			criteria.add(Property.forName("parent").eq(new Long(0)));
		}

		criteria.addOrder(Order.asc("subjectCode"));
		int pageNo = 1;
		if ((String) filter.get("page_no") != null) {
			pageNo = Integer.parseInt((String) filter.get("page_no"));
		}
		int pageSize = 15;
		if ((String) filter.get("page_size") != null) {
			pageSize = Integer.parseInt((String) filter.get("page_size"));
		}
		return abstractDao.getList(criteria, pageNo, pageSize);
	}

	/**
	 * ��ȡ�¼��б�
	 * 
	 * @param filter
	 * @return
	 */
	public List getSubFeeList(Map filter) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(SubjectCode.class);

		// �ϼ�Ԥ��
		String parent = (String) filter.get("parent");
		logger.info("parent:" + parent);
		if (parent != null) {
			criteria.add(Property.forName("parent").eq(new Long(parent)));
		}
		criteria.addOrder(Order.asc("subjectCode"));
		return abstractDao.getList(criteria);
	}

}