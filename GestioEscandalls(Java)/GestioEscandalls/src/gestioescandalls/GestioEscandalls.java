package gestioescandalls;

import gestioescandalls.Model.Categoria;
import gestioescandalls.Model.Plat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GestioEscandalls {

    public GestioEscandalls(EntityManager em) {
        String consulta = "select p from Plat p";
        Query q = em.createQuery(consulta);
        List<Plat> plats = q.getResultList();
        for (int i = 0; i < plats.size(); i++){
            System.out.println(plats.get(i));
        }
    }

    
    
}
