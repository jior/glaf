package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.utils.PageResult;

public class SysTreeServiceImpl implements SysTreeService {
	private static final Log logger = LogFactory
			.getLog(SysTreeServiceImpl.class);
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean create(SysTree bean) {
		boolean ret = false;
		if (abstractDao.create(bean)) {// �����¼�ɹ�
			bean.setSort((int) bean.getId());// ���������Ϊ�ղ����idֵ
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean update(SysTree bean) {
		return abstractDao.update(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean delete(SysTree bean) {
		// ��ȡ��ǰ�ڵ��µ������ӽڵ��б�
		List list = new ArrayList();
		getSysTree(list, (int) bean.getId(), 0);
		// Ȼ����Լ�Ҳ����ɾ���б�
		if (list != null) {
			list.add(bean);
		}

		return abstractDao.deleteAll(list);
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysTree bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id) {
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			SysTree bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public SysTree findById(long id) {
		return (SysTree) abstractDao.find(SysTree.class, new Long(id));
	}

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysTree
	 */
	public SysTree findByName(String name) {
		SysTree bean = null;
		Object[] values = new Object[] { name };
		String query = "from SysTree a where a.name=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysTree) list.get(0);
		}
		return bean;
	}

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	public PageResult getSysTreeList(int parent, int pageNo, int pageSize) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "select count(*) from SysTree a where a.parent=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// �����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// ��ѯ�б�
		query = "from SysTree a where a.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * ��ȡȫ���б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public List getSysTreeList(int parent) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡȫ���б�add by kxr 2010-09-14)
	 * 
	 * @param parent
	 *            int ��ID
	 * @param status
	 *            int ״̬
	 * @return List
	 */
	public List getSysTreeListForDept(int parent, int status) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? ";
		if (status != -1) {
			query += " and a.department.status = " + status;
		}
		query += "	order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public void getSysTree(List tree, int parent, int deep) {
		// ���Ȼ�ȡ��ǰ�ڵ��µ������ӽڵ�
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? order by a.sort desc";
		List nodes = abstractDao.getList(query, values, null);
		if (nodes != null) {
			Iterator iter = nodes.iterator();
			while (iter.hasNext()) {// �ݹ����
				SysTree bean = (SysTree) iter.next();
				bean.setDeep(deep + 1);
				tree.add(bean);// ���뵽����
				getSysTree(tree, (int) bean.getId(), bean.getDeep());
			}
		}
	}

	/**
	 * ��ȡ���ڵ��б���:��Ŀ¼>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	public void getSysTreeParent(List tree, long id) {
		// �����Ƿ��и��ڵ�
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParent() != 0) {
				getSysTreeParent(tree, bean.getParent());
			}
			tree.add(bean);
		}
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @param operate
	 *            int ����
	 */
	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// ǰ��
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// ����
			sortByForward(parent, bean);
		}
	}

	/**
	 * ��ǰ�ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ����ǰһ������
		String query = "from SysTree a where a.parent=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
		}
	}

	/**
	 * ����ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysTree bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ���Һ�һ������
		String query = "from SysTree a where a.parent=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
		}
	}

	/**
	 * ������Ż�ȡ���ڵ�
	 * 
	 * @param tree
	 * @return SysTree
	 */
	public SysTree getSysTreeByCode(String code) {
		SysTree bean = null;
		Object[] values = new Object[] { code };
		// ���Һ�һ������
		String query = "from SysTree a where a.code=?";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysTree) list.get(0);
		}
		return bean;
	}
}
