######################################################
####����ִ��copy-jar.bat����jar�����л�����       ####
######################################################

���ݷ�������Ϣ���е���JVM������
jvm_args    : -Xms1024m -Xmx1024m -XX:NewSize=64m -XX:MaxNewSize=64m -XX:PermSize=256m -XX:MaxPermSize=512m



1�����start-db.bat������ʾ���ݿ�
   ��ʾ���ݿ��û���Ϊsa,����Ϊ��

2���޸�server�µ������ļ�
   �޸�server\conf\context.xml,����ṩ�����ݿ����ƣ�url�����û�����username�������루password����һ��ʱ���޸ĳ����Լ���������Ϣ��Ĭ���������£�
   �ο��ĵ���http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html

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

 
3�� ����server
�������Ҫ�޸�����Ŀ¼�����޸�server/conf/server.xml��������Ϣ
<Context path="/glaf" docBase="../../workspace/glaf-web/WebContent" reloadable="false"/>
����server������
�������µ�ַ��
http://127.0.0.1:9090/glaf
�û���Ϊroot������111111

��ʽ����ʱ��ȥ�������ļ�,λ��glaf-web\WebContent\workflow\testĿ¼��
 