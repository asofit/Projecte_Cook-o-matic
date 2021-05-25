package gestioescandalls.Model;

import java.util.List;

public class Plat {
    private long id;
    private String nom;
    private String descripcio;
    private double preu;
    private String imageLocalUrl;
    private boolean disponible;
    private Categoria categoria;
    private List<LiniaEscandall> escandall;

    public Plat() {
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public double getPreu() {
        return preu;
    }

    public String getImageLocalUrl() {
        return imageLocalUrl;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public void setImageLocalUrl(String imageLocalUrl) {
        this.imageLocalUrl = imageLocalUrl;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
    
}
