@echo off
echo ========================================
echo QuickDoctor Appointment System
echo ========================================
echo.

echo Compiling Java files...
if not exist bin mkdir bin
javac -d bin -encoding UTF-8 -sourcepath src src\com\quickdoctor\Main.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo.
    echo Starting QuickDoctor...
    echo.
    java -cp bin com.quickdoctor.Main
) else (
    echo.
    echo Compilation failed! Please check for errors.
    pause
)
