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
	 * ����
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean create(SysFunction bean){
		boolean ret = false;		
		if(abstractDao.create(bean)){//�����¼�ɹ�
			bean.setSort((int)bean.getId());//���������Ϊ�ղ����idֵ
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}
	/**
	 * ����
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean update(SysFunction bean){		
		return abstractDao.update(bean);
	}
	/**
	 * ɾ��
	 * @param bean SysFunction
	 * @return boolean
	 */
	public boolean delete(SysFunction bean){		
		return abstractDao.delete(bean);
	}
	/**
	 * ɾ��
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
	 * ����ɾ��
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
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	public SysFunction findById(long id){
		return (SysFunction) abstractDao.find(SysFunction.class, new Long(id));
	}
		
	/**
	 * ��ȡ�б�
	 * @param appId int
	 * @return List
	 */
	public List getSysFunctionList(int appId){
		//��������
		Object[] values=new Object[]{new Long(appId)};
		String query = "from SysFunction a where a.app.id=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * ��ȡȫ���б�
	 * @return List
	 */
	public List getSysFunctionList(){
		String query = "from SysFunction a order by a.sort desc";
		return abstractDao.getList(query, null, null);
	}
	/**
	 * ����
	 * @param bean SysFunction
	 * @param operate int ����
	 */
	public void sort(SysFunction bean, int operate){
		if(bean==null) return;
		if(operate==SysConstants.SORT_PREVIOUS){//ǰ��
			sortByPrevious(bean);
		}else if(operate==SysConstants.SORT_FORWARD){//����
			sortByForward(bean);
		}		
	}
	/**
	 * ��ǰ�ƶ�����
	 * @param bean
	 */
	private void sortByPrevious(SysFunction bean){
		Object[] values=new Object[]{bean.getApp(), new Integer(bean.getSort())};
		//����ǰһ������
		String query = "from SysFunction a where a.app=? and a.sort>? order by a.sort asc";		
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			SysFunction temp = (SysFunction)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//����bean
			
			temp.setSort(i);
			abstractDao.update(temp);//����temp
		}
	}
	/**
	 * ����ƶ�����
	 * @param bean
	 */
	private void sortByForward(SysFunction bean){
		Object[] values=new Object[]{bean.getApp(), new Integer(bean.getSort())};
		//���Һ�һ������
		String query = "from SysFunction a where a.app=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			SysFunction temp = (SysFunction)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//����bean
			
			temp.setSort(i);
			abstractDao.update(temp);//����temp
		}
	}
}
