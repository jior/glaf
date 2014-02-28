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

package com.glaf.core.tree.component;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TreeRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(TreeRepository.class);

	protected Map<String, TreeComponent> trees = new LinkedHashMap<String, TreeComponent>();

	/**
	 * Adds a new component.
	 * 
	 * @param component
	 *            The tree component to add.
	 */
	public void addTree(TreeComponent component) {
		if (trees.containsKey(component.getId())) {
			if (log.isDebugEnabled()) {
				log.warn("Tree '" + component.getId()
						+ "' already exists in repository");
			}
			List<TreeComponent> children = (getTree(component.getId()))
					.getComponents();
			if (children != null && component.getComponents() != null) {
				for (Iterator<TreeComponent> it = children.iterator(); it
						.hasNext();) {
					TreeComponent child = (TreeComponent) it.next();
					component.addTreeComponent(child);
				}
			}
		}
		trees.put(component.getId(), component);
	}

	/**
	 * Convenience method for dynamic trees - returns the top-level trees only.
	 */
	public List<TreeComponent> getTopTrees() {
		List<TreeComponent> topTrees = new java.util.concurrent.CopyOnWriteArrayList<TreeComponent>();
		if (trees == null) {
			log.warn("No trees found in repository!");
			return topTrees;
		}

		for (Iterator<?> it = trees.keySet().iterator(); it.hasNext();) {
			String id = (String) it.next();
			TreeComponent component = getTree(id);
			if (component.getParent() == null) {
				topTrees.add(component);
			}
		}
		return topTrees;
	}

	/**
	 * Method getTopTreesAsArray. Get menus as array rather than a List
	 * 
	 * @return TreeComponent[]
	 */
	public TreeComponent[] getTopTreesAsArray() {
		List<TreeComponent> menuList = this.getTopTrees();
		TreeComponent[] menus = new TreeComponent[menuList.size()];
		for (int i = 0, len = menuList.size(); i < len; i++) {
			menus[i] = (TreeComponent) menuList.get(i);
		}

		return menus;
	}

	public TreeComponent getTree(String id) {
		return (TreeComponent) trees.get(id);
	}

	public Set<String> getTreeIds() {
		return trees.keySet();
	}

	/**
	 * Allows easy removal of all menus, suggested use for users wanting to
	 * reload menus without having to perform a complete reload of the
	 * TreeRepository
	 */
	public void removeAllTrees() {
		trees.clear();
	}

	/**
	 * Allows easy removal of a menu by its name.
	 * 
	 * @param id
	 */
	public void removeTree(String id) {
		trees.remove(id);
	}

}