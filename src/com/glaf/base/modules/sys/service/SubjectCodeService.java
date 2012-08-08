package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.utils.PageResult;

public interface SubjectCodeService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean create(SubjectCode bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean update(SubjectCode bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean delete(SubjectCode bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            long
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SubjectCode findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param code
	 *            String
	 * @return SubjectCode
	 */
	SubjectCode findByCode(String code);

	/**
	 * 获取所有列表
	 * 
	 * @return
	 */
	List<SubjectCode> getSubjectCodeList();

	/**
	 * 获取满足条件的列表
	 * 
	 * @param parent
	 *            long
	 * @return List
	 */
	List<SubjectCode> getSysSubjectCodeList(long parent);

	/**
	 * 获取费用分页列表
	 * 
	 * @param filter
	 * @return
	 */
	PageResult getFeePage(Map filter);

	/**
	 * 获取下级列表
	 * 
	 * @param filter
	 * @return
	 */
	List<SubjectCode> getSubFeeList(Map filter);

	/**
	 * 获取费用已使用的金额
	 * 
	 * @param subjectCode
	 * @return
	 */
	double getUsedFee(String subjectCode);
}
