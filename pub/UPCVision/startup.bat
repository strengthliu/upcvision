
@echo off
set JAVA_HOME=%cd%\jdk1.8.0_211
set path=.;./jre1.8.0_211/bin;%path%;
start cmd /k %cd%\redis-server.exe redis.conf
set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
::for %%i in (%cd%\lib\*.jar) do (
::	echo %%i
::   set CLASSPATH=%CLASSPATH%;%%i
::)

java -version

%cd%\jre1.8.0_211\bin\java -jar %cd%\upcvision-0.9.6-SNAPSHOT.jar