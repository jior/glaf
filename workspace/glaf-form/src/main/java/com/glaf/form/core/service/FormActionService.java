package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormAction;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormActionService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<FormAction> list(FormActionQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getFormActionCountByQueryCriteria(FormActionQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<FormAction> getFormActionsByQueryCriteria(int start, int pageSize,
			FormActionQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	FormAction getFormAction(String id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(FormAction formAction);

}
