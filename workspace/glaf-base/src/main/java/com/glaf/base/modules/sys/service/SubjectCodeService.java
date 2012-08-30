package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.utils.PageResult;

public interface SubjectCodeService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean create(SubjectCode bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean update(SubjectCode bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean delete(SubjectCode bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            long
	 * @return boolean
	 */
	boolean delete(long id);

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
	SubjectCode findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param code
	 *            String
	 * @return SubjectCode
	 */
	SubjectCode findByCode(String code);

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	List<SubjectCode> getSubjectCodeList();

	/**
	 * ��ȡ�����������б�
	 * 
	 * @param parent
	 *            long
	 * @return List
	 */
	List<SubjectCode> getSysSubjectCodeList(long parent);

	/**
	 * ��ȡ���÷�ҳ�б�
	 * 
	 * @param filter
	 * @return
	 */
	PageResult getFeePage(Map filter);

	/**
	 * ��ȡ�¼��б�
	 * 
	 * @param filter
	 * @return
	 */
	List<SubjectCode> getSubFeeList(Map filter);

	/**
	 * ��ȡ������ʹ�õĽ��
	 * 
	 * @param subjectCode
	 * @return
	 */
	double getUsedFee(String subjectCode);
}
