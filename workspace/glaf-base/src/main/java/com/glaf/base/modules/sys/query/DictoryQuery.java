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

package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DictoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Integer blocked;
	protected Integer blockedGreaterThanOrEqual;

	protected Integer blockedLessThanOrEqual;
	protected List<Integer> blockeds;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String desc;
	protected String descLike;
	protected List<String> descs;
	protected String name;

	protected String nameLike;
	protected List<String> names;
	protected Long nodeId;
	protected List<Long> rowIds;

	protected Integer sortGreaterThan;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortLessThan;
	protected Integer sortLessThanOrEqual;

	public DictoryQuery() {

	}

	public DictoryQuery blocked(Integer blocked) {
		if (blocked == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blocked = blocked;
		return this;
	}

	public DictoryQuery blockedGreaterThanOrEqual(
			Integer blockedGreaterThanOrEqual) {
		if (blockedGreaterThanOrEqual == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blockedGreaterThanOrEqual = blockedGreaterThanOrEqual;
		return this;
	}

	public DictoryQuery blockedLessThanOrEqual(Integer blockedLessThanOrEqual) {
		if (blockedLessThanOrEqual == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blockedLessThanOrEqual = blockedLessThanOrEqual;
		return this;
	}

	public DictoryQuery blockeds(List<Integer> blockeds) {
		if (blockeds == null) {
			throw new RuntimeException("blockeds is empty ");
		}
		this.blockeds = blockeds;
		return this;
	}

	public DictoryQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DictoryQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DictoryQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public DictoryQuery desc(String desc) {
		if (desc == null) {
			throw new RuntimeException("desc is null");
		}
		this.desc = desc;
		return this;
	}

	public DictoryQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public DictoryQuery descs(List<String> descs) {
		if (descs == null) {
			throw new RuntimeException("descs is empty ");
		}
		this.descs = descs;
		return this;
	}

	public Integer getBlocked() {
		return blocked;
	}

	public Integer getBlockedGreaterThanOrEqual() {
		return blockedGreaterThanOrEqual;
	}

	public Integer getBlockedLessThanOrEqual() {
		return blockedLessThanOrEqual;
	}

	public List<Integer> getBlockeds() {
		return blockeds;
	}

	public String getCode() {
		return code;
	}

	public String getCodeLike() {
		if (codeLike != null && codeLike.trim().length() > 0) {
			if (!codeLike.startsWith("%")) {
				codeLike = "%" + codeLike;
			}
			if (!codeLike.endsWith("%")) {
				codeLike = codeLike + "%";
			}
		}
		return codeLike;
	}

	public List<String> getCodes() {
		return codes;
	}

	public String getDesc() {
		return desc;
	}

	public String getDescLike() {
		if (descLike != null && descLike.trim().length() > 0) {
			if (!descLike.startsWith("%")) {
				descLike = "%" + descLike;
			}
			if (!descLike.endsWith("%")) {
				descLike = descLike + "%";
			}
		}
		return descLike;
	}

	public List<String> getDescs() {
		return descs;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("typeId".equals(sortColumn)) {
				orderBy = "E.TYPEID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("desc".equals(sortColumn)) {
				orderBy = "E.DICTDESC" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("blocked".equals(sortColumn)) {
				orderBy = "E.BLOCKED" + a_x;
			}

			if ("ext1".equals(sortColumn)) {
				orderBy = "E.EXT1" + a_x;
			}

			if ("ext2".equals(sortColumn)) {
				orderBy = "E.EXT2" + a_x;
			}

			if ("ext3".equals(sortColumn)) {
				orderBy = "E.EXT3" + a_x;
			}

			if ("ext4".equals(sortColumn)) {
				orderBy = "E.EXT4" + a_x;
			}

			if ("ext5".equals(sortColumn)) {
				orderBy = "E.EXT5" + a_x;
			}

			if ("ext6".equals(sortColumn)) {
				orderBy = "E.EXT6" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getSortGreaterThan() {
		return sortGreaterThan;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThan() {
		return sortLessThan;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("nodeId", "TYPEID");
		addColumn("name", "NAME");
		addColumn("desc", "DICTDESC");
		addColumn("code", "CODE");
		addColumn("sort", "SORT");
		addColumn("blocked", "BLOCKED");
		addColumn("ext1", "EXT1");
		addColumn("ext2", "EXT2");
		addColumn("ext3", "EXT3");
		addColumn("ext4", "EXT4");
		addColumn("ext5", "EXT5");
		addColumn("ext6", "EXT6");
	}

	public DictoryQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DictoryQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DictoryQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DictoryQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
	}

	public void setBlockedGreaterThanOrEqual(Integer blockedGreaterThanOrEqual) {
		this.blockedGreaterThanOrEqual = blockedGreaterThanOrEqual;
	}

	public void setBlockedLessThanOrEqual(Integer blockedLessThanOrEqual) {
		this.blockedLessThanOrEqual = blockedLessThanOrEqual;
	}

	public void setBlockeds(List<Integer> blockeds) {
		this.blockeds = blockeds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setDescs(List<String> descs) {
		this.descs = descs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setSortGreaterThan(Integer sortGreaterThan) {
		this.sortGreaterThan = sortGreaterThan;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThan(Integer sortLessThan) {
		this.sortLessThan = sortLessThan;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public DictoryQuery sortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public DictoryQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

}