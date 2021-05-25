package gestioescandalls.Model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LINIES_ESCANDALL")
public class LiniaEscandall implements Serializable{
    
    @Id
    @ManyToOne()
    @JoinColumn(name = "PLAT")
    private Plat plat;
    @Id
    @Column(name="LINIA_ESC_ID")
    private int id;
    private int quantitat;

    public LiniaEscandall() {
    }

    public Plat getPlat() {
        return plat;
    }

    public int getId() {
        return id;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.plat);
        hash = 89 * hash + this.id;
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
        final LiniaEscandall other = (LiniaEscandall) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.plat, other.plat)) {
            return false;
        }
        return true;
    }
    
    
}
