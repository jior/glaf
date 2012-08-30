package demoSrc.src;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class BaseSampleForm extends SysBaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private AutoArrayList baseSampleDetails = new AutoArrayList(BaseSampleDetails.class);

	private String s_Sysno;
	private String s_Name;
	private String s_Sex;
	private String s_Age;
	private String s_City;
	private String s_DeadYear;
	private String s_Date;
	private String s_Money;
	private String s_Path;
	private String s_Df;
	private String s_Pe0;
	private String s_Pe1;
	private String s_Pe2;
	private String s_Pe6;
	
	private Object combTestCollection;
	private String v_combTest;
	
	
	public String getV_combTest() {
		return v_combTest;
	}

	public void setV_combTest(String test) {
		v_combTest = test;
	}

	public Object getCombTestCollection() {
		return combTestCollection;
	}

	public void setCombTestCollection(Object combTestCollection) {
		this.combTestCollection = combTestCollection;
	}

	private String searchFlag;
	
	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getS_Path() {
		return s_Path;
	}

	public void setS_Path(String path) {
		s_Path = path;
	}

	public String getS_Date() {
		return s_Date;
	}

	public void setS_Date(String date) {
		s_Date = date;
	}

	public void setBaseSampleDetails(AutoArrayList baseSampleDetails){
		this.baseSampleDetails.clear();
		this.baseSampleDetails.addAll(baseSampleDetails);
		}

	public AutoArrayList getBaseSampleDetails(){
		return this.baseSampleDetails;
	}

	public String getS_Sysno() {
		return s_Sysno;
	}
	public void setS_Sysno(String sysno) {
		s_Sysno = sysno;
	}
	public void setS_Name(String s_Name) {
		this.s_Name = s_Name;
	}
	public String getS_Name() {
		return s_Name;
	}
	public String getS_Sex() {
		return s_Sex;
	}
	public void setS_Sex(String sex) {
		s_Sex = sex;
	}
	public String getS_Age() {
		return s_Age;
	}
	public void setS_Age(String age) {
		s_Age = age;
	}
	public String getS_Money() {
		return s_Money;
	}
	public void setS_Money(String money) {
		s_Money = money;
	}
	public String getS_City() {
		return s_City;
	}
	public void setS_City(String city) {
		s_City = city;
	}
	public String getS_DeadYear() {
		return s_DeadYear;
	}
	public void setS_DeadYear(String deadYear) {
		s_DeadYear = deadYear;
	}
	
	public String getS_Df() {
		return s_Df;
	}
	public void setS_Df(String df) {
		s_Df = df;
	}
	
	public String getS_Pe0() {
		return s_Pe0;
	}
	public void setS_Pe0(String pe0) {
		s_Pe0 = pe0;
	}
	
	public String getS_Pe1() {
		return s_Pe1;
	}
	public void setS_Pe1(String pe1) {
		s_Pe1 = pe1;
	}
	
	public String getS_Pe2() {
		return s_Pe2;
	}
	public void setS_Pe2(String pe2) {
		s_Pe2 = pe2;
	}
	
	public String getS_Pe6() {
		return s_Pe6;
	}
	public void setS_Pe6(String pe6) {
		s_Pe6 = pe6;
	}
	
	public ActionErrors validate(ActionMapping mapping, 
		      HttpServletRequest request) {
		
		ActionErrors ret = null;
		
		if("runPageIscSampleCreate".equals(this.getActionMethodId())){
			ret = super.validate(mapping, request);			
		}
		
		return ret;

	}
}
