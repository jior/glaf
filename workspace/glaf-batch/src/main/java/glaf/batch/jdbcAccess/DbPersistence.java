/**
 * 解析DB链接配置
 */
package glaf.batch.jdbcAccess;

import glaf.batch.BatchConstans;
import glaf.batch.exception.BatchException;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DbPersistence {
	//数据库配置名
	private String dbId;
	//驱动名
	private String driverClassName;
	//连接路径
	private String url;
	//链接用户名
	private String username;
	//连接密码
	private String password;

	//数据库配置文件名
	private final String CONFIG_FILE = "BatchDBConfig.xml";

	/**
	 * 构造函数，根据传入的数据库ID解析数据库配置信息，并保存至各属性中
	 * @param dbId
	 */
	public DbPersistence(String dbId) {
		this.dbId = dbId;
		// 读取配置文件中对应的配置信息
		getConfigs();
	}

	/**
	 * 根据dbId获取对应的配置信息, 初始化其他私有变量
	 * 
	 */
	private void getConfigs() {
		try {
			// 设置配置文件路径
			String configFile = BatchConstans.BATCH_PATH + "\\configfiles\\" + CONFIG_FILE;
			File f = new File(configFile);
			//读取文件信息
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element session = null;
			//获取所有的数据库链接，判断dbid是否和当前给定的匹配
			for (Iterator i = root.elementIterator("session"); i.hasNext();) {
				session = (Element) i.next();
				//获取详细配置信息，并存储在私有变量中
				if(this.dbId.equals(session.elementText("id"))){
					this.driverClassName = session.elementText("driverClassName");
					this.url = session.elementText("url");
					this.username = session.elementText("username");
					this.password = session.elementText("password");
				}
			}
		} catch (DocumentException e) {
			throw new BatchException(e);
		}
	}

	public String getDbId() {
		return dbId;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
