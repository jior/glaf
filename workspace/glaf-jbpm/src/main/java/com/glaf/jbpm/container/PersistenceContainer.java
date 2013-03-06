/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.jbpm.container;

import java.io.Serializable;
import java.util.Collection;

import org.jbpm.JbpmContext;

import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmPersistenceManager;

public class PersistenceContainer {

	public final static PersistenceContainer getContainer() {
		if (container == null) {
			container = new PersistenceContainer();
		}
		return container;
	}

	public static JbpmPersistenceManager getJbpmPersistenceManager() {
		return jbpmPersistenceManager;
	}

	public static void setJbpmPersistenceManager(
			JbpmPersistenceManager jbpmPersistenceManager) {
		PersistenceContainer.jbpmPersistenceManager = jbpmPersistenceManager;
	}

	private static PersistenceContainer container;

	private static JbpmPersistenceManager jbpmPersistenceManager;

	private PersistenceContainer() {
		jbpmPersistenceManager = new JbpmPersistenceManager();
	}

	/**
	 * 删除持久化对象
	 * 
	 * @param model
	 */
	public void delete(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.delete(jbpmContext, model);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 批量删除持久化对象
	 * 
	 * @param rows
	 */
	public void deleteAll(Collection<Object> rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.deleteAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 根据主键获取持久化对象
	 * 
	 * @param clazz
	 *            类名
	 * @param persistId
	 *            主键值
	 * @return
	 */
	public Object getPersistObject(Class<?> clazz,
			java.io.Serializable persistId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return jbpmPersistenceManager.getPersistObject(jbpmContext,
						clazz, persistId);
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void save(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.save(jbpmContext, model);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void saveAll(Collection<Object> rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.saveAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 更新持久化对象
	 * 
	 * @param model
	 */
	public void update(Serializable model) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.update(jbpmContext, model);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 批量更新持久化对象
	 * 
	 * @param rows
	 */
	public void updateAll(Collection<Object> rows) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmPersistenceManager.updateAll(jbpmContext, rows);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}