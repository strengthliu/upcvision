@echo off
%path%=%path%;.;./jre1.8.0_211/bin
start cmd /k %cd%\redis-server.exe redis.conf
for %%i in (%cd%\lib\*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%~fsi
)
java -version

%cd%\jre1.8.0_211\bin\java -jar %cd%\upcvision-0.0.1-SNAPSHOT.jar