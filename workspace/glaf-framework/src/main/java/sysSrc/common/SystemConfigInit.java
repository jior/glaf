package sysSrc.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;


public class SystemConfigInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SystemConfigInit.class);
	
	public void init(ServletConfig config) throws ServletException {
		// 设置配置文件路径
		String path = config.getInitParameter("configPath");
		// 加载配置文件内容
		try {
			SystemConfig.getInstance().init(path);
		} catch (Exception e) {
			logger.info("SystemConfig加载失败:" + e.getMessage());
		}
	}
	
}
