package clientdummy;

import model.Cambrer;
import model.Login;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categoria;
import model.LiniaComanda;
import model.Plat;
import model.Taula;



public class ClientDummy {


    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 9876);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

//            login(oos, socket);
            
//            getTaules(oos, socket);

//            getCarta(oos, socket);
//            getComanda(oos, socket);
//            createComanda(oos,socket);
//            buidarTaula(oos,socket);
            
            oos.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
//        catch (ClassNotFoundException ex) {
//            Logger.getLogger(ClientDummy.class.getName()).log(Level.SEVERE, null, ex);
//        } 
    }

    private static void getTaules(ObjectOutputStream oos, Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("Escrivim opció");
        oos.writeInt(2);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            System.out.println("Anem a enviar dades ");
            oos.writeInt(1000);
            oos.flush();
            System.out.println("Volem llegir dades");
            
            int totalTaules = 0;
            List<Taula> taules = null;
            totalTaules = ois.readInt();
            taules = (ArrayList<Taula>)ois.readObject();
            if (taules != null){
                System.out.println("Hem llegit dades");
                for (Taula t : taules) {
                    System.out.println(t);
                }
            }
            else{
                System.out.println("Error");
            }
        }
        else{
            System.out.println("Error");
            System.out.println(ois.readUTF());
        }
        ois.close();
    }

    private static void login(ObjectOutputStream oos, Socket socket) throws IOException {
        System.out.println("Escrivim opció");
        oos.writeInt(1);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            Login l = new Login("befa", "BeFa");
            System.out.println("Anem a enviar dades "+l.user+l.password);
            oos.writeObject(l);
            oos.flush();
            System.out.println("Volem llegir dades");
            
            l = null;
            try {
                l = (Login)ois.readObject();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
            System.out.println("Hem llegit dades");
            System.out.println(l);
        }
        else{
            System.out.println("Error");
            System.out.println(ois.readUTF());
        }
        ois.close();
    }

    private static void getCarta(ObjectOutputStream oos, Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("Escrivim opció");
        oos.writeInt(3);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            System.out.println("Anem a enviar dades ");
            oos.writeInt(1000);
            oos.flush();
            System.out.println("Volem llegir dades");
            
            int totalCategories = 0;
            List<Categoria> categories = null;
            totalCategories = ois.readInt();
            categories = (ArrayList<Categoria>)ois.readObject();
            if (categories != null){
                System.out.println("Hem llegit dades");
                for (Categoria c : categories) {
                    System.out.println(c);
                }

            }
            else{
                System.out.println("Error");
            }
            
            int totalPlats = 0;
            List<Plat> plats = null;
            totalPlats = ois.readInt();
            plats = (ArrayList<Plat>)ois.readObject();
            if (plats != null){
                System.out.println("Hem llegit dades");
                for (Plat p : plats) {
                    System.out.println(p);
                }
            }
            else{
                System.out.println("Error");
            }
        }
        else{
            System.out.println("Error");
            System.out.println(ois.readUTF());
        }
        ois.close();
    }
    
    private static void getComanda(ObjectOutputStream oos, Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("Escrivim opció");
        oos.writeInt(4);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            System.out.println("Anem a enviar sessió ");
            oos.writeInt(1000);
            oos.flush();
            System.out.println("Anem a enviar comanda ");
            oos.writeInt(3);
            oos.flush();
            System.out.println("Volem llegir dades");
            
            int totalLinies = 0;
            List<LiniaComanda> linies = null;
            totalLinies = ois.readInt();
            linies = (ArrayList<LiniaComanda>)ois.readObject();
            if (linies != null){
                System.out.println("Hem llegit dades");
                for (LiniaComanda lc : linies) {
                    System.out.println(lc);
                }

            }
            else{
                System.out.println("Error");
            }
        }
        else{
            System.out.println("Error");
            System.out.println(ois.readUTF());
        }
        ois.close();
    }

    private static void createComanda(ObjectOutputStream oos, Socket socket) throws IOException {
        System.out.println("Escrivim opció");
        oos.writeInt(5);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            System.out.println("Anem a enviar sessió ");
            oos.writeInt(1000);
            oos.flush();
            System.out.println("Anem a enviar taula ");
            Taula t = new Taula();
            t.taula_id = 6;
            oos.writeObject(t);
            oos.flush();
            
            ArrayList<LiniaComanda> linies = new ArrayList<LiniaComanda>();
            for (int i = 1; i <= 5; i++){
                LiniaComanda lc = new LiniaComanda();
                lc.num =  i;
                lc.quantitat = 3;
                lc.codi_plat = i+6;
                linies.add(lc);
            }
            
            System.out.println("Anem a enviar total de línies ");
            oos.writeInt(linies.size());
            oos.flush();
            System.out.println("Anem a enviar línies ");
            oos.writeObject(linies);
            oos.flush();
            
            System.out.println("Volem llegir dades");
            int comandaId = ois.readInt();
            if (comandaId > 0){
                System.out.println("Hem llegit dades");
                System.out.println(comandaId);
            }
            else{
                System.out.println("Error");
            }
        }
        else{
            System.out.println("Error");
        }
        ois.close();
    }

    private static void buidarTaula(ObjectOutputStream oos, Socket socket) throws IOException {
        System.out.println("Escrivim opció");
        oos.writeInt(6);
        oos.flush();
        System.out.println("Comprovem si és correcte");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (ois.readBoolean())
        {
            System.out.println("Anem a enviar sessió ");
            oos.writeInt(1000);
            oos.flush();
            System.out.println("Anem a enviar taula ");
            Taula t = new Taula();
            t.taula_id = 6;
            oos.writeObject(t);
            oos.flush();            
            
            System.out.println("Volem llegir dades");
            int result = ois.readInt();
            if (result == 0){
                System.out.println("Hem llegit dades");
                System.out.println(result);
            }
            else{
                System.out.println("Error");
            }
        }
        else{
            System.out.println("Error");
        }
        ois.close();
    }    
}
