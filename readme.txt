1����װPostgreSQL8.4
   ��װ��ɺ������һ���û�rootΪ����Ա��ͬʱ����һ�����ݿ�Ϊjbpm

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
          username="root"
          password="root"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://127.0.0.1:5432/jbpm"/>

3����pgsqlĿ¼�µĽű�������ʼ������
�����ݿ⹤����ִ��createTable.sql.txt����Ŀǰֻ��PostgreSQL8.4��
ִ��init.sql.txt�������ݳ�ʼ��

4������Eclipse�����б����װant����Դ�룬���û�б��빤�ߣ�ֱ�Ӱ�glaf-base.jar���Ƶ�war\WEB-INF\libĿ¼
����ʱ��Ҫ��war\WEB-INF\lib��ȫ��jar��server\lib�µ�jar�������·��

5������Tomcat������start-server.bat
�������µ�ַ��
http://127.0.0.1:9090/glaf
�û���Ϊroot������111111
 