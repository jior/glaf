package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormAction;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormActionService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FormAction> list(FormActionQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormActionCountByQueryCriteria(FormActionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormAction> getFormActionsByQueryCriteria(int start, int pageSize,
			FormActionQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormAction getFormAction(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormAction formAction);

}
