package com.glaf.base.modules.todo.dao;

import java.util.*;

public interface TodoDAO {

	void executeSQL(final String sql, final List values);

	void saveAll(final List rows);

	List getUserEntityList(final String actorId);

}
