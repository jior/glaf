package com.glaf.base.modules.sys.service.mybatis;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.mapper.DictoryDefinitionMapper;
import com.glaf.base.modules.sys.model.DictoryDefinition;
import com.glaf.base.modules.sys.query.DictoryDefinitionQuery;
import com.glaf.base.modules.sys.service.DictoryDefinitionService;
import com.glaf.base.modules.sys.util.DictoryDefinitionJsonFactory;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("dictoryDefinitionService")
@Transactional(readOnly = true)
public class DictoryDefinitionServiceImpl implements DictoryDefinitionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DictoryDefinitionMapper dictoryDefinitionMapper;

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	public DictoryDefinitionServiceImpl() {

	}

	public int count(DictoryDefinitionQuery query) {
		return dictoryDefinitionMapper.getDictoryDefinitionCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dictoryDefinitionMapper.deleteDictoryDefinitionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			DictoryDefinitionQuery query = new DictoryDefinitionQuery();
			query.rowIds(rowIds);
			dictoryDefinitionMapper.deleteDictoryDefinitions(query);
		}
	}

	public DictoryDefinition getDictoryDefinition(Long id) {
		if (id == null) {
			return null;
		}
		String cacheKey = "sys_dict_def_" + id;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject json = JSON.parseObject(text);
			return DictoryDefinitionJsonFactory.jsonToObject(json);
		}
		DictoryDefinition dictoryDefinition = dictoryDefinitionMapper
				.getDictoryDefinitionById(id);
		if (dictoryDefinition != null
				&& SystemConfig.getBoolean("use_query_cache")) {
			JSONObject json = dictoryDefinition.toJsonObject();
			CacheFactory.put(cacheKey, json.toJSONString());
		}
		return dictoryDefinition;
	}

	public List<DictoryDefinition> getDictoryDefinitions(Long nodeId,
			String target) {

		String cacheKey = "sys_dict_def_" + nodeId + "_" + target;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONArray array = JSON.parseArray(text);
			return DictoryDefinitionJsonFactory.arrayToList(array);
		}

		DictoryDefinitionQuery query = new DictoryDefinitionQuery();
		query.nodeId(nodeId);
		query.target(target);
		List<DictoryDefinition> list = dictoryDefinitionMapper
				.getDictoryDefinitions(query);
		if (list != null && !list.isEmpty()) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONArray array = DictoryDefinitionJsonFactory
						.listToArray(list);
				CacheFactory.put(cacheKey, array.toJSONString());
			}
		}

		return list;
	}

	public int getDictoryDefinitionCountByQueryCriteria(
			DictoryDefinitionQuery query) {
		return dictoryDefinitionMapper.getDictoryDefinitionCount(query);
	}

	public List<DictoryDefinition> getDictoryDefinitionsByQueryCriteria(
			int start, int pageSize, DictoryDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DictoryDefinition> rows = sqlSessionTemplate.selectList(
				"getDictoryDefinitions", query, rowBounds);
		return rows;
	}

	public List<DictoryDefinition> list(DictoryDefinitionQuery query) {
		List<DictoryDefinition> list = dictoryDefinitionMapper
				.getDictoryDefinitions(query);
		return list;
	}

	@Transactional
	public void save(DictoryDefinition dictoryDefinition) {
		if (dictoryDefinition.getId() == 0) {
			dictoryDefinition.setId(idGenerator.nextId());
			// dictoryDefinition.setCreateDate(new Date());
			dictoryDefinitionMapper.insertDictoryDefinition(dictoryDefinition);
		} else {
			dictoryDefinitionMapper.updateDictoryDefinition(dictoryDefinition);
			String cacheKey = "sys_dict_def_" + dictoryDefinition.getId();
			CacheFactory.remove(cacheKey);
		}
	}

	@javax.annotation.Resource
	public void setDictoryDefinitionMapper(
			DictoryDefinitionMapper dictoryDefinitionMapper) {
		this.dictoryDefinitionMapper = dictoryDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public void saveAll(Long nodeId, String target,
			List<DictoryDefinition> dictoryDefinitions) {
		DictoryDefinitionQuery query = new DictoryDefinitionQuery();
		query.nodeId(nodeId);
		query.target(target);
		List<DictoryDefinition> list = dictoryDefinitionMapper
				.getDictoryDefinitions(query);
		if (list != null && !list.isEmpty()) {
			for (DictoryDefinition m : list) {
				this.deleteById(m.getId());
				String cacheKey = "sys_dict_def_" + m.getId();
				CacheFactory.remove(cacheKey);
			}
		}

		if (dictoryDefinitions != null && !dictoryDefinitions.isEmpty()) {
			if (dictoryDefinitions != null && !dictoryDefinitions.isEmpty()) {
				for (DictoryDefinition m : dictoryDefinitions) {
					m.setId(idGenerator.nextId());
					m.setNodeId(nodeId);
					m.setTarget(target);
					dictoryDefinitionMapper.insertDictoryDefinition(m);
				}
			}
		}
		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "sys_dict_def_" + nodeId + "_" + target;
			CacheFactory.remove(cacheKey);
		}
	}

}
