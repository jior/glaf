package sysSrc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class SystemConfig {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SystemInit.class);
	private static Map mp = null;
	private boolean initFlag = false;

	private static SystemConfig instance = new SystemConfig();

	private SystemConfig() {
	}

	/**
	 * 单例模式
	 * 
	 * @return SystemConfig对象
	 */
	public static SystemConfig getInstance() {
		return instance;
	}

	/**
	 * 初始化SystemConfig，只能初始化一次。
	 * 
	 * @param path
	 *            配置文件路径
	 * @throws DocumentException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws URISyntaxException
	 */
	public void init(String path) throws FileNotFoundException, IOException,
			DocumentException, URISyntaxException {
		if (initFlag == true) {
			// 只能成功初始化一次。
			return;
		}
		// 加载配置文件内容
		this.setData(path);
		initFlag = true;
	}

	/**
	 * 
	 * @return 返回配置文件中信息对应Map
	 */
	public Map getMap() {
		return mp;
	}

	// 获取数据
	private void setData(String path) throws FileNotFoundException,
			IOException, DocumentException, URISyntaxException {
		String ProjectPath = path;

		File fo = new File(ProjectPath);
		// 检查入参对应文件是否存在
		if (!fo.exists()) {
			// 设置路径（本例为GMMC-PM\configfiles\路径下）
			ProjectPath = new File(getClass().getClassLoader().getResource("")
					.toURI()).getPath()
					+ "/" + ProjectPath;
		}

		// 为properties格式
		if (ProjectPath != null
				&& ProjectPath.trim().toLowerCase().lastIndexOf(".properties") > 0
				&& ProjectPath.toLowerCase().endsWith(".properties")) {
			this.mp = new HashMap();

			// 以下部分仅做例子...具体实现根据实际情况决定做
			Properties properties = new Properties();
			properties.load(new FileInputStream(ProjectPath));
			String name = properties.getProperty("name");
			String value = properties.getProperty("value");
			this.mp.put(name, value);
			return;
		}

		// 为xml格式
		if (ProjectPath != null && ProjectPath.trim().toLowerCase().lastIndexOf(".xml") > 0
				&& ProjectPath.toLowerCase().endsWith(".xml")) {
			this.mp = new HashMap();

			// 以下部分仅做例子...具体实现根据实际情况决定
			File f = new File(ProjectPath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element fooA;
			String name;
			String value;

			for (Iterator i = root.elementIterator("tag"); i.hasNext();) {
				fooA = (Element) i.next();
				name = fooA.elementText("name");
				value = fooA.elementText("value");
				this.mp.put(name, value);
			}
			return;
		}
	}
}
