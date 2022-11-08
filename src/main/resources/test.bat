@echo off

set S1AS_HOME=D:\appserver\glassfish3\glassfish
set ASADMIN=%S1AS_HOME%\bin\asadmin.bat
set APS_HOME=E:\project\glassfishsource\appserver\tests\v2-tests\appserv-tests
set JAVA_8_HOME=D:\java\jdk-17
set JAVA_6_HOME=D:\java\jdk1.6.0_45
set JAVA_7_HOME=D:\java\jdk1.7.0_80
set JAVA_HOME=%JAVA_8_HOME%
set BTRACE_HOME=D:\java\btrace-1.3.9
set ANT_HOME=D:\java\apache-ant-1.8.4
set HBASE_HOME=F:\project\pinpoint\quickstart\hbase\hbase
set M2_HOME=D:\java\apache-maven-3.6.1
set MQ_HOME=%S1AS_HOME%\..\mq
set GRADLE_HOME=D:\java\gradle-4.10.2
set MAVEN_OPTS=-Xmx2048M -XX:MaxMetaspaceSize=512m
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;
set Path=%BTRACE_HOME%\bin;%ANDROID_NDK_HOME%\bin;%ANDROID_HOME%\bin;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools;%GRADLE_HOME%\bin;D:\java\javacc-5.0\bin;D:\java\Apache2\bin;D:\java\putty;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%SYSTEMROOT%\System32\WindowsPowerShell\v1.0\;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;%ANDROID_SDK%\tools;%ANDROID_SDK%\platform-tools;%ANT_HOME%\bin;%M2_HOME%\bin;D:\java\MySQL\MySQL Server 6.0\bin;D:\software\cygwin\bin;%S1AS_HOME%\bin;D:\java\Git\bin;D:\java\Git\usr\bin;%CATALINA_HOME%\bin;D:\happy_software\TortoiseSVN\bin;%MQ_HOME%\bin;C:\strawberry\c\bin;C:\strawberry\perl\bin;%NEXUS_HOME%\bin;D:\java;D:\java\Subversion\bin;%Path%
rem set CATALINA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9009
rem set CATALINA_OPTS=-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=9009
echo Chose the dir:
echo 1. E:\project\glassfishsource\appserver\tests\v2-tests\appserv-tests
echo 2. D:\java\apache-tomee-plus-7.0.0-M3
set 1=E:\project\glassfishsource\appserver\tests\v2-tests\appserv-tests
set 2=D:\java\apache-tomee-plus-7.0.0-M3
set /p id="Your chose:"

if %id% == 1 (
  goto one
) else (
  goto two
)
:one
  set id=E:\project\glassfishsource\appserver\tests\v2-tests\appserv-tests
  goto end
:two
  set id=D:\java\apache-tomee-plus-7.0.0-M3
  echo "java -cp E:\project\tools\target\dependency\*;E:\project\tools\target\classes  com.bingli.tools.GoflywayTool"
  java -cp E:\project\tools\target\dependency\*;E:\project\tools\target\classes  com.bingli.tools.GoflywayTool
  goto end
:end
cmd /k cd /d %id%

