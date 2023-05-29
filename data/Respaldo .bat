@echo off

echo Realizando respaldo de la base de datos...

mkdir C:\PruebasBack

sqlcmd -S DESKTOP-SHNFVF3 -U sa -P bases123 -Q "BACKUP DATABASE GestorDocumentalMSI TO DISK='C:\PruebasBack\MyDatabaseGestorDocumentalMSI.bak'" >> C:\PruebasBack\backup_log.txt
if %errorlevel% neq 0 (
   echo Error al realizar el respaldo de la base de datos.
) else (
   echo El respaldo de la base de datos se ha realizado con éxito.
)



timeout /t 10