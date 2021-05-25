package gestioescandalls.Model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PLATS")
public class Plat {
    @Id
    @Column(name="PLAT_ID")
    private long id;
    
    private String nom;
    @Column(name="DESCRIPCIO_MD")
    private String descripcio;
    private double preu;
    private boolean disponible;
    
    @ManyToOne()
    @JoinColumn(name = "CATEGORIA")
    private Categoria categoria;
    
    @OneToMany(mappedBy = "plat")
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

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public void setDescripcio(String descripcio) {
//        this.descripcio = descripcio;
//    }
//
//    public void setPreu(double preu) {
//        this.preu = preu;
//    }
//
//    public void setDisponible(boolean disponible) {
//        this.disponible = disponible;
//    }
//
//    public void setCategoria(Categoria categoria) {
//        this.categoria = categoria;
//    }

    @Override
    public String toString() {
        return nom;
    }
    
    
    
}
