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

import org.apache.commons.collections.map.LinkedMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;

public class TreeRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MENU_REPOSITORY_KEY = "com.glaf.core.tree.component.TREE_REPOSITORY";
	private static Log log = LogFactory.getLog(TreeRepository.class);

	// ~ Instance fields
	// ========================================================

	protected String config = null;
	protected String name = null;

	protected LinkedMap trees = new LinkedMap();

	/**
	 * Adds a new menu. This is called when parsing the menu xml definition.
	 * 
	 * @param menu
	 *            The menu component to add.
	 */
	public void addTree(TreeComponent menu) {
		if (trees.containsKey(menu.getId())) {
			if (log.isDebugEnabled()) {
				log.warn("Tree '" + menu.getId()
						+ "' already exists in repository");
			}
			List<TreeComponent> children = (getTree(menu.getId()))
					.getComponents();
			if (children != null && menu.getComponents() != null) {
				for (Iterator<TreeComponent> it = children.iterator(); it
						.hasNext();) {
					TreeComponent child = (TreeComponent) it.next();
					menu.addTreeComponent(child);
				}
			}
		}
		trees.put(menu.getId(), menu);
	}

	public String getName() {
		return name;
	}

	/**
	 * Convenience method for dynamic trees - returns the top-level trees only.
	 */
	public List<TreeComponent> getTopTrees() {
		List<TreeComponent> topTrees = new ArrayList<TreeComponent>();
		if (trees == null) {
			log.warn("No trees found in repository!");
			return topTrees;
		}

		for (Iterator<?> it = trees.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			TreeComponent menu = getTree(name);
			if (menu.getParent() == null) {
				topTrees.add(menu);
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
		for (int a = 0; a < menuList.size(); a++) {
			menus[a] = (TreeComponent) menuList.get(a);
		}

		return menus;
	}

	/**
	 * Get a List of all the top menus' names
	 * 
	 * @return List
	 */
	public List<String> getTopTreeIds() {
		List<TreeComponent> menus = this.getTopTrees();
		List<String> names = new ArrayList<String>();
		for (Iterator<TreeComponent> iterator = menus.iterator(); iterator
				.hasNext();) {
			TreeComponent menu = (TreeComponent) iterator.next();
			names.add(menu.getId());
		}
		return names;
	}

	public TreeComponent getTree(String menuName) {
		return (TreeComponent) trees.get(menuName);
	}

	/**
	 * Method getTree. Get a subTree beneath a root or parent menu. Will
	 * drill-down as deep as requested
	 * 
	 * @param menuName
	 *            - e.g grandParent.parent.menu
	 * @param delimiter
	 *            - e.g. '.'
	 * @return TreeComponent
	 */
	public TreeComponent getTree(String menuName, String delimiter) {
		TreeComponent parent = null;
		StringTokenizer st = new StringTokenizer(menuName, delimiter);
		boolean firstTree = true;

		while (st.hasMoreTokens()) {
			if (firstTree) {
				parent = this.getTree(st.nextToken());
				firstTree = false;
			} else {
				TreeComponent child = null;
				String name = st.nextToken();
				for (int a = 0; a < parent.getComponents().size(); a++) {
					if (name.equals(((TreeComponent) parent.getComponents()
							.get(a)).getId())) {
						child = (TreeComponent) parent.getComponents().get(a);
						a = parent.getComponents().size();
					}
				}
				if (child != null) {
					parent = child;
				} else {
					parent = null;
					break;
				}
			}
		}

		return parent;
	}

	/**
	 * Method getTreeDepth. Get the depth of the deepest sub-menu throughout all
	 * menus held in the repository
	 * 
	 * @return int. If no menus return -1.
	 */
	public int getTreeDepth() {
		int currentDepth = 0;

		List<TreeComponent> topTrees = this.getTopTrees();

		if (topTrees == null)
			return -1;
		for (Iterator<TreeComponent> menu = topTrees.iterator(); menu.hasNext();) {
			int depth = ((TreeComponent) menu.next()).getTreeDepth();
			if (currentDepth < depth)
				currentDepth = depth;
		}
		return currentDepth;
	}

	/**
	 * Method getTreeDepth. Get the depth of the deepest sub-menu within the
	 * requested top menu
	 * 
	 * @param menuName
	 *            - name of the top menu to check the menu depth
	 * @return int. If no menuName found return -1
	 */
	public int getTreeDepth(String menuName) {
		TreeComponent menu = this.getTree(menuName);
		if (menu == null)
			return -1;
		if (menu.getTreeComponents() == null)
			return 1;
		return menu.getTreeDepth();
	}

	// ~ Methods
	// ================================================================
	public Set<?> getTreeNames() {
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
	 * @param name
	 */
	public void removeTree(String name) {
		trees.remove(name);
	}

	public void setName(String name) {
		this.name = name;
	}
}