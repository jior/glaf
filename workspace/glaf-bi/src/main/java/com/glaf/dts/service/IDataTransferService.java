package com.glaf.dts.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;

@Transactional(readOnly = true)
public interface IDataTransferService {

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
	void deleteByIds(List<String> ids);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	DataTransfer getDataTransfer(String id);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getDataTransferCountByQueryCriteria(DataTransferQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<DataTransfer> getDataTransfersByQueryCriteria(int start, int pageSize,
			DataTransferQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<DataTransfer> list(DataTransferQuery query);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(DataTransfer dataTransfer);

}
