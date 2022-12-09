@echo off
setlocal
javac -d ./bin ./src/*.java
set run=%CD%\bin\
start "File Security System Application" /d %run% /b /wait cmd /c java FileSecuritySystem
pause
