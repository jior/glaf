package ${packageName}.view.jsf;

import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.glaf.base.base.*;
import com.glaf.base.context.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.service.*;
 
import ${packageName}.domain.${entityName};
import ${packageName}.service.*;

@Component("${modelName}ViewBean")
@Scope("request")
public class ${entityName}ViewBean implements Serializable {
        private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	protected transient DataItemService dataItemService;

	@Autowired
	protected  transient ${entityName}Service ${modelName}Service;

<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.dataCode?exists>
	protected Vector<SelectItem> all${field.firstUpperName}s;

	protected SelectItem[] ${field.firstLowerName}Items;

         </#if>
    </#list>
</#if>

	protected List<User> users;

	protected Vector<SelectItem> allUsers;

	protected SelectItem[] userItems;

	protected ${entityName} ${modelName};


	public ${entityName}ViewBean() {

	}

	public ${entityName} get${entityName}() {
		if (${modelName} == null) {
			${modelName} = new ${entityName}();
			logger.debug("create new ${modelName}");
		}
		return ${modelName};
	}

<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.dataCode?exists>
	public Vector<SelectItem> getAll${field.firstUpperName}s() {
		all${field.firstUpperName}s = new Vector<SelectItem>();
		List<DataItem> list = dataItemService.getDataItems("${field.dataCode}");
		if (list != null && list.size() > 0) {
			for (DataItem item : list) {
				all${field.firstUpperName}s
						.add(new SelectItem(item.getCode(), item.getName()));
			}
		}
		return all${field.firstUpperName}s;
	}

	public SelectItem[] get${field.firstUpperName}Items() {
		List<DataItem> list = dataItemService.getDataItems("${field.dataCode}");
		if (list != null && !list.isEmpty()) {
			${field.firstLowerName}Items = new SelectItem[list.size()];
			int index = 0;
			for (DataItem item : list) {
				${field.firstLowerName}Items[index++] = new SelectItem(item.getCode(),
						item.getName());
			}
		}
		return ${field.firstLowerName}Items;
	}

         </#if>
    </#list>
</#if>

	public Vector<SelectItem> getAllUsers() {
		allUsers = new Vector<SelectItem>();
		if (users != null && !users.isEmpty()) {
			for (User user : users) {
				allUsers.add(new SelectItem(user.getActorId(), user.getName()));
			}
		}
		return allUsers;
	}

	public SelectItem[] getUserItems() {
		if (users != null && !users.isEmpty()) {
			userItems = new SelectItem[users.size()];
			int index = 0;
			for (User user : users) {
				userItems[index++] = new SelectItem(user.getActorId(),
						user.getName());
			}
		}
		return userItems;
	}

	public List<User> getUsers() {
		return users;
	}

	@PostConstruct
	public void onLoad() {
		String ${modelName}Id = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("${modelName}Id");
		if (StringUtils.isNotEmpty(${modelName}Id)) {
	<#if idField.type?exists && ( idField.type== 'Integer' )>
            ${modelName} = get${entityName}Service().get${entityName}(Integer.valueOf(${modelName}Id));
	<#elseif idField.type?exists && ( idField.type== 'Long' )>
            ${modelName} = get${entityName}Service().get${entityName}(Long.valueOf(${modelName}Id));
	<#else>
           ${modelName} = get${entityName}Service().get${entityName}(${modelName}Id);
	</#if>
			
			logger.debug("get ${modelName}:" + ${modelName});
			if(${modelName} != null){
			    Map<String, UserProfile> userMap = IdentityFactory.getUserProfileMap();
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.name?exists && field.name == 'createByName' >  
			    UserProfile createByUser = userMap.get(${modelName}.getCreateBy());
				if(createByUser != null){
					${modelName}.setCreateByName(createByUser.getName());
				}
        </#if>
	<#if field.name?exists && field.name == 'updateByName' >
				UserProfile updateByUser = userMap.get(${modelName}.getUpdateBy());
				if(updateByUser != null){
					${modelName}.setUpdateByName(updateByUser.getName());
				}
	</#if>
    </#list>
</#if>

<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.dataCode?exists>
	             Map<String, String> ${field.dataCode}DataMap = dataItemService.getDataItemCodeNameMap("${field.dataCode}");
                 if (${field.dataCode}DataMap.containsKey(${modelName}.get${field.firstUpperName}())) {
				    ${modelName}.set${field.firstUpperName}Name(${field.dataCode}DataMap.get(${modelName}.get${field.firstUpperName}()));
			    }
	 </#if>
    </#list>
</#if>
			}
		}
		users = IdentityFactory.getUsers();
	}

	public void remove() {
		logger.debug("remove ${modelName}:" + ${modelName});
		if (${modelName} != null) {
			get${entityName}Service().deleteById(${modelName}.get${idField.firstUpperName}());
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "删除成功",
					"OK");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	public void removeAction(ActionEvent actionEvent) {
		 this.remove();
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

	public void set${entityName}(${entityName} ${modelName}) {
		this.${modelName} = ${modelName};
	}
 
	public void setUsers(List<User> users) {
		this.users = users;
	}

    public void setDataItemService(DataItemService dataItemService) {
		this.dataItemService = dataItemService;
	}

	public ${entityName}Service get${entityName}Service(){
	    if(${modelName}Service == null){
		    ${modelName}Service = ContextFactory.getBean("${modelName}Service");
	    }
	    return ${modelName}Service;
	}

	public void set${entityName}Service(${entityName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
	}
}
