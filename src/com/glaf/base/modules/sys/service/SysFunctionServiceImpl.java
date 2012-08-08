package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysFunction;

public class SysFunctionServiceImpl implements SysFunctionService {
	private static final Log logger = LogFactory.getLog(SysFunctionServiceImpl.class);
	private AbstractSpringDao abstractDao;
	
	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}
	
	/**
	 * 保存
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean create(SysFunction bean){
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
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean update(SysFunction bean){		
		return abstractDao.update(bean);
	}
	/**
	 * 删除
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean delete(SysFunction bean){		
		return abstractDao.delete(bean);
	}
	/**
	 * 删除
	 * @param id int
	 * @return boolean
	 */
	public boolean delete(long id){
		SysFunction bean = findById(id);
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
			SysFunction bean = findById(id[i]);
			if(bean!=null)list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	public SysFunction findById(long id){
		return (SysFunction) abstractDao.find(SysFunction.class, new Long(id));
	}
		
	/**
	 * 获取列表
	 * @param appId int
	 * @return List
	 */
	public List getSysFunctionList(int appId){
		//计算总数
		Object[] values=new Object[]{new Long(appId)};
		String query = "from SysFunction a where a.app.id=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * 获取全部列表
	 * @return List
	 */
	public List getSysFunctionList(){
		String query = "from SysFunction a order by a.sort desc";
		return abstractDao.getList(query, null, null);
	}
	/**
	 * 排序
	 * @param bean SysFunction
	 * @param operate int 操作
	 */
	public void sort(SysFunction bean, int operate){
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
	private void sortByPrevious(SysFunction bean){
		Object[] values=new Object[]{bean.getApp(), new Integer(bean.getSort())};
		//查找前一个对象
		String query = "from SysFunction a where a.app=? and a.sort>? order by a.sort asc";		
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			SysFunction temp = (SysFunction)list.get(0);
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
	private void sortByForward(SysFunction bean){
		Object[] values=new Object[]{bean.getApp(), new Integer(bean.getSort())};
		//查找后一个对象
		String query = "from SysFunction a where a.app=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			SysFunction temp = (SysFunction)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//更新bean
			
			temp.setSort(i);
			abstractDao.update(temp);//更新temp
		}
	}
}
