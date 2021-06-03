package model;

import java.io.Serializable;

public class LiniaComanda implements Serializable{
    
    public int num;
    public int quantitat;
    public int codi_plat;

    public LiniaComanda() {
    }

    @Override
    public String toString() {
        return "LiniaComanda{" + "num=" + num + ", quantitat=" + quantitat + ", codi_plat=" + codi_plat + '}';
    }
    
}
