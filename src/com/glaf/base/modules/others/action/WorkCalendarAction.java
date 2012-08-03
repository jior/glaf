package com.glaf.base.modules.others.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.others.service.WorkCalendarService;
import com.glaf.base.utils.ParamUtil;

public class WorkCalendarAction extends DispatchActionSupport{
	private static final Log logger = LogFactory.getLog(WorkCalendarAction.class);
	
	private WorkCalendarService workCalendarService;		
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
		logger.info("setWorkCalendarService");
	}
	/**
	 * ��ʾ���������б�
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar cal=Calendar.getInstance(); 
		int year =ParamUtil.getIntParameter(request, "year", cal.get(Calendar.YEAR)); 
		request.setAttribute("year", String.valueOf(year));
		return mapping.findForward("show_list");
	}
	/**
	 * ��ʾ���������б�
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showCalendar(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		
		Calendar cal=Calendar.getInstance(); 
		int month=ParamUtil.getIntParameter(request, "month", cal.get(Calendar.MONTH));
		int year =ParamUtil.getIntParameter(request, "year", cal.get(Calendar.YEAR)); 

		cal.set(Calendar.MONTH, month); //�����·�
		cal.set(Calendar.YEAR, year); //�������
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		logger.info("month:" + month);
		int firstIndex=cal.get(Calendar.DAY_OF_WEEK)-1; //���µ�һ�������ڼ�
		logger.info("firstIndex:" + firstIndex);
		int maxIndex=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//���µ����� 
		logger.info("maxIndex:" + maxIndex);
		int weeks=Calendar.WEEK_OF_MONTH;//���µ�����		
		cal.set(Calendar.DATE, 1);//����1�������ڼ�
		if(cal.get(Calendar.DAY_OF_WEEK)==7)weeks+=1;
		logger.info("day of week:"+cal.get(Calendar.DAY_OF_WEEK));
		logger.info("weeks:" + weeks);
		
		String days[]=new String[42]; 
		for(int i=0;i<42;i++) { 
		  days[i]="";
		} 
		for(int i=0; i<maxIndex; i++){ 
		  days[firstIndex+i]=String.valueOf(i+1); 
		} 
		
		List list = workCalendarService.getWorkDateList(year, month+1);
		if(list==null)list=new ArrayList();
		
		request.setAttribute("list", list);
		request.setAttribute("year", String.valueOf(year));
		request.setAttribute("month", String.valueOf(month));
		request.setAttribute("weeks", String.valueOf(weeks));
		request.setAttribute("days", days);
		
		return mapping.findForward("show_cal");
	}
}
