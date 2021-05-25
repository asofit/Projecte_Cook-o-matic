package gestioescandalls.Model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UNITATS")
public class Unitat implements Serializable{
    
    @Id
    private long id;
    private String nom;

    public Unitat() {
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
