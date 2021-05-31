package gestioescandalls.Model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LINIES_ESCANDALL")
public class LiniaEscandall implements Serializable, Comparable<LiniaEscandall>{

    
    @Id
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PLAT")
    private Plat plat;
//    private int plat;
    @Id
    @Column(name="LINIA_ESC_ID")
    private int id;
    private int quantitat;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "INGREDIENT")
    private Ingredient ingredient;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "UNITAT")
    private Unitat unitat;

    public LiniaEscandall() {
    }

    public Plat getPlat() {
        return plat;
    }
//    public int getPlat() {
//        return plat;
//    }

    public int getId() {
        return id;
    }

    public int getQuantitat() {
        return quantitat;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }

    public Unitat getUnitat() {
        return unitat;
    }

//    public void setPlat(int plat) {
//        this.plat = plat;
//    }
    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setUnitat(Unitat unitat) {
        this.unitat = unitat;
    }
    public static int getLastIndexOfList(List<LiniaEscandall> escandall) {
        int index = 0;
        for (int i = 0; i < escandall.size(); i++){
            if (escandall.get(i).getId() > index) index = escandall.get(i).getId();
        }
        return index;
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

    @Override
    public String toString() {
        return quantitat + " " +  unitat + " de " + ingredient;
    }

    @Override
    public int compareTo(LiniaEscandall otherLe) {
        int platDiff = Long.compare(this.plat.getId(), otherLe.getPlat().getId());
//        int platDiff = Long.compare(this.plat, otherLe.getPlat());
        if (platDiff == 0){
            return Integer.compare(this.id, otherLe.getId());
        }
        else{
            return platDiff;
        }
    }    
    
    // CRUD de Línies d'Escandall
    public static boolean insertaLiniaEscandall(EntityManager em, LiniaEscandall le){
        em.getTransaction().begin();
        try {
            em.persist(le);
            em.getTransaction().commit();
            return true;
        } 
        catch (EntityExistsException eee) {
            System.out.println(eee);
            em.getTransaction().rollback();
        }
        return false;
    }
    public static boolean eliminaLiniaEscandall(EntityManager em, LiniaEscandall le){
        em.getTransaction().begin();
        try {
            if (!em.contains(le)){
                em.merge(le);
            }
          em.remove(le);
//            String consulta = "delete from LINIES_ESCANDALL where PLAT = :platid and LINIA_ESC_ID = :liniaid";
//            em.createNativeQuery(consulta).setParameter("platid", le.getPlat().getId()).setParameter("liniaid", le.getId()).executeUpdate();
            em.getTransaction().commit();
            return true;
        } 
        catch (EntityExistsException eee) {
            System.out.println(eee);
            em.getTransaction().rollback();
        }
        return false;
    }
}
