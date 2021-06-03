package model;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BDUtils {
    private static final int PORT = 9876;
    private static final String HOST = "192.168.1.24";

    private static final int LOGIN = 1;
    private static final int GET_TAULES = 2;
    private static final int GET_COMANDA = 4;

    public static Login login(String user, String password) {
        try {
            InetAddress host = InetAddress.getByName(HOST);
            Socket socket = new Socket(host.getHostName(), PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Log.d("stack","Escrivim opció");
            oos.writeInt(LOGIN);
            oos.flush();
            Log.d("stack","Comprovem si és correcte");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            if (ois.readBoolean())
            {
                Login l = new Login(user, password);
                Log.d("stack","Anem a enviar dades "+l.user+l.password);
                oos.writeObject(l);
                oos.flush();
                Log.d("stack","Volem llegir dades");

                l = null;
                try {
                    l = (Login)ois.readObject();
                } catch (ClassNotFoundException ex) {
                    Log.d("error","No es pot llegir Login");
                }
                Log.d("stack","Hem llegit dades");
                return l;
            }
            else{
                Log.d("error","Funció no implementada");
                Log.d("error",ois.readUTF());
            }
            ois.close();
            oos.close();

        } catch (IOException ex) {
            Log.d("error","try catch original"+ex.getClass());
        }
        return null;
    }
    public static ArrayList<Taula> getTaules(int sessionId){
        try{
            InetAddress host = InetAddress.getByName(HOST);
            Socket socket = new Socket(host.getHostName(), PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Escrivim opció");
            oos.writeInt(GET_TAULES);
            oos.flush();
            System.out.println("Comprovem si és correcte");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            if (ois.readBoolean())
            {
                System.out.println("Anem a enviar dades ");
                oos.writeInt(sessionId);
                oos.flush();
                System.out.println("Volem llegir dades");

                int totalTaules = 0;
                ArrayList<Taula> taules = null;
                totalTaules = ois.readInt();
                taules = (ArrayList<Taula>)ois.readObject();
                if (taules != null){
                    System.out.println("Hem llegit dades");
                    return taules;
                }
                else{
                    Log.d("error","Error");
                }
            }
            else{
                Log.d("error","Error");
                Log.d("error",ois.readUTF());
            }
            ois.close();
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<LiniaComanda> getComanda(int sessionId, int comandaId) {
        try {
            InetAddress host = InetAddress.getByName(HOST);
            Socket socket = new Socket(host.getHostName(), PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Escrivim opció");
            oos.writeInt(GET_COMANDA);
            oos.flush();
            System.out.println("Comprovem si és correcte");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            if (ois.readBoolean()) {
                System.out.println("Anem a enviar sessió ");
                oos.writeInt(sessionId);
                oos.flush();
                System.out.println("Anem a enviar comanda ");
                oos.writeInt(comandaId);
                oos.flush();
                System.out.println("Volem llegir dades");

                int totalLinies = 0;
                ArrayList<LiniaComanda> linies = null;
                totalLinies = ois.readInt();
                linies = (ArrayList<LiniaComanda>) ois.readObject();
                return linies;
            } else {
                System.out.println("Error");
                System.out.println(ois.readUTF());
            }
            ois.close();
        } catch (IOException ex) {
            Log.d("error",ex.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d("error",e.getMessage());
        }
        return null;
    }
}
