@echo off
setlocal
echo Installing the virtual terminal, this is only for windows 10
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
pause 