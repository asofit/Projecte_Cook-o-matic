USE COOK_O_MATIC_BD;

-- Esborrem tot el contingut per tornar a posar-lo
DELETE FROM PLATS;
DELETE FROM CATEGORIES;
DELETE FROM UNITATS;
DELETE FROM INGREDIENTS;

-- Primer insertem les dades estàtiques
INSERT INTO CATEGORIES VALUES (0,'Gelats','8d03ff');
INSERT INTO CATEGORIES VALUES (1,'Entrants','9aed6d');
INSERT INTO CATEGORIES VALUES (2,'Primers','fcbc32');
INSERT INTO CATEGORIES VALUES (3,'Segons','dbd818');
INSERT INTO CATEGORIES VALUES (4,'Postres','de1670');
INSERT INTO CATEGORIES VALUES (5,'Amanides','00ff15');
INSERT INTO CATEGORIES VALUES (6,'Carn','eb4034');
INSERT INTO CATEGORIES VALUES (7,'Peix','170ceb');
INSERT INTO CATEGORIES VALUES (8,'Arrossos','aceb0c');
INSERT INTO CATEGORIES VALUES (9,'Pasta','fcf800');


-- Insertem els plats, que fan referència a Categories
INSERT INTO PLATS VALUES (0,'Combinat de salmó','**Salmó amb cosetes**',	15.55,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/10.jpg'), 	FALSE,3);
INSERT INTO PLATS VALUES (1,'Mini-burguers','', 							5.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/1.jpg'),		TRUE, 1);
INSERT INTO PLATS VALUES (2,'Crudités de tomàquet, carbassó i ou dur','', 	3.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/2.jpg'),		TRUE, 1);
INSERT INTO PLATS VALUES (3,'Ous farcits','', 								6.3,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/3.jpg'),		TRUE, 1);
INSERT INTO PLATS VALUES (4,'Bunyols d''arròs','', 							8.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/4.jpg'),		FALSE,1);
INSERT INTO PLATS VALUES (5,'Macarrons','', 								4.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/5.jpg'),		TRUE, 2);
INSERT INTO PLATS VALUES (6,'Sopa de carbassó','', 							8.75,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/6.jpg'),	TRUE, 2);
INSERT INTO PLATS VALUES (7,'Sopa gratinada','', 							10.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/7.jpg'),	FALSE,2);
INSERT INTO PLATS VALUES (8,'Escalopa amb patates','', 						8.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/8.jpg'),		TRUE, 3);
INSERT INTO PLATS VALUES (9,'Pollastre al curry','', 						10.25,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/9.jpg'),	TRUE, 3);
INSERT INTO PLATS VALUES (10,'Llom de lluç a la planxa','',					11.25,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/20.jpg'), 	TRUE, 7);
INSERT INTO PLATS VALUES (11,'Amanida de pasta','',							7.25,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/11.jpg'), 	TRUE, 5);
INSERT INTO PLATS VALUES (12,'Pastís de llimona','',						6.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/12.jpg'), 	FALSE,4);
INSERT INTO PLATS VALUES (13,'Ensaladilla Russa','',						7.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/13.jpg'), 	TRUE, 5);
INSERT INTO PLATS VALUES (14,'Macedònia','',								4.75,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/14.jpg'), 	FALSE,4);
INSERT INTO PLATS VALUES (15,'Llonza de porc a la planxa','',				15.75,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/15.jpg'), 	TRUE, 6);
INSERT INTO PLATS VALUES (16,'Pollastre al forn','',						20.25,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/16.jpg'), 	TRUE, 6);
INSERT INTO PLATS VALUES (17,'Pit de pollastre amb la seva salsa','',		12.4,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/17.jpg'), 	TRUE, 6);
INSERT INTO PLATS VALUES (18,'Lligat de porc farcit','',					22.25,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/18.jpg'), 	TRUE, 6);
INSERT INTO PLATS VALUES (19,'Llom de salmó a la planxa','',				12.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/19.jpg'), 	TRUE, 7);
INSERT INTO PLATS VALUES (20,'Gelat fregit','',								10.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/30.jpg'), 	TRUE, 0);
INSERT INTO PLATS VALUES (21,'Salmó-lluç al forn','',						15.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/21.jpeg'), 	FALSE,7);
INSERT INTO PLATS VALUES (22,'Arròs blanc','',								5.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/22.jpg'), 	TRUE, 8);
INSERT INTO PLATS VALUES (23,'Arròs mexicà','',								10.3,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/23.jpg'), 	TRUE, 8);
INSERT INTO PLATS VALUES (24,'Gelat de xocolata','',						5.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/24.jpg'), 	TRUE, 0);
INSERT INTO PLATS VALUES (25,'Gelat de maduixa','',							5.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/25.jpg'), 	TRUE, 0);
INSERT INTO PLATS VALUES (26,'Gelat de tè matcha','',						8.5,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/26.jpeg'), 	TRUE, 0);
INSERT INTO PLATS VALUES (27,'Gelat de llimona','',							5.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/27.jpg'), 	FALSE,0);
INSERT INTO PLATS VALUES (28,'Gelat de menta','',							7.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/28.jpg'), 	TRUE, 0);
INSERT INTO PLATS VALUES (29,'Gelat de raïm','',							8.0,	LOAD_FILE('D:/Documents/DAM2/Projecte/mySQL/Imatges/29.jpg'), 	TRUE, 0);

-- Insertem les taules estàtiques Unitats i Ingredients, a les que fa referència Línies d'Escandall
INSERT INTO UNITATS VALUES (0,'Mil·lilitres');
INSERT INTO UNITATS VALUES (1,'Grams');
INSERT INTO UNITATS VALUES (2,'Quilograms');
INSERT INTO UNITATS VALUES (3,'Mil·ligrams');
INSERT INTO UNITATS VALUES (4,'Unces');
INSERT INTO UNITATS VALUES (5,'Peces');
INSERT INTO UNITATS VALUES (6,'Litres');

INSERT INTO INGREDIENTS VALUES (0,'Tè matcha');
INSERT INTO INGREDIENTS VALUES (1,'Enciam');
INSERT INTO INGREDIENTS VALUES (2,'Arròs');
INSERT INTO INGREDIENTS VALUES (3,'Tomàquet');
INSERT INTO INGREDIENTS VALUES (4,'Mantega');
INSERT INTO INGREDIENTS VALUES (5,'Oli');
INSERT INTO INGREDIENTS VALUES (6,'Ceba');
INSERT INTO INGREDIENTS VALUES (7,'Pollastre');
INSERT INTO INGREDIENTS VALUES (8,'Porc');
INSERT INTO INGREDIENTS VALUES (9,'Julivert');
INSERT INTO INGREDIENTS VALUES (10,'All');
INSERT INTO INGREDIENTS VALUES (11,'Llenties');
INSERT INTO INGREDIENTS VALUES (12,'Pasta');
INSERT INTO INGREDIENTS VALUES (13,'Lluç');
INSERT INTO INGREDIENTS VALUES (14,'Salmó');
INSERT INTO INGREDIENTS VALUES (15,'Maduixes');
INSERT INTO INGREDIENTS VALUES (16,'Xocolata');
INSERT INTO INGREDIENTS VALUES (17,'Romaní');
INSERT INTO INGREDIENTS VALUES (18,'Sal');
INSERT INTO INGREDIENTS VALUES (19,'Sucre');
INSERT INTO INGREDIENTS VALUES (20,'Vi blanc');
INSERT INTO INGREDIENTS VALUES (21,'Vi negre');
INSERT INTO INGREDIENTS VALUES (22,'Llimona');
INSERT INTO INGREDIENTS VALUES (23,'Carbassó');
INSERT INTO INGREDIENTS VALUES (24,'Albergínia');
INSERT INTO INGREDIENTS VALUES (25,'Meló');
INSERT INTO INGREDIENTS VALUES (26,'Coliflor');
INSERT INTO INGREDIENTS VALUES (27,'Quinoa');
INSERT INTO INGREDIENTS VALUES (28,'Cigrons');
INSERT INTO INGREDIENTS VALUES (29,'Mongetes');
INSERT INTO INGREDIENTS VALUES (30,'Farina');
INSERT INTO INGREDIENTS VALUES (31,'Ous');
INSERT INTO INGREDIENTS VALUES (32,'Patata');