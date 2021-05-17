-- DATA

-- TABLES
DROP TABLE IF EXISTS LINIES_COMANDA CASCADE;
ALTER TABLE TAULES DROP CONSTRAINT `TAULES_FK_COMANDES`;
DROP TABLE IF EXISTS COMANDES CASCADE;
DROP TABLE IF EXISTS TAULES CASCADE;
DROP TABLE IF EXISTS CAMBRERS CASCADE;

DROP TABLE IF EXISTS LINIES_ESCANDALL CASCADE;
DROP TABLE IF EXISTS PLATS CASCADE;
DROP TABLE IF EXISTS UNITATS CASCADE;
DROP TABLE IF EXISTS INGREDIENTS CASCADE;
DROP TABLE IF EXISTS CATEGORIES CASCADE;

-- SCHEMA
DROP SCHEMA IF EXISTS COOK_O_MATIC_BD;