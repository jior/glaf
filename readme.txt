
1、安装PostgreSQL8.4
   安装完成后请添加一个用户root为管理员，同时创建一个数据库为jbpm

2、修改tomcat下的配置文件
   修改tomcat\conf\context.xml,如果提供的数据库名称（url）、用户名（username）、密码（password）不一致时请修改成您自己的配置信息，默认配置如下：
   参考文档：http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html

   <Resource name="jdbc/jbpm"
          auth="Container"
          type="javax.sql.DataSource"
          factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
          testWhileIdle="true"
          testOnBorrow="true"
          testOnReturn="false"
          validationQuery="SELECT 1"
          validationInterval="30000"
          timeBetweenEvictionRunsMillis="30000"
          maxActive="100"
          minIdle="10"
          maxWait="10000"
          initialSize="10"
          removeAbandonedTimeout="60"
          removeAbandoned="true"
          logAbandoned="true"
          minEvictableIdleTimeMillis="30000"
          jmxEnabled="false"
          jdbcInterceptors="org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;
            org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"
          username="root"
          password="root"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://127.0.0.1:5432/jbpm"/>

3、用db/postgresql目录下的脚本建表及初始化数据
在数据库工具中执行createTable.sql.txt建表（目前只限PostgreSQL8.4）
执行init.sql.txt进行数据初始化

4、请在Eclipse工程中编译或安装ant编译源码，将各个工程bin目录下所有的class 复制到glaf-web\WebContent\WEB-INF\classes目录.

5、下载并安装Tomcat
如果把tomcat解压到当前目录，在server.xml中添加虚拟目录
<Context path="/glaf" docBase="../../workspace/glaf-web/WebContent" reloadable="false"/>
启动Tomcat服务器
访问如下地址：
http://127.0.0.1:8080/glaf
用户名为root，密码111111

正式发布时请去掉测试文件,位于glaf-web\WebContent\workflow\test目录。
 