package ${packageName}.view.jsf;

import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.glaf.base.context.*;
import com.glaf.base.utils.FacesUtil;
import ${packageName}.domain.${entityName};
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.*;

@Component("${modelName}Bean")
@Scope("view")
public class ${entityName}Bean implements Serializable  {
        private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected transient ${entityName}Service ${modelName}Service;

	protected LazyDataModel<${entityName}> lazy${entityName}s;

	protected ${entityName}Query ${modelName}Query = new ${entityName}Query();

	protected ${entityName} selected${entityName};

	protected ${entityName}[] selected${entityName}s;

	protected ${entityName} ${modelName};

<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.dataCode?exists>

	protected SelectItem[] ${field.firstLowerName}Items;

         </#if>
    </#list>
</#if>
 
	public ${entityName}Bean() {

	}
 
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.dataCode?exists>

	public SelectItem[] get${field.firstUpperName}Items() {
		return ${field.firstLowerName}Items;
	}

         </#if>
    </#list>
</#if>

	public ${entityName}Query get${entityName}Query() {
		return ${modelName}Query;
	}

	public LazyDataModel<${entityName}> getLazy${entityName}s() {
	        if(lazy${entityName}s == null){
                     this.reload();
		}
		return lazy${entityName}s;
	}

	public ${entityName} getSelected${entityName}() {
		return selected${entityName};
	}

	public ${entityName}[] getSelected${entityName}s() {
		return selected${entityName}s;
	}

	public ${entityName} get${entityName}() {
		if (${modelName} == null) {
			${modelName} = new ${entityName}();
			logger.debug("create new ${modelName}");
		}
		return ${modelName};
	}

	public void set${entityName}(${entityName} ${modelName}) {
		this.${modelName} = ${modelName};
	}

	@PostConstruct
	public void onLoad() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		logger.debug("request params:" + params);
	}

	public void reload() {
		Map<String, String> paramMap = FacesUtil.getParameterMap();

 <#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' )>
        if (paramMap.get("${field.firstLowerName}") != null) {
			${modelName}Query.${field.firstLowerName}(FacesUtil.getInt(paramMap, "${field.firstLowerName}"));
		}

		if (paramMap.get("${field.firstLowerName}GreaterThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}GreaterThanOrEqual(FacesUtil.getInt(paramMap, "${field.firstLowerName}GreaterThanOrEqual"));
		}

		if (paramMap.get("${field.firstLowerName}LessThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}LessThanOrEqual(FacesUtil.getInt(paramMap, "${field.firstLowerName}LessThanOrEqual"));
		}

	</#if>
	<#if field.type?exists && ( field.type== 'Long' )>
        if (paramMap.get("${field.firstLowerName}") != null) {
			${modelName}Query.${field.firstLowerName}(FacesUtil.getLong(paramMap, "${field.firstLowerName}"));
		}

		if (paramMap.get("${field.firstLowerName}GreaterThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}GreaterThanOrEqual(FacesUtil.getLong(paramMap, "${field.firstLowerName}GreaterThanOrEqual"));
		}

		if (paramMap.get("${field.firstLowerName}LessThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}LessThanOrEqual(FacesUtil.getLong(paramMap, "${field.firstLowerName}LessThanOrEqual"));
		}

	</#if>
	<#if field.type?exists && ( field.type== 'Double' )>
        if (paramMap.get("${field.firstLowerName}") != null) {
			${modelName}Query.${field.firstLowerName}(FacesUtil.getDouble(paramMap, "${field.firstLowerName}"));
		}

		if (paramMap.get("${field.firstLowerName}GreaterThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}GreaterThanOrEqual(FacesUtil.getDouble(paramMap, "${field.firstLowerName}GreaterThanOrEqual"));
		}

		if (paramMap.get("${field.firstLowerName}LessThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}LessThanOrEqual(FacesUtil.getDouble(paramMap, "${field.firstLowerName}LessThanOrEqual"));
		}

	</#if>
	<#if field.type?exists && ( field.type== 'Date' )>
        if (paramMap.get("${field.firstLowerName}") != null) {
			${modelName}Query.${field.firstLowerName}(FacesUtil.getDate(paramMap, "${field.firstLowerName}"));
		}

		if (paramMap.get("${field.firstLowerName}GreaterThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}GreaterThanOrEqual(FacesUtil.getDate(paramMap, "${field.firstLowerName}GreaterThanOrEqual"));
		}

		if (paramMap.get("${field.firstLowerName}LessThanOrEqual") != null) {
			${modelName}Query.${field.firstLowerName}LessThanOrEqual(FacesUtil.getDate(paramMap, "${field.firstLowerName}LessThanOrEqual"));
		}

	</#if>
	<#if field.type?exists && ( field.type== 'String' )>
        if (paramMap.get("${field.firstLowerName}") != null) {
			${modelName}Query.${field.firstLowerName}(paramMap.get("${field.firstLowerName}"));
		}
		if (paramMap.get("${field.firstLowerName}Like") != null) {
			${modelName}Query.${field.firstLowerName}Like(paramMap.get("${field.firstLowerName}Like"));
		}
	</#if>
    </#list>
</#if>
	    lazy${entityName}s = new ${entityName}DataModel( get${entityName}Service(), ${modelName}Query);
    }

	public void reload(ValueChangeEvent event) {
	    lazy${entityName}s = new ${entityName}DataModel( get${entityName}Service(), ${modelName}Query);
    }

	public void remove() {
		if (selected${entityName} != null) {
			get${entityName}Service().deleteById(selected${entityName}.get${idField.firstUpperName}());
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "删除成功",
					"OK");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		this.reload();
	}

	public void removeAction(ActionEvent actionEvent) {
		logger.debug("remove ${modelName}....");
		if (selected${entityName} != null) {
			get${entityName}Service().deleteById(selected${entityName}.get${idField.firstUpperName}());
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "删除成功",
					"OK");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		this.reload();
	}

	public void removeManyAction(ActionEvent actionEvent) {
		logger.debug("remove ${modelName}s....");
		if (selected${entityName}s != null) {
			List<${idField.type}> ${idField.name}s = new ArrayList<${idField.type}>();
			for (${entityName} model : selected${entityName}s) {
				if (!${idField.name}s.contains(model.get${idField.firstUpperName}())) {
					${idField.name}s.add(model.get${idField.firstUpperName}());
				}
			}
			if (${idField.name}s.size() > 0) {
				logger.debug("remove ${modelName}s:" + ${idField.name}s);
				get${entityName}Service().deleteByIds(${idField.name}s);
				FacesMessage facesMessage = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"删除成功", "OK");
				FacesContext.getCurrentInstance()
						.addMessage(null, facesMessage);
			}
		}
		this.reload();
	}

	public void save() {
		if (${modelName} != null) {
			//${modelName}.setCreateBy(FacesUtil.getActorId());
			get${entityName}Service().save(${modelName});
			logger.debug("save ${modelName}:" + ${modelName});
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "保存成功",
					"OK");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	public void saveAction(ActionEvent actionEvent) {
		 this.save();
	}

	public void search() {
		this.reload();
	}

	public void search(ActionEvent actionEvent) {
		this.reload();
	}

	public ${entityName}Service get${entityName}Service(){
	    if(${modelName}Service == null){
		    ${modelName}Service = ContextFactory.getBean("${modelName}Service");
	    }
	    return ${modelName}Service;
	}

	public void set${entityName}Query(${entityName}Query ${modelName}Query) {
		this.${modelName}Query = ${modelName}Query;
	}

	public void setLazy${entityName}s(LazyDataModel<${entityName}> lazy${entityName}s) {
		this.lazy${entityName}s = lazy${entityName}s;
	}

	public void setSelected${entityName}(${entityName} selected${entityName}) {
		this.selected${entityName} = selected${entityName};
	}

	public void setSelected${entityName}s(${entityName}[] selected${entityName}s) {
		this.selected${entityName}s = selected${entityName}s;
	}

	public void set${entityName}Service(${entityName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
	}
}
