package gestioescandalls.Model;

import java.util.List;
import javax.persistence.CascadeType;
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
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORIA")
    private Categoria categoria;
    
    @OneToMany(mappedBy = "plat", cascade = CascadeType.ALL)
    private List<LiniaEscandall> escandall;


    public Plat() {
    }
    public List<LiniaEscandall> getEscandall() {
        return escandall;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Plat other = (Plat) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nom;
    }
    
    
    
}
