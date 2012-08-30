package com.glaf.base.modules.sys.service;

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
import com.glaf.base.utils.PageResult;

public class SubjectCodeServiceImpl implements SubjectCodeService{
	private static final Log logger = LogFactory.getLog(SubjectCodeServiceImpl.class);
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}
	
	/**
	 * 保存
	 * 
	 * @param bean SubjectCode
	 * @return boolean
	 */
	public boolean create(SubjectCode bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean SubjectCode
	 * @return boolean
	 */
	public boolean update(SubjectCode bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean SubjectCode
	 * @return boolean
	 */
	public boolean delete(SubjectCode bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * 删除
	 * 
	 * @param id long
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
	 * 批量删除
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
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public SubjectCode findById(long id) {
		return (SubjectCode) abstractDao.find(SubjectCode.class, new Long(id));
	}

	/**
	 * 按名称查找对象
	 * 
	 * @param code String
	 * @return SubjectCode
	 */
	public SubjectCode findByCode(String code) {
		SubjectCode bean = null;
		Object[] values = new Object[] { code };
		String query = "from SubjectCode a where a.subjectCode=? order by a.subjectCode asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SubjectCode) list.get(0);
		}
		return bean;
	}
	/**
	 * 获取所有列表
	 * @return
	 */
	public List getSubjectCodeList(){
		String query = "from SubjectCode a order by a.subjectCode asc";
		return abstractDao.getList(query,null,null);
	}
	/**
	 * 获取满足条件的列表
	 * 
	 * @param parent long
	 * @return List
	 */
	public List getSysSubjectCodeList(long parent) {
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SubjectCode a where a.parent=? order by a.subjectCode asc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * 获取费用分页列表
	 * @param filter
	 * @return
	 */
	public PageResult getFeePage(Map filter) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubjectCode.class);
		//费用编号
		String subjectCode = (String)filter.get("subjectCode");
		logger.info("subjectCode:"+subjectCode);
		if(subjectCode!=null){
			criteria.add(Property.forName("subjectCode").like("%" + subjectCode + "%"));
		}
		//费用名称
		String subjectName = (String)filter.get("subjectName");
		logger.info("subjectName:"+subjectName);
		if(subjectName!=null){
			criteria.add(Property.forName("subjectName").like("%" + subjectName + "%"));
		}
		
		if(subjectCode==null && subjectName==null){
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
	 * 获取下级列表
	 * @param filter
	 * @return
	 */
	public List getSubFeeList(Map filter){
		DetachedCriteria criteria = DetachedCriteria.forClass(SubjectCode.class);
		
		//上级预算
		String parent = (String)filter.get("parent");
		logger.info("parent:"+parent);
		if(parent!=null){			
			criteria.add(Property.forName("parent").eq(new Long(parent)));			
		}		
		criteria.addOrder(Order.asc("subjectCode"));
		return abstractDao.getList(criteria);
	}
	/**
	 * 获取费用已使用的金额
	 * @param subjectCode
	 * @return
	 */
	public double getUsedFee(String subjectCode){
		double usedBudgetSum = 0;
		
		//重财已占用的金额
		String hql="select sum(a.realBudgetSum) from Finance a where a.subjectNo=? and a.status!=2";
		Object[] values=new Object[]{subjectCode};
		List list = abstractDao.getList(hql, values, null);
		if(list!=null && list.size()>0){//有记录
			Double sum = ((Double)list.get(0));
			usedBudgetSum = sum!=null?sum.doubleValue():0;
		}
		
		//无重财有费用预算的特采单已占用的金额
		hql="select sum(a.intendingSum) from Purchase a where a.hasFinance=0 and a.subjectNo=?";
		values=new Object[]{subjectCode};
		list = abstractDao.getList(hql, values, null);
		if(list!=null && list.size()>0){//有记录
			Double sum = ((Double)list.get(0));
			usedBudgetSum += sum!=null?sum.doubleValue():0;
		}
		return usedBudgetSum;
	}
}
