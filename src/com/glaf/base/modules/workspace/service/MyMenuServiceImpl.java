package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.base.utils.PageResult;

public class MyMenuServiceImpl implements MyMenuService {
	private static final Log logger = LogFactory.getLog(MyMenuServiceImpl.class);

	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean create(MyMenu bean) {
		boolean rst = false;
		if (abstractDao.create(bean)) {// �����¼�ɹ�
			bean.setSort((int) bean.getId());// ���������Ϊ�ղ����idֵ
			abstractDao.update(bean);
			rst = true;
		}
		return rst;
	}

	/**
	 * ������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean update(MyMenu myMenu) {
		return abstractDao.update(myMenu);
	}

	/**
	 * ����ɾ��
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean delete(MyMenu myMenu) {
		return abstractDao.delete(myMenu);
	}

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		MyMenu bean = find(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * ����ɾ��
	 * 
	 * @param c
	 * @return
	 */
	public boolean deleteAll(Collection c) {
		return abstractDao.deleteAll(c);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public MyMenu find(long id) {
		return (MyMenu) abstractDao.find(MyMenu.class, new Long(id));
	}

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getMyMenuList(long userId, int pageNo, int pageSize) {
		// ��������
		String query = "select count(*) from MyMenu a where a.userId = ?";
		Object[] values = new Object[] { new Long(userId) };
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// �����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		logger.info(query);

		// ��ѯ�б�
		query = "from MyMenu a where a.userId = ? order by a.sort desc, a.id desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @return
	 */
	public List getMyMenuList(long userId) {

		Object[] values = new Object[] { new Long(userId) };
		// ��ѯ�б�
		String query = "from MyMenu a where a.userId = ? order by a.sort desc, a.id desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int ����
	 */
	public void sort(MyMenu bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// ǰ��
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// ����
			sortByForward(bean);
		}
	}

	/**
	 * ��ǰ�ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByPrevious(MyMenu bean) {
		Object[] values = new Object[] { new Long(bean.getUserId()),
				new Integer(bean.getSort()) };
		// ����ǰһ������
		String query = "from MyMenu a where a.userId=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			MyMenu temp = (MyMenu) list.get(0);
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
	private void sortByForward(MyMenu bean) {
		Object[] values = new Object[] { new Long(bean.getUserId()),
				new Integer(bean.getSort()) };
		// ���Һ�һ������
		String query = "from MyMenu a where a.userId=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			MyMenu temp = (MyMenu) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean

			temp.setSort(i);
			abstractDao.update(temp);// ����temp

		}
	}

}
