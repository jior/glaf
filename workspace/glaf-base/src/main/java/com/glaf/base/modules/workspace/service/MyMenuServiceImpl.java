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
	 * 保存新增信息
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean create(MyMenu bean) {
		boolean rst = false;
		if (abstractDao.create(bean)) {// 插入记录成功
			bean.setSort((int) bean.getId());// 设置排序号为刚插入的id值
			abstractDao.update(bean);
			rst = true;
		}
		return rst;
	}

	/**
	 * 更新信息
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean update(MyMenu myMenu) {
		return abstractDao.update(myMenu);
	}

	/**
	 * 单项删除
	 * 
	 * @param myMenu
	 * @return
	 */
	public boolean delete(MyMenu myMenu) {
		return abstractDao.delete(myMenu);
	}

	/**
	 * 单项删除
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
	 * 批量删除
	 * 
	 * @param c
	 * @return
	 */
	public boolean deleteAll(Collection c) {
		return abstractDao.deleteAll(c);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public MyMenu find(long id) {
		return (MyMenu) abstractDao.find(MyMenu.class, new Long(id));
	}

	/**
	 * 获取列表(按序列号、编号排序)
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getMyMenuList(long userId, int pageNo, int pageSize) {
		// 计算总数
		String query = "select count(*) from MyMenu a where a.userId = ?";
		Object[] values = new Object[] { new Long(userId) };
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// 结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		logger.info(query);

		// 查询列表
		query = "from MyMenu a where a.userId = ? order by a.sort desc, a.id desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * 获取列表(按序列号、编号排序)
	 * 
	 * @param userId
	 * @return
	 */
	public List getMyMenuList(long userId) {

		Object[] values = new Object[] { new Long(userId) };
		// 查询列表
		String query = "from MyMenu a where a.userId = ? order by a.sort desc, a.id desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int 操作
	 */
	public void sort(MyMenu bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(bean);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(MyMenu bean) {
		Object[] values = new Object[] { new Long(bean.getUserId()),
				new Integer(bean.getSort()) };
		// 查找前一个对象
		String query = "from MyMenu a where a.userId=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			MyMenu temp = (MyMenu) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// 更新bean

			temp.setSort(i);
			abstractDao.update(temp);// 更新temp

		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(MyMenu bean) {
		Object[] values = new Object[] { new Long(bean.getUserId()),
				new Integer(bean.getSort()) };
		// 查找后一个对象
		String query = "from MyMenu a where a.userId=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			MyMenu temp = (MyMenu) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// 更新bean

			temp.setSort(i);
			abstractDao.update(temp);// 更新temp

		}
	}

}
