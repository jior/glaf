package com.glaf.base.modules;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glaf.base.modules.utils.ContextUtil;

public class InitBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 2072103368980714549L;

	private final static Log logger = LogFactory
			.getLog(InitBaseDataServlet.class);

	private Map beanMap = new HashMap();

	private Map serviceMap = new HashMap();

	private BaseDataManager bdm;// 基础信息管理

	public void init() {
		long startTime = System.currentTimeMillis();
		Constants.ROOT_PATH = getServletContext().getRealPath("/");
		logger.info("root path:" + Constants.ROOT_PATH);
		logger.info("初始化基础信息...");
		try {
			bdm = BaseDataManager.getInstance();
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			String[] beanNames = BaseDataManager.SV_NAMES;
			for (int i = 0; i < beanNames.length; i++) {
				logger.info("load service:" + beanNames[i]);
				try {
					Object ob = wac.getBean(beanNames[i]);
					serviceMap.put(beanNames[i], ob);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
			bdm.setServiceMap(serviceMap);
			bdm.refreshBaseData();// 刷新数据

			beanMap.put("todoJobBean", wac.getBean("todoJobBean"));

			bdm.setBeanMap(beanMap);

			logger.info("初始化基础信息完成.");

			// 装载系统功能列表
			ContextUtil.put("function", bdm.getBaseData("ZD0015"));
			ContextUtil.put("wac", wac);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化基础信息失败！");
		}
		// 38环境需注释以下部份，表示禁用接口接收
		/*
		 * AppContext.setServletContext(this.getServletContext());
		 * 
		 * logger.info("接口处理开始..."); if(!MappingManager.init()){
		 * logger.error("加载接口mapping失败！接口系统数据接收线程不能启动！"); }else{
		 * logger.info("加载接口mapping完成."); QueueManager.receiveStart(); }
		 */
		// InterfaceConstants.setSendable(true);
		logger.info("接口数据发送环境准备就绪");

		logger.info("耗时：" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}
