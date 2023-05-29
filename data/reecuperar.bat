@echo off

net stop MSSQLSERVER
net stop SQLSERVERAGENT

echo Servicios detenidos correctamente.
sqlcmd -S DESKTOP-SHNFVF3 -U sa -P bases123 -Q "RESTORE DATABASE GestorDocumentalMSI FROM DISK='C:\PruebasBack\MyDatabaseGestorDocumentalMSI.bak' WITH REPLACE" >> C:\PruebasBack\restore_log.txt

set exitCode=%ERRORLEVEL%

if %exitCode% equ 0 (
  echo Proceso exitoso
) else (
  echo Error en el proceso
)

net start MSSQLSERVER
net start SQLSERVERAGENT

echo Servicios reiniciados correctamente.

timeout /t 60