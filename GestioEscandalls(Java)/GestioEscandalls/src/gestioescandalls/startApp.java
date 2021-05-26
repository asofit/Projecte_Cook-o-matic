package gestioescandalls;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class startApp {
    
    private static String up;
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Aquesta app necessita un argument amb la ruta al fitxer de propietats");
            System.exit(1);
        }
        
        String propertiesFileUrl = args[0];
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        HashMap<String,String> emProperties = readPropertiesFile(propertiesFileUrl);
        
        try {
            emf = Persistence.createEntityManagerFactory(up, emProperties);
            em = emf.createEntityManager();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        }
        
        GestioEscandalls ui = new GestioEscandalls(em);
    }
    
    private static HashMap<String,String> readPropertiesFile(String propertiesFileUrl) {
        Properties p = new Properties();
        
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propertiesFileUrl);
            p.load(fis);
        } catch (FileNotFoundException ex) {
            System.out.println("Path al fitxer de propietats incorrecte.");
            System.exit(2);
        } catch (IOException ex) {
            System.out.println("No ha sigut possible llegir el fitxer de propietats.");
            System.exit(3);
        }finally{
            if (fis != null) try {
                fis.close();
            } catch (IOException ex) {
                System.out.println("No ha sigut possible tancar l'accés al fitxer de propietats.");
                System.exit(3);
            }
        }
        
        up = p.getProperty("up");
        String url = p.getProperty("dbtype")+"//"+p.getProperty("host")+"/"+p.getProperty("dbname");

        HashMap<String,String> emProperties = new HashMap<String,String>();
        emProperties.put("javax.persistence.jdbc.url", url);
        emProperties.put("javax.persistence.jdbc.user", p.getProperty("user"));
        emProperties.put("javax.persistence.jdbc.password", p.getProperty("password"));
        emProperties.put("javax.persistence.jdbc.driver", p.getProperty("driver"));
        String dialect = p.getProperty("dialect");
        if (dialect != null && dialect.length() > 0) emProperties.put("hibernate.dialect", dialect);   

        return emProperties;
    }
}
