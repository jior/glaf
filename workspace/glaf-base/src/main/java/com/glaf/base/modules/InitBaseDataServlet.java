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

	private BaseDataManager bdm;// ������Ϣ����

	public void init() {
		long startTime = System.currentTimeMillis();
		Constants.ROOT_PATH = getServletContext().getRealPath("/");
		logger.info("root path:" + Constants.ROOT_PATH);
		logger.info("��ʼ��������Ϣ...");
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
			bdm.refreshBaseData();// ˢ������

			beanMap.put("todoJobBean", wac.getBean("todoJobBean"));

			bdm.setBeanMap(beanMap);

			logger.info("��ʼ��������Ϣ���.");

			// װ��ϵͳ�����б�
			ContextUtil.put("function", bdm.getBaseData("ZD0015"));
			ContextUtil.put("wac", wac);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("��ʼ��������Ϣʧ�ܣ�");
		}

		logger.info("��ʱ��" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}
