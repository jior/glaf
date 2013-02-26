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

package com.glaf.dts.service;

import java.util.*;

import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;

public interface ITransformStepService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	void deleteByIds(List<String> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<TransformStep> list(TransformStepQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getTransformStepCountByQueryCriteria(TransformStepQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<TransformStep> getTransformStepsByQueryCriteria(int start,
			int pageSize, TransformStepQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	TransformStep getTransformStep(String id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	void save(TransformStep transformStep);

}