package com.glaf.base.modules.sys.business;

import java.util.*;

import com.alibaba.druid.util.StringUtils;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysTreeService;

public class UpdateTreeBean {

	protected SysTreeService sysTreeService;

	protected String getTreeId(Map<Long, SysTree> dataMap, SysTree tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		SysTree parent = dataMap.get(parentId);
		if (parent != null && parent.getId() != 0) {
			if (StringUtils.isEmpty(parent.getTreeId())) {
				return getTreeId(dataMap, parent) + id + "|";
			}
			if (!parent.getTreeId().endsWith("|")) {
				parent.setTreeId(parent.getTreeId() + "|");
			}
			return parent.getTreeId() + id + "|";
		}
		return tree.getTreeId();
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	public void updateTreeIds() {
		SysTree root = sysTreeService.findById(1);
		if (root != null) {
			if (!StringUtils.equals(root.getTreeId(), "1|")) {
				root.setTreeId("1|");
				sysTreeService.update(root);
			}
			List<SysTree> trees = sysTreeService.getAllSysTreeList();
			if (trees != null && !trees.isEmpty()) {
				Map<Long, SysTree> dataMap = new java.util.concurrent.ConcurrentHashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					dataMap.put(tree.getId(), tree);
				}
				Map<Long, String> treeIdMap = new java.util.concurrent.ConcurrentHashMap<Long, String>();
				for (SysTree tree : trees) {
					if (StringUtils.isEmpty(tree.getTreeId())) {
						String treeId = this.getTreeId(dataMap, tree);
						if (treeId != null && treeId.endsWith("|")) {
							treeIdMap.put(tree.getId(), treeId);
						}
					}
				}
				sysTreeService.updateTreeIds(treeIdMap);
			}
		}
	}

}
