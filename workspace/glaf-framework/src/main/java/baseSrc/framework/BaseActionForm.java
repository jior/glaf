//================================================================================================
//项目名称 ：    基盘
//功    能 ：   ActionForm
//文件名称 ：    BaseActionForm.java                                   
//描    述 ：    所有Form都需要继承
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================

package baseSrc.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.validator.ValidatorResult;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;


public class BaseActionForm extends ValidatorForm {
	
	private static final long serialVersionUID = 1L;
	
	private String actionMethodId;

	private int pageNo;
	private int pageNoBefore;
	private int pageNoNext;
	private int pageNoEnd;
	private int pageNoMax;
	private long recordCount;
	private Map<String,Object> ProcessParems;
	private long taskInstanceId = 0;
	
	private String gridData;
	private String recordsJsonStr;
    //EXT回调标识
    private String extCBFlag;
    
    private String ip;
    
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 将传入的json串转换成list对象
	 * @param detailClass 需要转换成的对象.class
	 * @return 从json串转换出的list对象
	 */
	public List getGridList(Class detailClass) {
		List objs=null;
		//如果json串不为空时进行转换
		if (null != getRecordsJsonStr() && !"".equals(getRecordsJsonStr())){
			//将json串转换成object序列
			JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(getRecordsJsonStr());
			
			if(jsonArray!=null){
				objs=new ArrayList();
				//将object序列转换成list
				List list=(List)JSONSerializer.toJava(jsonArray);
				for(Object o:list){
					//将list中的对象转换成class实例
					JSONObject jsonObject=JSONObject.fromObject(o);
					objs.add(JSONObject.toBean(jsonObject, detailClass));
				}
			}
		}
		//返回转换结果
		return objs;
	}

	public String getRecordsJsonStr() {
		return recordsJsonStr;
	}

	public void setRecordsJsonStr(String recordsJsonStr) {
		this.recordsJsonStr = recordsJsonStr;
	}

	public String getGridData() {
		return gridData;
	}

	public void setGridData(String gridData) {
		this.gridData = gridData;
	}

	public String getActionMethodId() {
		return actionMethodId;
	}

	public void setActionMethodId(String actionMethodId) {
		this.actionMethodId = actionMethodId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageNoBefore() {
		return pageNoBefore;
	}

	public void setPageNoBefore(int pageNoBefore) {
		this.pageNoBefore = pageNoBefore;
	}

	public int getPageNoNext() {
		return pageNoNext;
	}

	public void setPageNoNext(int pageNoNext) {
		this.pageNoNext = pageNoNext;
	}

	public int getPageNoEnd() {
		return pageNoEnd;
	}

	public void setPageNoEnd(int pageNoEnd) {
		this.pageNoEnd = pageNoEnd;
	}
	public int getPageNoMax() {
		return pageNoMax;
	}

	public void setPageNoMax(int pageNoMax) {
		this.pageNoMax = pageNoMax;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	
	public Map<String, Object> getProcessParems() {
		return ProcessParems;
	}

	public void setProcessParems(Map<String, Object> processParems) {
		ProcessParems = processParems;
	}

	public long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}
	
	public String getExtCBFlag() {
		return extCBFlag;
	}

	public void setExtCBFlag(String extCBFlag) {
		this.extCBFlag = extCBFlag;
	}	

	public ActionErrors validate(ActionMapping mapping, 
		      HttpServletRequest request) { 

		if(null == this.actionMethodId || "".equals(this.actionMethodId)){
			return null;
		}
		
		ActionErrors errors = super.validate(mapping, request);
		
		Object[] propertyNames = null;
		//for (Object property : this.getValidatorResults().getPropertyNames()) {
		int pnsLen = 0;
		if(null != this.getValidatorResults()){
			propertyNames = this.getValidatorResults().getPropertyNames().toArray();
			pnsLen = propertyNames.length;
		}
		for (int i = 0;i < pnsLen;i++ ) {
			
			String propertyName = propertyNames[i].toString();
			
			boolean hasError = false;
			
		    ValidatorResult vr = 
		    	this.getValidatorResults().getValidatorResult(propertyName);
            
		    Iterator<?> keys = vr.getActions();
			if(null!=keys){
			    while (keys.hasNext()) {
					String actName = (String) keys.next();      
					if (!vr.isValid(actName)){
						hasError = true;
						break;
					}
				}
			}
			
			if (hasError){
			    
				if (!vr.getField().isIndexed()){
					ArrayList<String> errObjIds = new ArrayList<String>();
					errObjIds.add(propertyName);				
					request.setAttribute(BaseConstants.ISC_ERROR_DTO_ID, errObjIds);
				}
			    Iterator<?> err = errors.get(propertyName);			    
				if(null!=err){
				    while (err.hasNext()) {
						ActionMessage am = (ActionMessage) err.next();
						errors.clear();
						errors.add(propertyName, am);
							break;
					}
				}
				
				break;				
			}
	    }

		
	    //�ԉ�ActionErrors 
		return errors; 
		
	}


}
