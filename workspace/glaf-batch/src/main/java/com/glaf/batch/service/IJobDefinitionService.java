package com.glaf.batch.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.batch.domain.*;
import com.glaf.batch.query.*;

@Transactional(readOnly = true)
public interface IJobDefinitionService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> jobDefinitionIds);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	JobDefinition getJobDefinition(Long id);
	
	JobDefinition getJobDefinitionByKey(String jobKey);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getJobDefinitionCountByQueryCriteria(JobDefinitionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<JobDefinition> getJobDefinitionsByQueryCriteria(int start,
			int pageSize, JobDefinitionQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<JobDefinition> list(JobDefinitionQuery query);

	/**
	 * 保存一条Job定义信息
	 * 
	 * @return
	 */
	@Transactional
	void saveJobDefinition(JobDefinition jobDefinition);

	/**
	 * 保存一条Job步骤定义信息
	 * 
	 * @param stepDefinition
	 */
	@Transactional
	void saveStepDefinition(StepDefinition stepDefinition);

}
