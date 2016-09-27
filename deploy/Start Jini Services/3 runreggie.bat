call setpaths.bat
%JAVAHOME%\java -jar -Djava.security.policy=policy.all %JINIHOME%\lib\reggie.jar http://%DOWNLOADHOST%/reggie-dl.jar policy.all reggie.log public