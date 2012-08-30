package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.utils.PageResult;

public class SysRoleServiceImpl implements SysRoleService {
	private static final Log logger = LogFactory.getLog(SysRoleServiceImpl.class);
	private AbstractSpringDao abstractDao;	
	
	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}	

	/**
	 * 保存
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean create(SysRole bean){		
		boolean ret = false;		
		if(abstractDao.create(bean)){//插入记录成功
			bean.setSort((int)bean.getId());//设置排序号为刚插入的id值
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}
	/**
	 * 更新
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean update(SysRole bean){		
		return abstractDao.update(bean);
	}
	/**
	 * 删除
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean delete(SysRole bean){		
		return abstractDao.delete(bean);
	}
	/**
	 * 删除
	 * @param id int
	 * @return boolean
	 */
	public boolean delete(long id){
		SysRole bean = findById(id);
		if(bean!=null){
			return delete(bean);
		}else{
			return false;
		}
	}
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id){
		List list = new ArrayList();
		for(int i=0; i<id.length; i++){
			SysRole bean = findById(id[i]);
			if(bean!=null)list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	public SysRole findById(long id){
		return (SysRole) abstractDao.find(SysRole.class, new Long(id));
	}
	/**
	 * 按名称查找对象
	 * @param name String
	 * @return SysRole
	 */
	public SysRole findByName(String name){
		SysRole bean = null;
		Object[] values=new Object[]{name};
		String query = "from SysRole a where a.name=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			bean = (SysRole)list.get(0);
		}
		return bean;
	}
	/**
	 * 按code查找对象
	 * @param name String
	 * @return SysRole
	 */
	public SysRole findByCode(String code){
		SysRole bean = null;
		Object[] values=new Object[]{code};
		String query = "from SysRole a where a.code=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			bean = (SysRole)list.get(0);
		}
		return bean;
	}
	/**
	 * 获取分页列表
	 * @param pageNo int
	 * @param pageSize int
	 * @return
	 */
	public PageResult getSysRoleList(int pageNo, int pageSize){		
		//计算总数
		String query = "select count(*) from SysRole a";		
		int count = ((Long)abstractDao.getList(query, null, null).iterator().next()).intValue();
		if(count==0){//结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		//查询列表
		query = "from SysRole a order by a.sort desc";
		return abstractDao.getList(query, null, pageNo, pageSize, count);
	}	
	/**
	 * 获取列表
	 * @return List
	 */
	public List getSysRoleList(){
		String query = "from SysRole a order by a.sort desc";
		return abstractDao.getList(query, null, null);		
	}	
	/**
	 * 排序
	 * @param bean SysRole
	 * @param operate int 操作
	 */
	public void sort(SysRole bean, int operate){
		if(bean==null) return;
		if(operate==SysConstants.SORT_PREVIOUS){//前移
			sortByPrevious(bean);
		}else if(operate==SysConstants.SORT_FORWARD){//后移
			sortByForward(bean);
		}		
	}
	/**
	 * 向前移动排序
	 * @param bean
	 */
	private void sortByPrevious(SysRole bean){
		Object[] values=new Object[]{new Integer(bean.getSort())};
		//查找前一个对象
		String query = "from SysRole a where a.sort>? order by a.sort asc";		
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			SysRole temp = (SysRole)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//更新bean
			
			temp.setSort(i);
			abstractDao.update(temp);//更新temp
		}
	}
	/**
	 * 向后移动排序
	 * @param bean
	 */
	private void sortByForward(SysRole bean){
		Object[] values=new Object[]{new Integer(bean.getSort())};
		//查找后一个对象
		String query = "from SysRole a where a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			SysRole temp = (SysRole)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//更新bean
			
			temp.setSort(i);
			abstractDao.update(temp);//更新temp
		}
	}	
}
