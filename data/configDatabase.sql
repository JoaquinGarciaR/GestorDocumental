USE [master]
GO

-- Obtener los ID de las sesiones activas del usuario
DECLARE @killSessionsSQL NVARCHAR(MAX) = N'';
SELECT @killSessionsSQL += N'KILL ' + CAST(session_id AS NVARCHAR) + N'; '
FROM sys.dm_exec_sessions
WHERE login_name = 'msigdadmin';

-- Ejecutar las sentencias KILL para cerrar las sesiones
EXEC sp_executesql @killSessionsSQL;


IF EXISTS (SELECT 1 FROM sys.server_principals WHERE name = 'msigdadmin')
    DROP LOGIN [msigdadmin];
GO

IF EXISTS (SELECT 1 FROM sys.database_principals WHERE name = 'msigdadmin')
    DROP USER msigdadmin;
GO

CREATE DATABASE GestorDocumentalMSI;

CREATE LOGIN [msigdadmin] WITH PASSWORD = N'msiadmin1234#', DEFAULT_DATABASE = [GestorDocumentalMSI]
GO

ALTER SERVER ROLE [sysadmin] ADD MEMBER [msigdadmin]
GO

CREATE USER msigdadmin FOR LOGIN msigdadmin;  
GO
