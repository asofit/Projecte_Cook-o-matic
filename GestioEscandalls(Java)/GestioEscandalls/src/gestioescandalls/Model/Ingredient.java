
package gestioescandalls.Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INGREDIENTS")
public class Ingredient implements Serializable{
    
    @Id
    @Column(name="INGREDIENT_ID")
    private long id;
    @Basic(optional = false)
    @Column(nullable = false, length=25, name="NOM")
    private String nom;

    public Ingredient() {
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

    @Override
    public String toString() {
        return nom;
    }
    
}
