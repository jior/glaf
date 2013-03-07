######################################################
####请先执行copy-jar.bat复制jar到运行环境。       ####
######################################################

根据服务器信息自行调整JVM参数：
jvm_args    : -Xms1024m -Xmx1024m -XX:NewSize=64m -XX:MaxNewSize=64m -XX:PermSize=256m -XX:MaxPermSize=512m



1、点击start-db.bat启动演示数据库
   演示数据库用户名为sa,密码为空

2、修改server下的配置文件
   修改server\conf\context.xml,如果提供的数据库名称（url）、用户名（username）、密码（password）不一致时请修改成您自己的配置信息，默认配置如下：
   参考文档：http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html

   <Resource name="jdbc/glafdb"
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
          username="sa"
          password=""
          driverClassName="org.h2.Driver"
          url="jdbc:h2:tcp://localhost/glafdb"/>

 
3、 启动server
如果把需要修改虚拟目录，请修改server/conf/server.xml中配置信息
<Context path="/glaf" docBase="../../workspace/glaf-web/WebContent" reloadable="false"/>
启动server服务器
访问如下地址：
http://127.0.0.1:9090/glaf
用户名为root，密码111111

正式发布时请去掉测试文件,位于glaf-web\WebContent\workflow\test目录。
 