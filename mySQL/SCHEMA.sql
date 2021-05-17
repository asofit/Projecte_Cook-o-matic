-- Creació de l'esquema, per si encara no el tenim o l'hem esborrat
CREATE SCHEMA COOK_O_MATIC_BD;
USE COOK_O_MATIC_BD;

-- Primer creem les taules d'informació al voltant del plat
-- Creem primer les tres taules amb dades estàtiques, que no tenen claus foranes
CREATE TABLE CATEGORIES (
	CATEGORIA_ID 		MEDIUMINT(7) 	CHECK ( CATEGORIA_ID >= 0 AND CATEGORIA_ID < 9999999),      
	NOM     			VARCHAR(25) 	NOT NULL ,
	COLOR				VARCHAR(6),

	PRIMARY KEY(CATEGORIA_ID)
);

CREATE TABLE INGREDIENTS (
	INGREDIENT_ID 		MEDIUMINT(7) 	CHECK ( INGREDIENT_ID >= 0 AND INGREDIENT_ID < 9999999),      
	NOM     			VARCHAR(25) 	NOT NULL ,

	PRIMARY KEY(INGREDIENT_ID)
);

CREATE TABLE UNITATS (
	UNITAT_ID 			MEDIUMINT(7) 	CHECK ( UNITAT_ID >= 0 AND UNITAT_ID < 9999999),      
	NOM     			VARCHAR(25) 	NOT NULL ,

	PRIMARY KEY(UNITAT_ID)
);

-- Creem la taula de plats, que només referencia "CATEGORIES"
CREATE TABLE PLATS (
	PLAT_ID   		MEDIUMINT(7) 	CHECK ( PLAT_ID >= 0 AND PLAT_ID < 9999999),           
	NOM				VARCHAR(50)		NOT NULL,
	DESCRIPCIO_MD 	TEXT,
	PREU   			DECIMAL(15,2),
	FOTO			MEDIUMBLOB,
	DISPONIBLE		BOOLEAN			NOT NULL,
	CATEGORIA		MEDIUMINT(7),
	
	PRIMARY KEY (PLAT_ID),
	CONSTRAINT `PLATS_FK_CATEGORIES` FOREIGN KEY (CATEGORIA) REFERENCES CATEGORIES (CATEGORIA_ID)  ON DELETE RESTRICT
);

-- Creem la taula de línies de l'escandall, que referencia "PLATS", "INGREDIENTS" i "UNITATS"
CREATE TABLE LINIES_ESCANDALL (
	LINIA_ESC_ID   	SMALLINT(2) 	CHECK ( LINIA_ESC_ID >= 0 AND LINIA_ESC_ID < 99),   
	PLAT			MEDIUMINT(7)	NOT NULL,
	QUANTITAT		SMALLINT(5),
	INGREDIENT		MEDIUMINT(7)	NOT NULL,
	UNITAT			MEDIUMINT(7)	NOT NULL,	
	
	PRIMARY KEY (PLAT,LINIA_ESC_ID),
	CONSTRAINT `LINIES_ESCANDALL_FK_PLATS` FOREIGN KEY (PLAT) REFERENCES PLATS (PLAT_ID) ON DELETE RESTRICT,
	CONSTRAINT `LINIES_ESCANDALL_FK_INGREDIENTS` FOREIGN KEY (INGREDIENT) REFERENCES INGREDIENTS (INGREDIENT_ID) ON DELETE RESTRICT,
	CONSTRAINT `LINIES_ESCANDALL_FK_UNITATS` FOREIGN KEY (UNITAT) REFERENCES UNITATS (UNITAT_ID) ON DELETE RESTRICT
);

-- Finalment, creem les taules referents a les comandes
-- Creem primer les taules que no tenen claus foranes
CREATE TABLE CAMBRERS (
	CAMBRER_ID 			SMALLINT (3) 	CHECK ( CAMBRER_ID >= 0 AND CAMBRER_ID < 999),
	NOM     			VARCHAR(15)		NOT NULL,
	COGNOM2     		VARCHAR(15)		NOT NULL,
	COGNOM1     		VARCHAR(15),
	USUARI				VARCHAR(15) 	NOT NULL,
	CONTRASENYA			VARCHAR(15) 	NOT NULL,

	PRIMARY KEY(CAMBRER_ID)
);

-- Creem les taules "TAULES" i "COMANDES" que es referencien mútuament
CREATE TABLE TAULES (
	TAULA_ID 			SMALLINT (3) CHECK ( TAULA_ID >= 0 AND TAULA_ID < 999),
	COMANDA_ACTIVA		MEDIUMINT(7),

	PRIMARY KEY(TAULA_ID)
);

CREATE TABLE COMANDES (
	COMANDA_ID   	MEDIUMINT(7) 	CHECK ( COMANDA_ID >= 0 AND COMANDA_ID < 9999999),           
	DATA_COMANDA	DATE			NOT NULL,
	TAULA			SMALLINT (3),
	
	PRIMARY KEY (COMANDA_ID),
	CONSTRAINT `COMANDES_FK_TAULA` FOREIGN KEY (TAULA) REFERENCES TAULES (TAULA_ID)
);

ALTER TABLE TAULES ADD CONSTRAINT `TAULES_FK_COMANDES` FOREIGN KEY (COMANDA_ACTIVA) REFERENCES COMANDES (COMANDA_ID);

-- Per últim, creem la taula per les línies de comanda, que referencia "PLATS" i "COMANDES"
CREATE TABLE LINIES_COMANDA (
	LINIA_COM_ID   	SMALLINT(2) 	CHECK ( LINIA_COM_ID >= 0 AND LINIA_COM_ID < 99),   
	COMANDA			MEDIUMINT(7)	NOT NULL,
	PLAT			MEDIUMINT(7),
	QUANTITAT		SMALLINT(3),
	ESTAT			ENUM('EN_PREPARACIO','PREPARADA'),
	
	PRIMARY KEY (COMANDA,LINIA_COM_ID),
	CONSTRAINT `LINIES_COMANDA_FK_COMANDES` FOREIGN KEY (COMANDA) REFERENCES COMANDES (COMANDA_ID) ON DELETE RESTRICT,
	CONSTRAINT `LINIES_COMANDA_FK_PLATS` FOREIGN KEY (PLAT) REFERENCES PLATS (PLAT_ID) ON DELETE RESTRICT
);