/**
 * 提供JDBC方式下数据库的RIUD处理
 */
package glaf.batch.jdbcAccess;

import glaf.batch.BatchConstans;
import glaf.batch.exception.BatchException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class JDBCAccess {
	//数据库连接
	private Connection conn;
	//数据库链接配置信息
	private DbPersistence dbConfig;
	//预编译查询语句
	private PreparedStatement pstmt;
	//查询结果集
	private ResultSet rs;
	//非查询操作的 Statement
	private PreparedStatement insertPstmt;
	//Result Set Statck
	Stack rsStrack = new Stack();
	//Statement Statck
	Stack statmStrack = new Stack();
	
	private String sqlConfigFile;
	
	

	/**
	 * 构造函数
	 * @param db 数据库连接配置信息
	 */
	public JDBCAccess(DbPersistence db) {
		this.dbConfig = db;

	}

	/**
	 * 新建一个数据库链接
	 */
	public void openConnection(){

		try {
			Class.forName(this.dbConfig.getDriverClassName());
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			throw new BatchException(e);
		}
		try {
			this.conn = DriverManager.getConnection(this.dbConfig.getUrl(),
					this.dbConfig.getUsername(), this.dbConfig.getPassword());
			this.conn.setAutoCommit(false);
		} catch (SQLException se) {
			System.out.println("数据库连接失败！");
			throw new BatchException(se);
		}
	}

	/**
	 * 关闭数据库链接
	 */
	public void closeConnection() {
		//关闭数据集
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new BatchException(e);
			}
		}
		//关闭预编译查询语句
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new BatchException(e);
			}
		}
		//关闭数据库链接
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new BatchException(e);
			}
		}
	}
	
	/**
	 * 提交事务
	 */
	public void commit(){
		if (conn != null) { 
			try {
				conn.commit();
			} catch (SQLException e) {
				throw new BatchException(e);
			}
		}
	}
	
	/**
	 * 回滚事务
	 */
	public void rollback(){
		if (conn != null) { 
			try {
				conn.rollback();
			} catch (SQLException e) {
				throw new BatchException(e);
			}
		}
	}
	
	/**
	 * 传入原生检索sql，返回检索结果集ResultSet
	 * @param sql 原生检索sql
	 * @return 检索结果集ResultSet
	 */
	public ResultSet find(String sql){
		this.rs = null;
		/**
		 * 修改3.
		 */
		//当存在ResultSet时把当前 ResultSet及 statement 推入Strack中
		if(this.rs!=null&&this.pstmt!=null){
			rsStrack.push(this.rs);
			statmStrack.push(this.pstmt);
		}
		try {
		//开启 statement 及 ResultSet 
			this.pstmt = conn.prepareStatement(sql) ;
			this.rs=this.pstmt.executeQuery();
		} catch (SQLException e) {
			throw new BatchException(e);
		}
		return this.rs;
	}
	
	
	
	/**修改2.
	 * 关闭当前的Result Set 及 Statement
	 * 增加的方法：显式关闭 Result Set 及 Statement 资源
	 * 解决多次查询操作 时 超出打开最大游标数的错误
	 */
	public void rsClose(){
		//关闭Result Set 及 Statement
		if(this.rs!=null){
			try {
				this.pstmt.close();
				this.rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//还原上次的 Result Set 及 Statement
			if(!rsStrack.isEmpty())
			this.rs=(ResultSet)this.rsStrack.pop();
			if(!statmStrack.isEmpty())
			this.pstmt=(PreparedStatement)this.statmStrack.pop();
		}
		
	}
	/**
	 * 关闭所有的Result Set 及 Statement
	 */
	public void closeAll(){
		for(;!rsStrack.isEmpty();){
			try {
				((ResultSet)this.rsStrack.pop()).close();
				((PreparedStatement)this.statmStrack.pop()).close();
				insertPstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 传入需要执行的sql语句
	 * @param sql 必须是insert语句
	 * @return 执行结果条数
	 */
	public int insert(String sql){
		return executeUpdate(sql);
	}
	
	/**
	 * 传入需要执行的sql语句
	 * @param sql 必须是update语句
	 * @return 执行结果条数
	 */
	public int update(String sql){
		return executeUpdate(sql);
	}
	
	/**
	 * 传入需要执行的sql语句
	 * @param sql 必须是delete语句
	 * @return 执行结果条数
	 */
	public int delete(String sql){
		return executeUpdate(sql);
	}
	
	/**
	 * 传入需要执行的sql语句
	 * @param sql 必须是insert/update/delete语句
	 * @return 执行结果条数
	 */
	private int executeUpdate(String sql){
		try {
			int ret = 0;
			this.insertPstmt= conn.prepareStatement(sql) ;
			ret = this.insertPstmt.executeUpdate();
			//修改1.
			//关闭statement 避免开启过多statement
			//为因没有ResultSet statement关闭不影响其它操作
			this.insertPstmt.close();
			return ret;
		} catch (SQLException e) {
			throw new BatchException(e);
		}
	}

	public String getSqlConfigFile() {
		return sqlConfigFile;
	}

	public void setSqlConfigFile(String sqlConfigFile) {
		this.sqlConfigFile = sqlConfigFile;
	}

	/**
	 * 根据传入的sqlid查找对应的sql文
	 * @param sqlID
	 * @return
	 */
	public String getSqlById(String sqlID,Map<String,Object> params){
		String ret = "";
		
		try {
			// 设置配置文件路径
			String configFile = BatchConstans.BATCH_PATH + "\\configfiles\\sqlXml\\" + this.sqlConfigFile;
			File f = new File(configFile);
			//读取文件信息
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element sqlElement = null;
			
			//遍历sql节点，根据id查找sql文
			for (Iterator i = root.elementIterator("sql"); i.hasNext();) {
				sqlElement = (Element) i.next();
				if(sqlElement.attributeValue("id").equals(sqlID)){
					ret = (String)sqlElement.getText();
					break;
				}
			}
			
			//如果没有找到sql文，则抛出异常
			if("".equals(ret.trim())){
				throw new BatchException("sql文未找到!");
			}
			
			//替换参数
			if (params != null && params.size() > 0) {
				Iterator iterator = params.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					if(ret.contains(":" + name)){
						Object value = params.get(name);
						String temp = "";
						if(value instanceof String){
							temp = "'" + value.toString() + "'";
						}else{
							temp = value.toString();
						}
						ret = ret.replaceAll("\\:" + name, "\\" + temp);
					}
				}
			}
		} catch (DocumentException e) {
			throw new BatchException(e);
		}
		
		return ret;
	}
	
}
