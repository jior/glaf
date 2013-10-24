package ${packageName}.view.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.glaf.base.context.*;
import com.glaf.base.utils.*;
import ${packageName}.domain.${entityName};
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.*;

public class ${entityName}DataModel extends LazyDataModel<${entityName}> {

	private static final long serialVersionUID = 0L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected transient ${entityName}Service ${modelName}Service;

	protected ${entityName}Query ${modelName}Query;

	protected int total;

	public ${entityName}DataModel(${entityName}Service ${modelName}Service, ${entityName}Query ${modelName}Query) {
		this.${modelName}Service = ${modelName}Service;
		this.${modelName}Query = ${modelName}Query;
		setPageResultSize(15);
		setRowCount(1);
	}

	public int getTotal() {
		return total;
	}

    /**
	 * 根据业务对象的主键获取业务对象
	 *
	 */
	@Override
	public ${entityName} getRowData(${idField.type} rowKey) {
		return ${modelName}Service.get${entityName}(rowKey);
	}

    /**
	 * 根据业务对象获取主键
	 *
	 */
	@Override
	public Object getRowKey(${entityName} model) {
		return model.get${idField.firstUpperName}();
	}

    /**
	 * 分页方法
	 * 提供数据库物理分页
	 */
	@Override
	public List<${entityName}> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		logger.debug("first:" + first);
		logger.debug("pageSize:" + pageSize);
		logger.debug("filters:" + filters);
		logger.debug("sortField:" + sortField);
		logger.debug("sortOrder:" + sortOrder);
		if (pageSize <= 0) {
			pageSize = 15;
		}
		 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(filters);
 		if (${modelName}Query == null) {
			${modelName}Query = new ${entityName}Query();
		}
		try{
		    Tools.populate(${modelName}Query, paramMap);
		} catch(Exception ex){
		    ex.printStackTrace();
		}
		 
		${modelName}Query.setSortColumn(sortField);
		//${modelName}Query.setSortOrder(sortOrder);

		if (sortOrder != null) {
			if (SortOrder.ASCENDING.equals(sortOrder)) {
				${modelName}Query.setSortOrder(" asc ");
			} else if (SortOrder.DESCENDING.equals(sortOrder)) {
				${modelName}Query.setSortOrder(" desc ");
			}
		}

		if(${modelName}Service == null){
		    ${modelName}Service = ContextFactory.getBean("${modelName}Service");
		}

		logger.debug("---------------------total----------------------");
		total = ${modelName}Service.get${entityName}CountByQueryCriteria(${modelName}Query);

		logger.debug("row count:" + total);

		logger.debug("----------------------list---------------------");
		List<${entityName}> rows = new ArrayList<${entityName}>();
		if (total > 0) {
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(first,
					pageSize, ${modelName}Query);
 			for (${entityName} ${modelName} : list) {
				rows.add(${modelName});
			}
		}


		if (getPageResultSize() == 0) {
		    setPageResultSize(15);
		}

		setRowCount(total);

		return rows;
	}

}
