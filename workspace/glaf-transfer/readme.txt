1����װJDK
������վ��
http://www.oracle.com/technetwork/java/javase/downloads/index.html
�Ƽ�����jdk1.6�İ汾��
���ݲ���ϵͳ�İ汾��32λ��64λ��ѡ������һ���汾��װ��
���е�32λWindows��jdk-6u45-windows-i586.exe����װ��C:\���Ƽ���װ��C:\jdk1.6.0Ŀ¼�¡�
���е�64λWindows��jdk-6u45-windows-x64.exe����װ��C:\���Ƽ���װ��C:\jdk1.6.0Ŀ¼�¡�

2������JAVA_HOME
  ��������ϡ��ҵĵ��ԡ��򡰼�������Ҽ���ѡ�����ԡ�������������������������Ի���,�½�һ����������JAVA_HOME��
JAVA_HOME=c:\jdk1.6.0

3�����û�������������Դ��Ϣconf\jdbc.properties
    
jdbc.name=default
jdbc.type=sqlserver
jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc.url=jdbc:sqlserver://127.0.0.1:1433;databaseName=pDemo1223
jdbc.user=sa
jdbc.password=

4������Դ���ݿ������Դ��Ϣconf\jdbc\src.jdbc.properties
    
jdbc.name=src
jdbc.type=sqlserver
jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc.url=jdbc:sqlserver://127.0.0.1:1433;databaseName=pDemo1223
jdbc.user=sa
jdbc.password=
jdbc.autocommit=false

ע�⣺ͨ����������������Դ��Ϣ��Դ���ݿ������Դ��Ϣһ�¼��ɡ�
      ��3��4�������ļ��з����������ݿ⣬�û���������һ����


5������Ŀ�����ݿ������Դ��Ϣconf\jdbc\dest.jdbc.properties
    
jdbc.name=dest
jdbc.type=sqlserver
jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc.url=jdbc:sqlserver://127.0.0.1:1433;databaseName=pDemo_test
jdbc.user=sa
jdbc.password=
jdbc.autocommit=false


6������run.bat
   �����������run.bat���ɡ�

