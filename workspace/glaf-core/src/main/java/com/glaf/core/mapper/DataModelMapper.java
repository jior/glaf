package com.glaf.core.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaf.core.query.DataModelQuery;

@Component
public interface DataModelMapper {

	int getTableCountByDataModelQuery(DataModelQuery query);

	List<Map<String, Object>> getTableDataByDataModelQuery(DataModelQuery query);

}
