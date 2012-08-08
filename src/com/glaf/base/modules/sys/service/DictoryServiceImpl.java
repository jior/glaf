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
	 * ����
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean create(Dictory bean){		
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
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean update(Dictory bean){	
		logger.info("create Dictory");
		return abstractDao.update(bean);
	}
	/**
	 * ɾ��
	 * @param bean Dictory
	 * @return boolean
	 */
	public boolean delete(Dictory bean){
		return abstractDao.delete(bean);
	}
	/**
	 * ɾ��
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
	 * ����ɾ��
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
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	public Dictory find(long id){
		return (Dictory) abstractDao.find(Dictory.class, new Long(id));
	}
	/**
	 * ��ȡ��ҳ�б�
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getDictoryList(int pageNo, int pageSize){
		//��������
		String query = "select count(*) from Dictory a";
		int count = ((Long)abstractDao.getList(query, null, null).iterator().next()).intValue();
		if(count==0){//�����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		//��ѯ�б�
		query = "from Dictory a order by a.sort desc";
		return abstractDao.getList(query, null, pageNo, pageSize, count);		
	}
	
	/**
	 * �����ͺ������б�
	 * @param parent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getDictoryList(long parent, int pageNo, int pageSize){
		//��������
		Object[] values=new Object[]{new Long(parent)};
		String query = "select count(*) from Dictory a where a.typeId=?";
		int count = ((Long)abstractDao.getList(query, values, null).iterator().next()).intValue();
		if(count==0){//�����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}		
		
		//��ѯ�б�
		query = "from Dictory a where a.typeId=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);	
	}
	/**
	 * ����ĳ�����µ������ֵ��б�
	 * @param parent
	 * @return
	 */
	public List<Dictory> getDictoryList(long parent){
		Object[] values=new Object[]{new Long(parent)};
		String query = "from Dictory a where a.typeId=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * ����ĳ�����µ������ֵ��б�
	 * @param parent
	 * @return
	 */
	public List<Dictory> getAvailableDictoryList(long parent){
		Object[] values=new Object[]{new Long(parent)};
		String query = "from Dictory a where a.typeId=? and a.blocked=0 order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}
	/**
	 * ����
	 * @param bean Dictory
	 * @param operate int ����
	 */
	public void sort(long parent, Dictory bean, int operate){
		if(bean==null) return;
		if(operate==SysConstants.SORT_PREVIOUS){//ǰ��
			sortByPrevious(parent, bean);
		}else if(operate==SysConstants.SORT_FORWARD){//����
			sortByForward(parent, bean);
		}		
	}
	/**
	 * ��ǰ�ƶ�����
	 * @param bean
	 */
	private void sortByPrevious(long parent, Dictory bean){
		Object[] values=new Object[]{new Long(parent), new Integer(bean.getSort())};
		//����ǰһ������
		String query = "from Dictory a where a.typeId=? and a.sort>? order by a.sort asc";		
		List<?> list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			Dictory temp = (Dictory)list.get(0);
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
	private void sortByForward(long parent, Dictory bean){
		Object[] values=new Object[]{new Long(parent), new Integer(bean.getSort())};
		//���Һ�һ������
		String query = "from Dictory a where a.typeId=? and a.sort<? order by a.sort desc";
		List<?> list = abstractDao.getList(query, values, null);
		if(list!=null && list.size()>0){//�м�¼
			Dictory temp = (Dictory)list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);//����bean
			
			temp.setSort(i);
			abstractDao.update(temp);//����temp
		}
	}
	
 
	/**
	 * ��Ҫ���ڻ�ú�����Ŀ�ļ�ֵ��
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
				//logger.info("��ѯ������Ŀֵ��sql==="+sql);
			}
			dictoryMap.put(dictory.getName(), str);
			//logger.info("�زɵ�ID��"+purchaseId+"��Ӧ������Ŀ�����ƣ�"+dictory.getName()+"��ֵ��"+str);
		}
		return dictoryMap;
	}
	
	/**
	 * ����ID��code
	 * @param id
	 * @return
	 */
	public String getCodeById(long id){
		Dictory dic = find(id);
		return dic.getCode();
	}
}
