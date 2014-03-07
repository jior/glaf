package com.glaf.batch.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.batch.domain.*;
import com.glaf.batch.query.*;

@Transactional(readOnly = true)
public interface IJobDefinitionService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> jobDefinitionIds);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	JobDefinition getJobDefinition(Long id);
	
	JobDefinition getJobDefinitionByKey(String jobKey);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getJobDefinitionCountByQueryCriteria(JobDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<JobDefinition> getJobDefinitionsByQueryCriteria(int start,
			int pageSize, JobDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<JobDefinition> list(JobDefinitionQuery query);

	/**
	 * ����һ��Job������Ϣ
	 * 
	 * @return
	 */
	@Transactional
	void saveJobDefinition(JobDefinition jobDefinition);

	/**
	 * ����һ��Job���趨����Ϣ
	 * 
	 * @param stepDefinition
	 */
	@Transactional
	void saveStepDefinition(StepDefinition stepDefinition);

}
