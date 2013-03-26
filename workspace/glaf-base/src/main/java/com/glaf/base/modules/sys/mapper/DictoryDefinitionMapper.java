package com.glaf.base.modules.sys.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Component
public interface DictoryDefinitionMapper {

	void deleteDictoryDefinitions(DictoryDefinitionQuery query);

	void deleteDictoryDefinitionById(Long id);

	DictoryDefinition getDictoryDefinitionById(Long id);

	int getDictoryDefinitionCount(DictoryDefinitionQuery query);

	List<DictoryDefinition> getDictoryDefinitions(DictoryDefinitionQuery query);

	void insertDictoryDefinition(DictoryDefinition model);

	void updateDictoryDefinition(DictoryDefinition model);

}
