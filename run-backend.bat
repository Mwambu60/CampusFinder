@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -d out java\src\campusfinder\*.java
if errorlevel 1 (
  echo Java compilation failed. Check that a JDK is installed and on PATH.
  pause
  exit /b 1
)
java -cp out campusfinder.CampusFinderServer
