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
	 * 保存
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean create(SysTree bean) {
		boolean ret = false;
		if (abstractDao.create(bean)) {// 插入记录成功
			bean.setSort((int) bean.getId());// 设置排序号为刚插入的id值
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean update(SysTree bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	public boolean delete(SysTree bean) {
		// 获取当前节点下的所有子节点列表
		List list = new ArrayList();
		getSysTree(list, (int) bean.getId(), 0);
		// 然后把自己也加入删除列表
		if (list != null) {
			list.add(bean);
		}

		return abstractDao.deleteAll(list);
	}

	/**
	 * 删除
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
	 * 批量删除
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
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public SysTree findById(long id) {
		return (SysTree) abstractDao.find(SysTree.class, new Long(id));
	}

	/**
	 * 按名称查找对象
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
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysTree) list.get(0);
		}
		return bean;
	}

	/**
	 * 获取分页列表
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
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "select count(*) from SysTree a where a.parent=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// 结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// 查询列表
		query = "from SysTree a where a.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public List getSysTreeList(int parent) {
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 获取全部列表（add by kxr 2010-09-14)
	 * 
	 * @param parent
	 *            int 父ID
	 * @param status
	 *            int 状态
	 * @return List
	 */
	public List getSysTreeListForDept(int parent, int status) {
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? ";
		if (status != -1) {
			query += " and a.department.status = " + status;
		}
		query += "	order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 获取树型列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public void getSysTree(List tree, int parent, int deep) {
		// 首先获取当前节点下的所有子节点
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysTree a where a.parent=? order by a.sort desc";
		List nodes = abstractDao.getList(query, values, null);
		if (nodes != null) {
			Iterator iter = nodes.iterator();
			while (iter.hasNext()) {// 递归遍历
				SysTree bean = (SysTree) iter.next();
				bean.setDeep(deep + 1);
				tree.add(bean);// 加入到数组
				getSysTree(tree, (int) bean.getId(), bean.getDeep());
			}
		}
	}

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	public void getSysTreeParent(List tree, long id) {
		// 查找是否有父节点
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParent() != 0) {
				getSysTreeParent(tree, bean.getParent());
			}
			tree.add(bean);
		}
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysTree
	 * @param operate
	 *            int 操作
	 */
	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// 查找前一个对象
		String query = "from SysTree a where a.parent=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
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
	private void sortByForward(long parent, SysTree bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// 查找后一个对象
		String query = "from SysTree a where a.parent=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// 更新bean

			temp.setSort(i);
			abstractDao.update(temp);// 更新temp
		}
	}

	/**
	 * 按树编号获取树节点
	 * 
	 * @param tree
	 * @return SysTree
	 */
	public SysTree getSysTreeByCode(String code) {
		SysTree bean = null;
		Object[] values = new Object[] { code };
		// 查找后一个对象
		String query = "from SysTree a where a.code=?";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysTree) list.get(0);
		}
		return bean;
	}
}
