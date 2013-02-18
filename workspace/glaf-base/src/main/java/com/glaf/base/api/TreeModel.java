package com.glaf.base.api;

public interface TreeModel {

	String getCode();

	long getId();

	String getName();

	long getParentId();

	int getSort();

	void setCode(String code);

	void setId(long id);

	void setName(String name);

	void setParentId(long parentId);

	void setSort(int sort);

}
