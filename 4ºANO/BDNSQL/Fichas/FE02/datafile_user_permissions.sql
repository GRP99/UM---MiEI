--1)

--Criar Datafiles:
------------------------------------------------------------------------------------
CREATE TABLESPACE sensors_tables DATAFILE 'sensors_tables_01.dbf' SIZE 200m;

--Criar User:
------------------------------------------------------------------------------------
CREATE USER sensor IDENTIFIED BY "sensor2020" DEFAULT TABLESPACE sensors_tables QUOTA
UNLIMITED ON sensors_tables ;

--Permissões e Roles:
------------------------------------------------------------------------------------
GRANT CONNECT,RESOURCE,CREATE VIEW,CREATE SEQUENCE TO sensor;
------------------------------------------------------------------------------------
