
1�����start-db.bat������ʾ���ݿ�
   ��ʾ���ݿ��û���Ϊsa,����Ϊ��

2���޸�tomcat�µ������ļ�
   �޸�tomcat\conf\context.xml,����ṩ�����ݿ����ƣ�url�����û�����username�������루password����һ��ʱ���޸ĳ����Լ���������Ϣ��Ĭ���������£�
   �ο��ĵ���http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html

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
          username="sa"
          password=""
          driverClassName="org.h2.Driver"
          url="jdbc:h2:tcp://localhost/glafdb"/>

 
3�����ز���װTomcat
�����tomcat��ѹ����ǰĿ¼����server.xml���������Ŀ¼
<Context path="/glaf" docBase="../../workspace/glaf-web/WebContent" reloadable="false"/>
����Tomcat������
�������µ�ַ��
http://127.0.0.1:9090/glaf
�û���Ϊroot������111111

��ʽ����ʱ��ȥ�������ļ�,λ��glaf-web\WebContent\workflow\testĿ¼��
 