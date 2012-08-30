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
	 * ����
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean create(SysRole bean){		
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
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean update(SysRole bean){		
		return abstractDao.update(bean);
	}
	/**
	 * ɾ��
	 * @param bean SysRole
	 * @return boolean
	 */
	public boolean delete(SysRole bean){		
		return abstractDao.delete(bean);
	}
	/**
	 * ɾ��
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
	 * ����ɾ��
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
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	public SysRole findById(long id){
		return (SysRole) abstractDao.find(SysRole.class, new Long(id));
	}
	/**
	 * �����Ʋ��Ҷ���
	 * @param name String
	 * @return SysRole
	 */
	public SysRole findByName(String name){
		SysRole bean = null;
		Object[] values=new Object[]{name};
		String query = "from SysRole a where a.name=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			bean = (SysRole)list.get(0);
		}
		return bean;
	}
	/**
	 * ��code���Ҷ���
	 * @param name String
	 * @return SysRole
	 */
	public SysRole findByCode(String code){
		SysRole bean = null;
		Object[] values=new Object[]{code};
		String query = "from SysRole a where a.code=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			bean = (SysRole)list.get(0);
		}
		return bean;
	}
	/**
	 * ��ȡ��ҳ�б�
	 * @param pageNo int
	 * @param pageSize int
	 * @return
	 */
	public PageResult getSysRoleList(int pageNo, int pageSize){		
		//��������
		String query = "select count(*) from SysRole a";		
		int count = ((Long)abstractDao.getList(query, null, null).iterator().next()).intValue();
		if(count==0){//�����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		//��ѯ�б�
		query = "from SysRole a order by a.sort desc";
		return abstractDao.getList(query, null, pageNo, pageSize, count);
	}	
	/**
	 * ��ȡ�б�
	 * @return List
	 */
	public List getSysRoleList(){
		String query = "from SysRole a order by a.sort desc";
		return abstractDao.getList(query, null, null);		
	}	
	/**
	 * ����
	 * @param bean SysRole
	 * @param operate int ����
	 */
	public void sort(SysRole bean, int operate){
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
	private void sortByPrevious(SysRole bean){
		Object[] values=new Object[]{new Integer(bean.getSort())};
		//����ǰһ������
		String query = "from SysRole a where a.sort>? order by a.sort asc";		
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			SysRole temp = (SysRole)list.get(0);
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
	private void sortByForward(SysRole bean){
		Object[] values=new Object[]{new Integer(bean.getSort())};
		//���Һ�һ������
		String query = "from SysRole a where a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			SysRole temp = (SysRole)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//����bean
			
			temp.setSort(i);
			abstractDao.update(temp);//����temp
		}
	}	
}
