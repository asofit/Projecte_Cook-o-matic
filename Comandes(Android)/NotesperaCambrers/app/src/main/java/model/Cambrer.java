package model;

import java.io.Serializable;

public class Cambrer implements Serializable{
//    	CAMBRER_ID 			SMALLINT (3) 	CHECK ( CAMBRER_ID > 0 AND CAMBRER_ID < 999),
//	NOM     			VARCHAR(15)		NOT NULL,
//	COGNOM1     		VARCHAR(15)		NOT NULL,
//	COGNOM2     		VARCHAR(15),
//	USUARI				VARCHAR(15) 	NOT NULL,
//	CONTRASENYA			VARCHAR(15) 	NOT NULL,
    public int cambrer_id;
    public String nom;
    public String cognom1;
    public String cognom2;
    public String usuari;
    public String contrasenya;

    @Override
    public String toString() {
        return "Cambrer{" + "cambrer_id=" + cambrer_id + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", usuari=" + usuari + ", contrasenya=" + contrasenya + '}';
    }
    
    
}
