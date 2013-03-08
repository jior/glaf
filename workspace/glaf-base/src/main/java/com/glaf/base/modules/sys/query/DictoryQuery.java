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
	protected List<Long> rowIds;
	protected Long typeId;
	protected Long typeIdGreaterThanOrEqual;
	protected Long typeIdLessThanOrEqual;
	protected List<Long> typeIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String desc;
	protected String descLike;
	protected List<String> descs;
	protected String code;
	protected String codeLike;
	protected List<String> codes;

	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;

	protected Integer blocked;
	protected Integer blockedGreaterThanOrEqual;
	protected Integer blockedLessThanOrEqual;
	protected List<Integer> blockeds;
	protected String ext1;
	protected String ext1Like;
	protected List<String> ext1s;
	protected String ext2;
	protected String ext2Like;
	protected List<String> ext2s;
	protected String ext3;
	protected String ext3Like;
	protected List<String> ext3s;
	protected String ext4;
	protected String ext4Like;
	protected List<String> ext4s;
	protected Date ext5;
	protected Date ext5GreaterThanOrEqual;
	protected Date ext5LessThanOrEqual;
	protected List<Date> ext5s;
	protected Date ext6;
	protected Date ext6GreaterThanOrEqual;
	protected Date ext6LessThanOrEqual;
	protected List<Date> ext6s;

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

	public DictoryQuery ext1(String ext1) {
		if (ext1 == null) {
			throw new RuntimeException("ext1 is null");
		}
		this.ext1 = ext1;
		return this;
	}

	public DictoryQuery ext1Like(String ext1Like) {
		if (ext1Like == null) {
			throw new RuntimeException("ext1 is null");
		}
		this.ext1Like = ext1Like;
		return this;
	}

	public DictoryQuery ext1s(List<String> ext1s) {
		if (ext1s == null) {
			throw new RuntimeException("ext1s is empty ");
		}
		this.ext1s = ext1s;
		return this;
	}

	public DictoryQuery ext2(String ext2) {
		if (ext2 == null) {
			throw new RuntimeException("ext2 is null");
		}
		this.ext2 = ext2;
		return this;
	}

	public DictoryQuery ext2Like(String ext2Like) {
		if (ext2Like == null) {
			throw new RuntimeException("ext2 is null");
		}
		this.ext2Like = ext2Like;
		return this;
	}

	public DictoryQuery ext2s(List<String> ext2s) {
		if (ext2s == null) {
			throw new RuntimeException("ext2s is empty ");
		}
		this.ext2s = ext2s;
		return this;
	}

	public DictoryQuery ext3(String ext3) {
		if (ext3 == null) {
			throw new RuntimeException("ext3 is null");
		}
		this.ext3 = ext3;
		return this;
	}

	public DictoryQuery ext3Like(String ext3Like) {
		if (ext3Like == null) {
			throw new RuntimeException("ext3 is null");
		}
		this.ext3Like = ext3Like;
		return this;
	}

	public DictoryQuery ext3s(List<String> ext3s) {
		if (ext3s == null) {
			throw new RuntimeException("ext3s is empty ");
		}
		this.ext3s = ext3s;
		return this;
	}

	public DictoryQuery ext4(String ext4) {
		if (ext4 == null) {
			throw new RuntimeException("ext4 is null");
		}
		this.ext4 = ext4;
		return this;
	}

	public DictoryQuery ext4Like(String ext4Like) {
		if (ext4Like == null) {
			throw new RuntimeException("ext4 is null");
		}
		this.ext4Like = ext4Like;
		return this;
	}

	public DictoryQuery ext4s(List<String> ext4s) {
		if (ext4s == null) {
			throw new RuntimeException("ext4s is empty ");
		}
		this.ext4s = ext4s;
		return this;
	}

	public DictoryQuery ext5(Date ext5) {
		if (ext5 == null) {
			throw new RuntimeException("ext5 is null");
		}
		this.ext5 = ext5;
		return this;
	}

	public DictoryQuery ext5GreaterThanOrEqual(Date ext5GreaterThanOrEqual) {
		if (ext5GreaterThanOrEqual == null) {
			throw new RuntimeException("ext5 is null");
		}
		this.ext5GreaterThanOrEqual = ext5GreaterThanOrEqual;
		return this;
	}

	public DictoryQuery ext5LessThanOrEqual(Date ext5LessThanOrEqual) {
		if (ext5LessThanOrEqual == null) {
			throw new RuntimeException("ext5 is null");
		}
		this.ext5LessThanOrEqual = ext5LessThanOrEqual;
		return this;
	}

	public DictoryQuery ext5s(List<Date> ext5s) {
		if (ext5s == null) {
			throw new RuntimeException("ext5s is empty ");
		}
		this.ext5s = ext5s;
		return this;
	}

	public DictoryQuery ext6(Date ext6) {
		if (ext6 == null) {
			throw new RuntimeException("ext6 is null");
		}
		this.ext6 = ext6;
		return this;
	}

	public DictoryQuery ext6GreaterThanOrEqual(Date ext6GreaterThanOrEqual) {
		if (ext6GreaterThanOrEqual == null) {
			throw new RuntimeException("ext6 is null");
		}
		this.ext6GreaterThanOrEqual = ext6GreaterThanOrEqual;
		return this;
	}

	public DictoryQuery ext6LessThanOrEqual(Date ext6LessThanOrEqual) {
		if (ext6LessThanOrEqual == null) {
			throw new RuntimeException("ext6 is null");
		}
		this.ext6LessThanOrEqual = ext6LessThanOrEqual;
		return this;
	}

	public DictoryQuery ext6s(List<Date> ext6s) {
		if (ext6s == null) {
			throw new RuntimeException("ext6s is empty ");
		}
		this.ext6s = ext6s;
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

	public String getExt1() {
		return ext1;
	}

	public String getExt1Like() {
		if (ext1Like != null && ext1Like.trim().length() > 0) {
			if (!ext1Like.startsWith("%")) {
				ext1Like = "%" + ext1Like;
			}
			if (!ext1Like.endsWith("%")) {
				ext1Like = ext1Like + "%";
			}
		}
		return ext1Like;
	}

	public List<String> getExt1s() {
		return ext1s;
	}

	public String getExt2() {
		return ext2;
	}

	public String getExt2Like() {
		if (ext2Like != null && ext2Like.trim().length() > 0) {
			if (!ext2Like.startsWith("%")) {
				ext2Like = "%" + ext2Like;
			}
			if (!ext2Like.endsWith("%")) {
				ext2Like = ext2Like + "%";
			}
		}
		return ext2Like;
	}

	public List<String> getExt2s() {
		return ext2s;
	}

	public String getExt3() {
		return ext3;
	}

	public String getExt3Like() {
		if (ext3Like != null && ext3Like.trim().length() > 0) {
			if (!ext3Like.startsWith("%")) {
				ext3Like = "%" + ext3Like;
			}
			if (!ext3Like.endsWith("%")) {
				ext3Like = ext3Like + "%";
			}
		}
		return ext3Like;
	}

	public List<String> getExt3s() {
		return ext3s;
	}

	public String getExt4() {
		return ext4;
	}

	public String getExt4Like() {
		if (ext4Like != null && ext4Like.trim().length() > 0) {
			if (!ext4Like.startsWith("%")) {
				ext4Like = "%" + ext4Like;
			}
			if (!ext4Like.endsWith("%")) {
				ext4Like = ext4Like + "%";
			}
		}
		return ext4Like;
	}

	public List<String> getExt4s() {
		return ext4s;
	}

	public Date getExt5() {
		return ext5;
	}

	public Date getExt5GreaterThanOrEqual() {
		return ext5GreaterThanOrEqual;
	}

	public Date getExt5LessThanOrEqual() {
		return ext5LessThanOrEqual;
	}

	public List<Date> getExt5s() {
		return ext5s;
	}

	public Date getExt6() {
		return ext6;
	}

	public Date getExt6GreaterThanOrEqual() {
		return ext6GreaterThanOrEqual;
	}

	public Date getExt6LessThanOrEqual() {
		return ext6LessThanOrEqual;
	}

	public List<Date> getExt6s() {
		return ext6s;
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

	public Long getTypeId() {
		return typeId;
	}

	public Long getTypeIdGreaterThanOrEqual() {
		return typeIdGreaterThanOrEqual;
	}

	public Long getTypeIdLessThanOrEqual() {
		return typeIdLessThanOrEqual;
	}

	public List<Long> getTypeIds() {
		return typeIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("typeId", "TYPEID");
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

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt1Like(String ext1Like) {
		this.ext1Like = ext1Like;
	}

	public void setExt1s(List<String> ext1s) {
		this.ext1s = ext1s;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt2Like(String ext2Like) {
		this.ext2Like = ext2Like;
	}

	public void setExt2s(List<String> ext2s) {
		this.ext2s = ext2s;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt3Like(String ext3Like) {
		this.ext3Like = ext3Like;
	}

	public void setExt3s(List<String> ext3s) {
		this.ext3s = ext3s;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt4Like(String ext4Like) {
		this.ext4Like = ext4Like;
	}

	public void setExt4s(List<String> ext4s) {
		this.ext4s = ext4s;
	}

	public void setExt5(Date ext5) {
		this.ext5 = ext5;
	}

	public void setExt5GreaterThanOrEqual(Date ext5GreaterThanOrEqual) {
		this.ext5GreaterThanOrEqual = ext5GreaterThanOrEqual;
	}

	public void setExt5LessThanOrEqual(Date ext5LessThanOrEqual) {
		this.ext5LessThanOrEqual = ext5LessThanOrEqual;
	}

	public void setExt5s(List<Date> ext5s) {
		this.ext5s = ext5s;
	}

	public void setExt6(Date ext6) {
		this.ext6 = ext6;
	}

	public void setExt6GreaterThanOrEqual(Date ext6GreaterThanOrEqual) {
		this.ext6GreaterThanOrEqual = ext6GreaterThanOrEqual;
	}

	public void setExt6LessThanOrEqual(Date ext6LessThanOrEqual) {
		this.ext6LessThanOrEqual = ext6LessThanOrEqual;
	}

	public void setExt6s(List<Date> ext6s) {
		this.ext6s = ext6s;
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

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public void setTypeIdGreaterThanOrEqual(Long typeIdGreaterThanOrEqual) {
		this.typeIdGreaterThanOrEqual = typeIdGreaterThanOrEqual;
	}

	public void setTypeIdLessThanOrEqual(Long typeIdLessThanOrEqual) {
		this.typeIdLessThanOrEqual = typeIdLessThanOrEqual;
	}

	public void setTypeIds(List<Long> typeIds) {
		this.typeIds = typeIds;
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

	public DictoryQuery typeId(Long typeId) {
		if (typeId == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeId = typeId;
		return this;
	}

	public DictoryQuery typeIdGreaterThanOrEqual(Long typeIdGreaterThanOrEqual) {
		if (typeIdGreaterThanOrEqual == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdGreaterThanOrEqual = typeIdGreaterThanOrEqual;
		return this;
	}

	public DictoryQuery typeIdLessThanOrEqual(Long typeIdLessThanOrEqual) {
		if (typeIdLessThanOrEqual == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdLessThanOrEqual = typeIdLessThanOrEqual;
		return this;
	}

	public DictoryQuery typeIds(List<Long> typeIds) {
		if (typeIds == null) {
			throw new RuntimeException("typeIds is empty ");
		}
		this.typeIds = typeIds;
		return this;
	}

}