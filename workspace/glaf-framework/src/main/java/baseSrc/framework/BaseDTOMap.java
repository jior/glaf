//================================================================================================
//项目名称 ：    基盘
//功    能 ：   DTO
//文件名称 ：    BaseDTOMap.java                                   
//描    述 ：    DTO基类，内置对象：返回页面的ID；session对象；返回页面的消息；出错控件；
//         dao中方法返回值必须为BaseDTOMap
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================
package baseSrc.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import baseSrc.framework.BaseConstants.ReportType;

public class BaseDTOMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	//返回页面ID
	private String forwardId = "";
	//session对象
	private Object beanCom = null;	
    private Object defaultDTO = null; 
    private Object defaultDTO_A = null; 
    private Object defaultDTO_B = null; 
	//消息ID
    private String msgId = null; 
	//消息参数
    private ArrayList<String> msgArgs = new ArrayList<String>();
    //出错控件
    private ArrayList<String> errObjIds = new ArrayList<String>();
    //列表数据
    private List<?> detailList;
    
    //下载文件名（含路径），如果此值不为空，struts跳转失效
    private String file = "";
    //设置返回的文件类型
    private String fileContentType = "";

    //报表模版
	private String reportTpl = "";
	//报表类型（PDF、EXCEL,HTML）
	private ReportType reportType = BaseConstants.ReportType.EXCEL;
    //报表Sql
	private HashMap<String, String> reportQuerys = null;
    //报表参数
	private HashMap<String, Object> reportParameters = null;
	
    
    public HashMap<String, Object> getReportParameters() {
		return reportParameters;
	}

    public Object getReportParameter(String paraName) {
		return reportParameters!=null?reportParameters.get(paraName):null;
	}
    
	public void setReportParameter(String paraName,Object paraValue) {
		
		if (null==reportParameters) reportParameters=new HashMap<String, Object>();
		reportParameters.put(paraName, paraValue);
	}

	public HashMap<String, String> getReportQuerys() {
		return reportQuerys;
	}

    public String getReportQuery(String queryName) {
		return reportQuerys!=null?reportQuerys.get(queryName):null;
	}

	public void setReportQuery(String queryName,String queryString) {
		if (null==reportQuerys) reportQuerys=new HashMap<String, String>();
		reportQuerys.put(queryName, queryString);
	}

	public String getReportTpl() {
		return reportTpl;
	}

	public void setReportTpl(String reportTpl) {
		this.reportTpl = reportTpl;
	}
	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	
    /** start 共通下拉框 **/
    private Object lightDTO = null;
    public Object getLightDTO() {
		return lightDTO;
	}

	public void setLightDTO(Object lightDTO) {
		this.lightDTO = lightDTO;
	}

	/** end 共通下拉框 **/

	public List<?> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<?> detailList) {
		this.detailList = detailList;
	}
    
    
	public Object getBeanCom() {
		return beanCom;
	}

	public void setBeanCom(Object beanCom) {
		this.beanCom = beanCom;
	}

	public void setForwardId(String forwardId){
		this.forwardId = forwardId;
	}
	
	public String getForwardId(){
		return this.forwardId;		
	}

	public void setDefaultDTO(Object defaultDTO) {
		this.defaultDTO = defaultDTO;
	}
	public Object getDefaultDTO() {
		return defaultDTO;
	}
	
	public void setDefaultDTO_A(Object defaultDTO_A) {
		this.defaultDTO_A = defaultDTO_A;
	}
	public Object getDefaultDTO_A() {
		return defaultDTO_A;
	}
	
	public void setDefaultDTO_B(Object defaultDTO_B) {
		this.defaultDTO_B = defaultDTO_B;
	}
	public Object getDefaultDTO_B() {
		return defaultDTO_B;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public List<String> getMsgArgs() {
		return msgArgs;
	}
	public void addMsgArg(String msdArg) {
		this.msgArgs.add(msdArg);
	}
	public List<String> getErrObjIds() {
		return errObjIds;
	}
	public void addErrObjId(String errObj) {
		this.errObjIds.add(errObj);
	}	
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}	

}
