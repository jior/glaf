package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.utils.PageResult;

public class DictoryServiceImpl implements DictoryService {
	private static final Log logger = LogFactory.getLog(DictoryServiceImpl.class);
	private AbstractSpringDao abstractDao;
	
	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}
	
	/**
	 * 保存
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean create(Dictory bean){		
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
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean update(Dictory bean){	
		logger.info("create Dictory");
		return abstractDao.update(bean);
	}
	/**
	 * 删除
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean delete(Dictory bean){
		return abstractDao.delete(bean);
	}
	/**
	 * 删除
	 * @param id int
	 * @return boolean
	 */
	public boolean delete(long id){
		Dictory bean = find(id);
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
		List<Dictory> list = new ArrayList<Dictory>();
		for(int i=0; i<id.length; i++){
			Dictory bean = find(id[i]);
			if(bean!=null)list.add(bean);			
		}
		return abstractDao.deleteAll(list);
	}
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	public Dictory find(long id){
		return (Dictory) abstractDao.find(Dictory.class, new Long(id));
	}
	/**
	 * 获取分页列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getDictoryList(int pageNo, int pageSize){
		//计算总数
		String query = "select count(*) from Dictory a";
		int count = ((Long)abstractDao.getList(query, null, null).iterator().next()).intValue();
		if(count==0){//结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		//查询列表
		query = "from Dictory a order by a.sort desc";
		return abstractDao.getList(query, null, pageNo, pageSize, count);		
	}
	
	/**
	 * 按类型号搜索列表
	 * @param parent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getDictoryList(long parent, int pageNo, int pageSize){
		//计算总数
		Object[] values=new Object[]{new Long(parent)};
		String query = "select count(*) from Dictory a where a.typeId=?";
		int count = ((Long)abstractDao.getList(query, values, null).iterator().next()).intValue();
		if(count==0){//结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		
		//查询列表
		query = "from Dictory a where a.typeId=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);	
	}
	/**
	 * 返回某分类下的所有字典列表
	 * @param parent
	 * @return
	 */
	public List<Dictory> getDictoryList(long parent){
		Object[] values=new Object[]{new Long(parent)};
		String query = "from Dictory a where a.typeId=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * 返回某分类下的所有字典列表
	 * @param parent
	 * @return
	 */
	public List<Dictory> getAvailableDictoryList(long parent){
		Object[] values=new Object[]{new Long(parent)};
		String query = "from Dictory a where a.typeId=? and a.blocked=0 order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * 排序
	 * @param bean Dictory
	 * @param operate int 操作
	 */
	public void sort(long parent, Dictory bean, int operate){
		if(bean==null) return;
		if(operate==SysConstants.SORT_PREVIOUS){//前移
			sortByPrevious(parent, bean);
		}else if(operate==SysConstants.SORT_FORWARD){//后移
			sortByForward(parent, bean);
		}		
	}
	/**
	 * 向前移动排序
	 * @param bean
	 */
	private void sortByPrevious(long parent, Dictory bean){
		Object[] values=new Object[]{new Long(parent), new Integer(bean.getSort())};
		//查找前一个对象
		String query = "from Dictory a where a.typeId=? and a.sort>? order by a.sort asc";		
		List<?> list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			Dictory temp = (Dictory)list.get(0);
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
	private void sortByForward(long parent, Dictory bean){
		Object[] values=new Object[]{new Long(parent), new Integer(bean.getSort())};
		//查找后一个对象
		String query = "from Dictory a where a.typeId=? and a.sort<? order by a.sort desc";
		List<?> list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//有记录
			Dictory temp = (Dictory)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//更新bean
			
			temp.setSort(i);
			abstractDao.update(temp);//更新temp
		}
	}
	
 
	/**
	 * 主要用在获得核算项目的键值对
	 * @param list
	 * @param purchaseId
	 * @return
	 */
	public Map<String, String> getDictoryMap(List<Dictory> list,long purchaseId){
		Map<String, String> dictoryMap = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			String str = "";
			Dictory dictory = (Dictory)list.get(i);
			String sql = dictory.getExt1();
			if(sql!=null && sql.length()>0){
				if(dictory.getCode().trim().equals("contractNo")){
					sql = sql.replaceAll("purchaseId", purchaseId+"");
					str = abstractDao.getStringBySQL(sql);
					if(str==null || str.trim().length()==0 || "".equals(str.trim())){
						sql = sql.replaceAll("contractNo", "orderNo");
						str = abstractDao.getStringBySQL(sql);
					}
				}else{
					sql = sql.replaceAll("purchaseId", purchaseId+"");
					str = abstractDao.getStringBySQL(sql);
				}
				//logger.info("查询核算项目值的sql==="+sql);
			}
			dictoryMap.put(dictory.getName(), str);
			//logger.info("特采单ID："+purchaseId+"对应核算项目的名称："+dictory.getName()+"的值："+str);
		}
		return dictoryMap;
	}
	
	/**
	 * 根据ID拿code
	 * @param id
	 * @return
	 */
	public String getCodeById(long id){
		Dictory dic = find(id);
		return dic.getCode();
	}
}
