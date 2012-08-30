package com.glaf.base.modules.others.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.others.model.Attachment;

public class AttachmentServiceImpl implements AttachmentService {
	private static final Log logger = LogFactory.getLog(AttachmentServiceImpl.class);

	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	public boolean create(Attachment bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	public boolean update(Attachment bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	public boolean delete(Attachment bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		Attachment bean = find(id);
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
		String path = Constants.ROOT_PATH + Constants.UPLOAD_DIR;
		logger.info(path);

		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			Attachment bean = find(id[i]);
			// 先删除附件
			if (bean != null) {
				File file = new File(path + File.separator + bean.getUrl());
				if (file.isFile())
					file.delete();
				list.add(bean);
			}
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public Attachment find(long id) {
		return (Attachment) abstractDao.find(Attachment.class, new Long(id));
	}

	/**
	 * 返回所有附件列表
	 * 
	 * @param parent
	 * @return
	 */
	public List getAttachmentList(long referId, int referType) {
		Object[] values = new Object[] { new Long(referId),
				new Integer(referType) };
		String query = "from Attachment a where a.referId=? and a.referType=? order by a.id desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	public Attachment find(long referId, int referType) {
		Attachment bean = null;
		Object[] values = new Object[] { new Long(referId),
				new Integer(referType) };
		String query = "from Attachment a where a.referId=? and a.referType=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (Attachment) list.get(0);
		}
		return bean;
	}

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	public Attachment find(long id, long referId, int referType) {
		Attachment bean = null;
		Object[] values = new Object[] { new Long(id), new Long(referId),
				new Integer(referType) };
		String query = "from Attachment a where a.id=? and a.referId=? and a.referType=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (Attachment) list.get(0);
		}
		return bean;
	}

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @param name
	 * @return Attachment
	 */
	public Attachment find(long referId, int referType, String name) {
		Attachment bean = null;
		Object[] values = new Object[] { new Long(referId),
				new Integer(referType), new String(name) };
		String query = "from Attachment a where a.referId=? and a.referType=? and a.name=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (Attachment) list.get(0);
		}
		return bean;
	}

	/**
	 * 返回所有附件名称列表
	 * 
	 * @param parent
	 * @return
	 */
	public Map getNameMap(long referId, int referType) {
		List list = getAttachmentList(referId, referType);
		Map map = new HashMap();
		for (int i = 0; list != null && i < list.size(); i++) {
			Attachment atta = (Attachment) list.get(i);
			map.put(atta.getName(), atta.getName());
		}
		return map;
	}

	/**
	 * 返回所有附件列表
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	public List getAttachmentList(long[] referIds, int referType) {
		StringBuffer referId = new StringBuffer("");
		for (int i = 0; i < referIds.length; i++) {
			referId.append(referIds[i]);
			if (i != referIds.length - 1) {
				referId.append(",");
			}
		}
		if (referId.length() == 0) {//
			return new ArrayList();
		}

		String query = "from Attachment a where a.referId IN ("
				+ referId.toString() + ")" + " and a.referType=" + referType
				+ " order by a.referId asc, a.id asc";
		List list = abstractDao.getList(query, null, null);
		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}

	/**
	 * 返回附件个数
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	public int getAttachmentCount(long[] referIds, int referType) {
		StringBuffer referId = new StringBuffer("");
		for (int i = 0; i < referIds.length; i++) {
			referId.append(referIds[i]);
			if (i != referIds.length - 1) {
				referId.append(",");
			}
		}
		if (referId.length() == 0) {//
			return 0;
		}

		String query = "select count(*) from Attachment a where a.referId IN ("
				+ referId.toString() + ")" + " and a.referType=" + referType;
		List list = abstractDao.getList(query, null, null);
		return ((Long) list.iterator().next()).intValue();
	}
	
	/**
	 * 返回对应的附件列表
	 * 
	 * @param parent
	 * @return
	 */
	public List getPurchaseAttachmentList(long[] ids, int referType) {
		StringBuffer referId = new StringBuffer("");
		for(int i=0;i<ids.length;i++){
			if(i!=0){
				referId.append(",");
			}
			referId.append(ids[i]);
		}
		if(referId.toString() != null && !"".equals(referId.toString())){
			String query = "from Attachment a where a.id in("+referId.toString()+") and a.referType="+referType+" order by a.id";
			return abstractDao.getList(query, null, null);
		}
		return null;		
	}
}
