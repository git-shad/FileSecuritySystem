@echo off
javac -d ./bin ./src/*.java
cd ./bin
java FileSecuritySystem
pause