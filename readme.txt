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

3、用pgsql目录下的脚本建表及初始化数据
在数据库工具中执行createTable.sql.txt建表（目前只限PostgreSQL8.4）
执行init.sql.txt进行数据初始化

4、请在Eclipse工程中编译或安装ant编译源码，如果没有编译工具，直接把glaf-base.jar复制到war\WEB-INF\lib目录
编译时需要将war\WEB-INF\lib的全部jar及server\lib下的jar加入编译路径

5、启动Tomcat服务器start-server.bat
访问如下地址：
http://127.0.0.1:9090/glaf
用户名为root，密码111111
 