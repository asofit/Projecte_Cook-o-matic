package gestioescandalls;

import gestioescandalls.Model.*;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JFrame;

public class GestioEscandalls {

    // Elements de UI
    JFrame mainFrame;
    // Llistes
    private List<Plat> plats;
    private List<Categoria> categories;
    private List<Unitat> unitats;
    private List<Ingredient> ingredients;
    
    public GestioEscandalls(EntityManager em) {
        refrescaLlistes(em);
        mainFrame = new JFrame("Gestió d'Escandalls");
        
//        LiniaEscandall le = plats.get(0).getEscandall().get(3);
//        em.getTransaction().begin();
//        System.out.println(eliminaLiniaEscandall(em, le));
//        em.getTransaction().commit();
//        em.getTransaction().begin();
//        refrescaLlistes(em);
//        le = plats.get(0).getEscandall().get(3);
//        em.getTransaction().commit();
    }
    // CRUD de Línies d'Escandall
    private boolean insertaLiniaEscandall(EntityManager em, LiniaEscandall le){
        try {
            em.persist(le);
            em.getTransaction().commit();
            return true;
        } 
        catch (EntityExistsException eee) {
            System.out.println(eee);
        }
        return false;
    }
    private boolean eliminaLiniaEscandall(EntityManager em, LiniaEscandall le){
        try {
            if (!em.contains(le)){
                em.merge(le);
            }
//          em.remove(le);
            String consulta = "delete from LINIES_ESCANDALL where PLAT = :platid and LINIA_ESC_ID = :liniaid";
            em.createNativeQuery(consulta).setParameter("platid", le.getPlat().getId()).setParameter("liniaid", le.getId()).executeUpdate();;

            return true;
        } 
        catch (EntityExistsException eee) {
            System.out.println(eee);
        }
        return false;
    }
    // Selects de BD
    private void refrescaLlistes(EntityManager em) {
        // Obtenim les llistes necessàries
        plats = getAllPlats(em);
        categories = getAllCategories(em);
        unitats = getAllUnitats(em);
        ingredients = getAllIngredients(em);
    }
    private List<Categoria> getAllCategories(EntityManager em) {
        String consulta = "select c from Categoria c";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Plat> getAllPlats(EntityManager em) {
        String consulta = "select p from Plat p";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Unitat> getAllUnitats(EntityManager em) {
        String consulta = "select u from Unitat u";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Ingredient> getAllIngredients(EntityManager em) {
        String consulta = "select i from Ingredient i";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }

    
    
}
