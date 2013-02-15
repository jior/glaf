/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.persistence.Executor;

public class PersistenceContainer {
	private final static Log logger = LogFactory
			.getLog(PersistenceContainer.class);

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private static PersistenceContainer container;

 

	private static ActorManager actorManager;

	private static PersistenceManager persistenceManager;

	private PersistenceContainer() {
	 
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
		persistenceManager = (PersistenceManager) JbpmContextFactory
				.getBean("persistenceManager");
	}

	public final static PersistenceContainer getContainer() {
		if (container == null) {
			container = new PersistenceContainer();
		}
		return container;
	}

	/**
	 * ��ȡ�û�Map������key���û��ʺţ�value��org.jpage.actor.User����
	 * 
	 * @return
	 */
	public Map getUserMap() {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			return actorManager.getUserMap(jbpmContext);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	public List getActors(Map params) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			return actorManager.getActors(jbpmContext, params);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	 

	/**
	 * ɾ���־û�����
	 * 
	 * @param model
	 */
	public void delete(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.delete(jbpmContext, model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ����ɾ���־û�����
	 * 
	 * @param rows
	 */
	public void deleteAll(Collection rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.deleteAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * �ϲ��־û�����
	 * 
	 * @param model
	 */
	public void merge(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.merge(jbpmContext, model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * �����ϲ��־û�����
	 * 
	 * @param rows
	 */
	public void mergeAll(Collection rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.mergeAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ����־û�����
	 * 
	 * @param model
	 */
	public void persist(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.persist(jbpmContext, model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��������־û�����
	 * 
	 * @param rows
	 */
	public void persistAll(Collection rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.persistAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ����־û�����
	 * 
	 * @param model
	 */
	public void save(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.save(jbpmContext, model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��������־û�����
	 * 
	 * @param rows
	 */
	public void saveAll(Collection rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.saveAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���³־û�����
	 * 
	 * @param model
	 */
	public void update(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.update(jbpmContext, model);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * �������³־û�����
	 * 
	 * @param rows
	 */
	public void updateAll(Collection rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				persistenceManager.updateAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ����������ȡ�־û�����
	 * 
	 * @param clazz
	 *            ����
	 * @param persistId
	 *            ����ֵ
	 * @return
	 */
	public Object getPersistObject(Class clazz, java.io.Serializable persistId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return persistenceManager.getPersistObject(jbpmContext, clazz,
						persistId);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��ѯ
	 * 
	 * @param currPageNo
	 * @param maxResults
	 * @param queryExecutor
	 * @return
	 */
	public List query(int currPageNo, int maxResults, Executor queryExecutor) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return persistenceManager.query(jbpmContext, currPageNo,
						maxResults, queryExecutor);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(int currPageNo, int pageSize, Executor countExecutor,
			Executor queryExecutor) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return persistenceManager.getPage(jbpmContext, currPageNo,
						pageSize, countExecutor, queryExecutor);
			}
			return Page.EMPTY_PAGE;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��ѯ���ݣ�ͨ��iBATIS���ò�ѯ����
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	public Object queryForObject(String statementId, Object parameterObject) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return MutableSQLMapContainer.getContainer().queryForObject(
						jbpmContext, statementId, parameterObject);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��ѯ���ݣ�ͨ��iBATIS���ò�ѯ����
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	public java.util.List query(String statementId, Object parameterObject) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return MutableSQLMapContainer.getContainer().query(jbpmContext,
						statementId, parameterObject);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}
 

	/**
	 * ִ����������ͨ��iBATISִ��
	 * 
	 * @param rows
	 *            ����������󼯺� {@see org.jpage.jbpm.ibatis.SQLMap}
	 * @return
	 */
	public int executeBatch(java.util.List rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return MutableSQLMapContainer.getContainer().executeBatch(
						jbpmContext, rows);
			}
			return -1;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}
