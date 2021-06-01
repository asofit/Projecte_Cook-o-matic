package model;

import java.io.Serializable;

public class Taula implements Serializable{
    public int taula_id;
    public int comanda_activa_id;
    public int plats_totals;
    public int plats_preparats;
    public String nom_cambrer;
    public boolean es_meva;

    public Taula() {
    }

    @Override
    public String toString() {
        return "Taula{" + "taula_id=" + taula_id + ", comanda_activa_id=" + comanda_activa_id + ", plats_totals=" + plats_totals + ", plats_preparats=" + plats_preparats + ", nom_cambrer=" + nom_cambrer + ", es_meva=" + es_meva + '}';
    }    
}
