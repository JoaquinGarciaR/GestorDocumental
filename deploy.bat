@echo off
@REM Created by Joaquin Garcia Ramirez
@REM 
@REM This batch job utility will help the automation of
@REM Cleaning, compiling & running

@REM Setting up backend and frontend directories
@REM (Assumes this batch job file is inside GestorDocumental base folder)
set BACKEND_DIR=backend
set FRONTEND_DIR=frontend

set command=%1


if "%command%" == "clean" goto opt_clean
if "%command%" == "build" goto opt_compile
if "%command%" == "exec" goto opt_exec
if "%command%" == "create_db" goto opt_create_db
if "%command%" == "insertData" goto opt_insertData



@echo Incorrect Option: '%command%'. Available commands:
@echo   clean           Cleans React dependencies, build folder and SpringBoot Project.
@echo   build           Downloads dependencies, generates bundled React Project 
@echo                   and compiles SpringBoot App.
@echo   exec            Runs the SpringBoot app (PORT 8000)
@echo   create_db       Configure the database
@echo   insertData      Insert a start data in DB

exit /B

@REM User decided to clean. 
:opt_clean
@echo =============== Cleaning npm packages and build folder... ===============
@pushd %FRONTEND_DIR%
@rmdir /S /Q build\
@rmdir /S /Q node_modules\

@echo ================= Cleaning Java Spring Boot project... ===================
@popd
@pushd %BACKEND_DIR%
@echo off
@call mvn clean
@popd
@echo [+] Cleaning done, Goodbye!

goto final

:opt_compile
@echo on 
@echo ================= Downloading Npm dependencies... ======================
@pushd %FRONTEND_DIR%
@timeout 2
@call npm install


@echo ================== Building react app... ====================
@timeout 2
@call npm run build
pause
@echo ======== Cleaning Last Build in Resources Directory =========
@popd
@rmdir /S /Q %BACKEND_DIR%\src\main\resources\public

@echo ===== Copying built frontend into Java Backend... ======

@REM Moves the generated build folder that containts the react app
@REM into a new folder under the resources directory, where
@REM the Spring-Boot app serves static content by default
@xcopy /E /Y %FRONTEND_DIR%\build %BACKEND_DIR%\src\main\resources\public\

@echo ========== Building .jar of the SpringBoot app with its dependencies ==============
@timeout 2
@pushd %BACKEND_DIR%
@call mvn install spring-boot:repackage -DskipTests
@popd
pause
@cls
@echo [+] Compiling of the solution Done. Goodbye!  
goto final


:opt_exec

@REM Then start the SpringBoot Server
@echo ============= Starting the SpringBoot Server [Port 8080] =============
@timeout 2
@echo [+] To finish it press CTRL + C
@call mvn spring-boot:run  -f %BACKEND_DIR%

goto final

@REM Script Database
:opt_create_db
@echo =============== Creating database... ===============
sqlcmd -S KINCHO -U sa -P bases123 -d master -i "%~dp0data\configDatabase.sql"
@echo [+] Database created successfully.
goto final

@REM Script Database
:opt_insertData
@echo =============== Creating database... ===============
sqlcmd -S KINCHO -U sa -P bases123 -d GestorDocumentalMSI -i "%~dp0data\insertData.sql"
@echo [+] Database inserted successfully.
goto final



:final
@REM final of the tool, does nothing