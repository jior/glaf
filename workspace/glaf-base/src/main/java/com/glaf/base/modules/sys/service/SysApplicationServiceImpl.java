package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.glaf.base.context.ApplicationContext;
import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public class SysApplicationServiceImpl implements SysApplicationService {
	private static final Log logger = LogFactory
			.getLog(SysApplicationServiceImpl.class);
	private AuthorizeService authorizeService;
	private SysTreeService sysTreeService;
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	public boolean create(SysApplication bean) {
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
	 *            SysApplication
	 * @return boolean
	 */
	public boolean update(SysApplication bean) {
		return abstractDao.update(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	public boolean delete(SysApplication bean) {
		return sysTreeService.delete(bean.getId());
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysApplication bean = findById(id);
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
			SysApplication bean = findById(id[i]);
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
	public SysApplication findById(long id) {
		return (SysApplication) abstractDao.find(SysApplication.class,
				new Long(id));
	}

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysApplication
	 */
	public SysApplication findByName(String name) {
		SysApplication bean = null;
		Object[] values = new Object[] { name };
		String query = "from SysApplication a where a.name=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysApplication) list.get(0);
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
	public PageResult getApplicationList(int parent, int pageNo, int pageSize) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "select count(*) from SysApplication a where a.node.parent=?";
		logger.info(query);
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// �����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// ��ѯ�б�
		query = "from SysApplication a where a.node.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public List getApplicationList(int parent) {
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysApplication a where a.node.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡȫ���б�
	 * 
	 * @return List
	 */
	public List getApplicationList() {
		String query = "from SysApplication a order by a.sort desc";
		return abstractDao.getList(query, null, null);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @param operate
	 *            int ����
	 */
	public void sort(long parent, SysApplication bean, int operate) {
		if (bean == null)
			return;
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
	private void sortByPrevious(long parent, SysApplication bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ����ǰһ������
		String query = "from SysApplication a where a.node.parent=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * ����ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysApplication bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ���Һ�һ������
		String query = "from SysApplication a where a.node.parent=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * ��ȡ�û��ܷ��ʵ���ģ���б�
	 * 
	 * @param userId
	 *            int
	 * @param parent
	 *            int
	 * @return List
	 */
	public List getAccessAppList(long parent, SysUser user) {
		logger.info("parent:" + parent);
		long parentAppId = parent;
		SysApplication parentApp = findById(parent);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}

		logger.info("parent node:" + parentAppId);

		StringBuffer sb = new StringBuffer();
		Iterator iter = user.getApps().iterator();
		while (iter.hasNext()) {
			SysApplication bean = (SysApplication) iter.next();
			sb.append(bean.getId()).append(",");
		}
		sb.append(0);

		// ��������
		Object[] values = new Object[] { new Long(parentAppId) };
		String query = "select a from SysApplication a "
				+ "where a.node.parent=? and " + "a.id in (" + sb.toString()
				+ ") " + "order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡ�˵�
	 * 
	 * @param parent
	 * @param userId
	 * @return
	 */
	public String getMenu(long parent, SysUser user) {
		StringBuffer menu = new StringBuffer("");
		List list = getAccessAppList(parent, user);
		if (list == null || list.isEmpty()) {
			if (user.isSystemAdmin()) {
				list = getApplicationList((int) parent);
			}
		}
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				menu.append("<li>");
				menu.append("<a href=\"javascript:jump('");
				// System.out.println("ContextUtil.getContextPath():"+ContextUtil.getContextPath());
				if (bean.getUrl().startsWith("/")) {
					if (ApplicationContext.getContextPath() != null) {
						menu.append(ApplicationContext.getContextPath());
					}
				}
				menu.append(bean.getUrl());
				menu.append("',");
				menu.append(bean.getShowMenu());
				menu.append(");\">");
				menu.append(bean.getName()).append("</a>\n");

				List sonNode = getAccessAppList(bean.getId(), user);
				if (sonNode == null || sonNode.isEmpty()) {
					if (user.isSystemAdmin()) {
						sonNode = getApplicationList((int) bean.getId());
					}
				}
				if (sonNode != null && sonNode.size() > 0) {// ���Ӳ˵�
					menu.append("<ul>");
					menu.append(getMenu(bean.getId(), user));
					menu.append("</ul>");
				}
				menu.append("</li>").append("<li></li>\n");
			}
		}
		return menu.toString();
	}

	public JSONArray getUserMenu(long parent, String userId) {
		JSONArray array = new JSONArray();
		SysUser user = authorizeService.login(userId);
		if (user != null) {
			logger.debug("@user="+user.getName());
			List list = getAccessAppList(parent, user);
			if (list == null || list.isEmpty()) {
				if (user.isSystemAdmin()) {
					list = getApplicationList((int) parent);
				}
			}
			if (list != null && list.size() > 0) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					JSONObject item = new JSONObject();
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("url", bean.getUrl());

					List sonNode = getAccessAppList(bean.getId(), user);
					if (sonNode == null || sonNode.isEmpty()) {
						if (user.isSystemAdmin()) {
							sonNode = getApplicationList((int) bean.getId());
						}
					}
					if (sonNode != null && sonNode.size() > 0) {// ���Ӳ˵�
						JSONArray children = this.getUserMenu(bean.getId(),
								userId);
						item.put("children", children);
					}

					array.put(item);

				}
			}
		}
		return array;
	}
}
