USE [GestorDocumentalMSI]
GO

--Primer Usuario

INSERT INTO [dbo].[departament] ([id_departament], [date_created], [last_updated], [name]) 
VALUES (100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Departamento Administrativo')
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (100,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tecnologia', 100)
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (101,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FundeCoca', 100)
GO

INSERT INTO [dbo].[user] ([id_user],[boss],[date_created],[email],[last_name1],[last_name2],
[last_updated],[name],[password_encrypt],[role],[state],[telephone],[departament_id],[unit_id])
VALUES ('11111111', 0, CURRENT_TIMESTAMP, 'informatica@sanisidro.go.cr', 'Salas', 'Thompson', CURRENT_TIMESTAMP, 
'Carlo', '$2a$10$bGog2dETYQx4XpXNK9ZmJ.hv2VCW5DTAbVZXQ2o3i64QS2ixMF8IK', 1, 1, '11111111', 100, 100) -- Password Carlo123
GO

-- Segundo Usuario

INSERT INTO [dbo].[departament] ([id_departament], [date_created], [last_updated], [name]) 
VALUES (110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Departamento Financiero')
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (110,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Tesorer�a', 110)
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (111,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Sub Tesorer�a', 110)
GO

INSERT INTO [dbo].[user] ([id_user],[boss],[date_created],[email],[last_name1],[last_name2],
[last_updated],[name],[password_encrypt],[role],[state],[telephone],[departament_id],[unit_id])
VALUES ('22222222', 0, CURRENT_TIMESTAMP, 'anag.penaranda@sanisidro.go.cr', 'Pe�aranda', 'Chinchilla', CURRENT_TIMESTAMP, 
'Ana', '$2a$10$MGHqeK/bkeiOcJWpiTMY1OpEJsFf8N/.u0GiD4BPgyF2mG9JqIRJO', 0, 1, '2222222', 110, 110) -- Password Ana321
GO


-- Tercer Usuario

INSERT INTO [dbo].[departament] ([id_departament], [date_created], [last_updated], [name]) 
VALUES (120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Departamento Administrativo')
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (120,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Archivo', 120)
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (121,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Sub Archivo', 120)
GO

INSERT INTO [dbo].[user] ([id_user],[boss],[date_created],[email],[last_name1],[last_name2],
[last_updated],[name],[password_encrypt],[role],[state],[telephone],[departament_id],[unit_id])
VALUES ('333333333', 0, CURRENT_TIMESTAMP, 'francine.rodriguez@sanisidro.go.cr', 'Rodr�guez', 'Arce', CURRENT_TIMESTAMP, 
'Francine', '$2a$10$9tinyJ7.bXw1u418ia3t.evBY1eo3IwnhTu72MmEIOk396orTvCtO', 0, 1, '33333333', 120, 120) -- Password Franarce123
GO


-- Cuarto Usuario

INSERT INTO [dbo].[departament] ([id_departament], [date_created], [last_updated], [name]) 
VALUES (130, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Departamento Social')
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (130,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Escuela de M�sica ', 130)
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (131,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Sub Escuela de M�sica ', 130)
GO

INSERT INTO [dbo].[user] ([id_user],[boss],[date_created],[email],[last_name1],[last_name2],
[last_updated],[name],[password_encrypt],[role],[state],[telephone],[departament_id],[unit_id])
VALUES ('444444444', 0, CURRENT_TIMESTAMP, 'miriam.padilla@sanisidro.go.cr', 'Padilla', 'Chinchilla', CURRENT_TIMESTAMP, 
'Miriam', '$2a$10$u9FbS0eotIOXsFIo7/PDXOXhyKEghSS74SP7R88GuZGPnFD6Vbh2W', 0, 1, '44444444', 130, 130) -- Password Miram123
GO

-- Quinto Usuario


INSERT INTO [dbo].[departament] ([id_departament], [date_created], [last_updated], [name]) 
VALUES (140, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Departamento Gestion Vial')
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (140,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Unidad Tecnica Vial', 140)
GO

INSERT INTO [dbo].[unit] ([id_unit] ,[date_created] ,[last_updated] ,[name] ,[departament_id]) 
VALUES (141,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Sub Unidad Tecnica Vial', 140)
GO

INSERT INTO [dbo].[user] ([id_user],[boss],[date_created],[email],[last_name1],[last_name2],
[last_updated],[name],[password_encrypt],[role],[state],[telephone],[departament_id],[unit_id])
VALUES ('555555555', 0, CURRENT_TIMESTAMP, 'kevin.salazar@sanisidro.go.cr', 'Salazar', 'Zamora', CURRENT_TIMESTAMP, 
'Kevin', '$2a$10$GTFWRtguwk3coxsmfaNzx.U8e57a9VAJObsmX.DUtStTUB9ASRrAC', 0, 1, '55555555', 140, 140) -- Password Kev123
GO
