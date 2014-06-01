@java -cp "h2.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.Console -tcpAllowOthers 
@if errorlevel 1 pause