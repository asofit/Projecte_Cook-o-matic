/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import model.Cambrer;
import model.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import model.Categoria;
import model.LiniaComanda;
import model.Plat;
import model.Taula;

/**
 *
 * @author Usuario
 */
public class Server {

    private ServerSocket server = null;
    //socket server port on which it will listen
    private int port = 9876;
    private boolean on = true;

    public static JTextArea t;
    
    public List<Client> clients = new ArrayList<Client>();
    public static HashMap<Integer,Cambrer> sessionsActives  = new HashMap<Integer,Cambrer>();
    
    public Server() {
        JFrame f = new JFrame("Servidor Comandes");
        t = new JTextArea();
        t.setEditable(false);
        t.setSize(4000,4000);
        t.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        JScrollPane s = new JScrollPane(t);
        Dimension listSize = new Dimension(400, 400);
        s.setSize(listSize);
        s.setMaximumSize(listSize);
        s.setPreferredSize(listSize);
        Border m1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        Border m2 = BorderFactory.createEmptyBorder(0, 40, 5, 0);
        Border m3 = BorderFactory.createCompoundBorder(m2, m1);

        s.setBorder(m3);
        
        JPanel p = new JPanel();
        
        
//        s.add(t);
        s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JButton b = new JButton();
        b.setText("Iniciar");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (server == null){
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            on=true;
                            startServer();
                        }
                    });
                    t.start();
                }
            }
        });
        
        JButton bu = new JButton();
        bu.setText("Parar");
        bu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (server != null){
                    on = false;
                    for (int i = 0; i < clients.size(); i++){
                        clients.get(i).interrupt();
                    }
//                    System.exit(0);
                }
            }
        });
        
        p.add(b,BorderLayout.WEST);
        p.add(bu,BorderLayout.WEST);
        
        f.add(s, BorderLayout.PAGE_START);
        f.add(p,BorderLayout.SOUTH);        
        
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void startServer() {
        try {
            //create the socket server object
            server = new ServerSocket(port);
            //keep listens indefinitely until receives 'exit' call or program terminates
            while(on){
                putTextOnScreen("Esperant un client");
                //creating socket and waiting for client connection
                Socket socket = server.accept();
                //read from socket to ObjectInputStream object
                putTextOnScreen("Client " + clients.size() + " accepted.");
                //create ObjectOutputStream object
//                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                Client c = new Client(socket);
                clients.add(c);
                c.start();
            }
            putTextOnScreen("Shutting down Socket server!!");
            //close the ServerSocket object
            server.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void putTextOnScreen(String text) {
        t.setText(t.getText()+"\n"+text);
    }
    
    private class Client extends Thread{

        private Socket s; 
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        
        public Client(Socket _s) {
            s=_s;
        }
        
        @Override
        public void run() {
            int option = 0;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
                // Llegim opció
                putTextOnScreen("Llegim opció");
//                System.out.println("Llegim opció");
                option=ois.readInt();
                
                System.out.println(option);
                switch(option){
                    case 1:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        login();
                        break;
                    case 2:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        getTaules();
                        break;
                    case 3:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        getCarta();
                        break;
                    case 4:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        getComanda();
                        break;
                    case 5:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        createComanda();
                        break;
                    case 6:
                        putTextOnScreen("Donem ok");
//                        System.out.println("Donem ok");
                        oos.writeBoolean(true);
                        oos.flush();
                        buidarTaula();
                        break;
                    default:
                        oos.writeBoolean(false);
                        oos.writeUTF("Opció no contemplada");
                }
                ois.close();
                oos.close();
                s.close();
                clients.remove(this);
                super.interrupt();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        private void login() {
            try {
                putTextOnScreen("Llegim login");
//                System.out.println("Llegim login");
                Login l = (Login)ois.readObject();
                System.out.println(l.user + l.password);
                if (l.user==null || l.user.length()<= 0 || l.password == null){
                    oos.writeObject(null);
                    oos.flush();
                }
                else{
                    Connection conn = null;
                    try{
                        conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                        Statement stmt=conn.createStatement();  
                        String query = "select * from cambrers where usuari='"+l.user+"' and contrasenya='"+l.password+"'";
                        ResultSet rs=stmt.executeQuery(query);  
                        Cambrer c = null;
                        if(rs.next()){
                            c = new Cambrer();
                            c.cambrer_id = rs.getInt("CAMBRER_ID");
                            c.nom       = rs.getString("NOM");
                            c.cognom1   = rs.getString("COGNOM1");
                            c.cognom2   = rs.getString("COGNOM2");
                            c.usuari = l.user;
                            c.contrasenya = l.password;
                        }  
                        conn.close();  
                        
                        l.cambrer = c;
                        l.session_id = l.cambrer.cambrer_id*1000;
                        
                        Server.sessionsActives.put(l.session_id,c);
                        
                        oos.writeObject(l);
                        oos.flush();
                    }
                    catch(Exception e){
                        oos.writeObject(null);
                        oos.flush();
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }

        private void getTaules() {
            try {
                putTextOnScreen("Llegim sessió");
//                System.out.println("Llegim sessió");
                int sessio = ois.readInt();
                System.out.println(sessio);
                if (sessio <= 0){
                    oos.writeInt(0);
                    oos.flush();
                }
                else
                {
                    Cambrer c = Server.sessionsActives.get(sessio);
                    if (c==null){
                        oos.writeInt(0);
                        oos.flush();
                    }
                    else
                    {
                        Connection conn = null;
                        try{
                            conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                            Statement stmt=conn.createStatement();  
                            String query = "select t.*, (select count(lc.LINIA_COM_ID) from linies_comanda lc where lc.COMANDA = t.COMANDA_ACTIVA) as \"plats_totals\", (select count(lco.LINIA_COM_ID) from linies_comanda lco where lco.COMANDA = t.COMANDA_ACTIVA and lco.ESTAT='PREPARADA') as \"plats_preparats\", c.CAMBRER_ID as \"cambrer_id\", c.NOM as \"nom_cambrer\" from taules t left join cambrers c on c.CAMBRER_ID = (SELECT co.cambrer from comandes co where co.COMANDA_ID = t.COMANDA_ACTIVA)";
                            ResultSet rs=stmt.executeQuery(query);  
                            
                            List<Taula> taules = new ArrayList<Taula>();
                            while(rs.next()){
                                Taula t = new Taula();
                                t.taula_id = rs.getInt("TAULA_ID");
                                t.comanda_activa_id = rs.getInt("COMANDA_ACTIVA");
                                if (t.comanda_activa_id == 0) t.comanda_activa_id = -1;
                                t.plats_totals = rs.getInt("plats_totals");
                                t.plats_preparats = rs.getInt("plats_preparats");
                                t.nom_cambrer = rs.getString("nom_cambrer");
                                int cambrer_id = rs.getInt("cambrer_id");
                                if (c.cambrer_id == cambrer_id) t.es_meva = true;
                                taules.add(t);
                            }  
                            conn.close();  
                            
                            int totalTaules = taules.size();

                            oos.writeInt(totalTaules);
                            oos.flush();
                            oos.writeObject(taules);
                            oos.flush();
                        }
                        catch(Exception e){
                            oos.writeInt(0);
                            oos.flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        private void getCarta() {
            try {
                putTextOnScreen("Llegim sessió");
//                System.out.println("Llegim sessió");
                int sessio = ois.readInt();
                System.out.println(sessio);
                if (sessio <= 0){
                    oos.writeInt(0);
                    oos.flush();
                }
                else
                {
                    Cambrer c = Server.sessionsActives.get(sessio);
                    if (c==null){
                        oos.writeInt(0);
                        oos.flush();
                    }
                    else
                    {
                        Connection conn = null;
                        try{
                            conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                            Statement stmt=conn.createStatement();  
                            String query = "select * from categories";
                            ResultSet rs=stmt.executeQuery(query);  
                            
                            List<Categoria> categories = new ArrayList<Categoria>();
                            while(rs.next()){
                                Categoria categoria = new Categoria();
                                categoria.setCodi(rs.getInt("CATEGORIA_ID"));
                                categoria.setNom(rs.getString("NOM"));
                                categories.add(categoria);
                            }  
                            
                            int totalCategories = categories.size();

                            oos.writeInt(totalCategories);
                            oos.flush();
                            oos.writeObject(categories);
                            oos.flush();
                            
                            stmt=conn.createStatement();  
                            query = "select *, OCTET_LENGTH(foto) as 'mida_bytes_foto' from plats";
                            rs=stmt.executeQuery(query);  
                            
                            List<Plat> plats = new ArrayList<Plat>();
                            while(rs.next()){
                                Plat plat = new Plat();
                                plat.setCodi(rs.getInt("PLAT_ID"));
                                plat.setNom(rs.getString("NOM"));
                                plat.setPreu(rs.getDouble("PREU"));
                                plat.setMida_bytes_foto(rs.getInt("mida_bytes_foto"));
                                byte[] bytesFoto = new byte[plat.getMida_bytes_foto()];
                                
                                Blob b = rs.getBlob("FOTO");
                                InputStream is = b.getBinaryStream();
                                while(is.read(bytesFoto) > 0){
                                }
                                
                                plat.setStream_foto(bytesFoto);
                                
                                plats.add(plat);
                            }  
                            conn.close();  
                            
                            int totalPlats = plats.size();

                            oos.writeInt(totalPlats);
                            oos.flush();
                            oos.writeObject(plats);
                            oos.flush();
                            
                        }
                        catch(Exception e){
                            oos.writeInt(0);
                            oos.flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        private void getComanda() {
            try {
                putTextOnScreen("Llegim sessió");
//                System.out.println("Llegim sessió");
                int sessio = ois.readInt();
                System.out.println(sessio);
                putTextOnScreen("Llegim comanda");
//                System.out.println("Llegim comanda");
                int comanda_id = ois.readInt();
                System.out.println(comanda_id);
                if (sessio <= 0){
                    oos.writeInt(0);
                    oos.flush();
                }
                else
                {
                    Cambrer c = Server.sessionsActives.get(sessio);
                    if (c==null){
                        oos.writeInt(0);
                        oos.flush();
                    }
                    else
                    {
                        Connection conn = null;
                        try{
                            conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                            Statement stmt=conn.createStatement();  
                            String query = "SELECT * FROM linies_comanda where comanda="+comanda_id;
                            ResultSet rs=stmt.executeQuery(query);  
                            
                            List<LiniaComanda> linies = new ArrayList<LiniaComanda>();
                            while(rs.next()){
                                LiniaComanda lc = new LiniaComanda();
                                lc.num = rs.getInt("LINIA_COM_ID");
                                lc.quantitat = rs.getInt("QUANTITAT");
                                lc.codi_plat = rs.getInt("PLAT");
                                linies.add(lc);
                            }  
                            
                            int totalLinies = linies.size();

                            oos.writeInt(totalLinies);
                            oos.flush();
                            oos.writeObject(linies);
                            oos.flush();
                            
                        }
                        catch(Exception e){
                            oos.writeInt(0);
                            oos.flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        private void createComanda() {
            try {
                putTextOnScreen("Llegim sessió");
//                System.out.println("Llegim sessió");
                int sessio = ois.readInt();
                System.out.println(sessio);
                putTextOnScreen("Llegim taula");
//                System.out.println("Llegim comanda");
                Taula t = (Taula)ois.readObject();
                System.out.println(t);
                putTextOnScreen("Llegim num linies comanda");
//                System.out.println("Llegim comanda");
                int totalLinies = ois.readInt();
                System.out.println(totalLinies);
                putTextOnScreen("Llegim linies comanda");
//                System.out.println("Llegim comanda");
                ArrayList<LiniaComanda> linies = (ArrayList<LiniaComanda>)ois.readObject();
                if (linies != null){
                    putTextOnScreen("Hem llegit dades");
                    for (LiniaComanda lco : linies) {
                        putTextOnScreen(lco.toString());
                        if (lco.num <= 0) linies.remove(lco);
                    }
                }   
                
                if (sessio <= 0){
                    oos.writeInt(-1);
                    oos.flush();
                }
                else
                {
                    Cambrer c = Server.sessionsActives.get(sessio);
                    if (c==null){
                        oos.writeInt(-1);
                        oos.flush();
                    }
                    else
                    {
                        Connection conn = null;
                        try{
                            conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                            // Comprovem que la taula existeix i no té comanda activa
                            String query = "SELECT count(*) as taules FROM taules where comanda_activa IS NULL and taula_id="+t.taula_id;
                            Statement stmt=conn.createStatement();  
                            ResultSet rs=stmt.executeQuery(query);  
                            if (rs.next()){
                                if (rs.getInt("taules") <= 0){
                                    oos.writeInt(-1);
                                    oos.flush();
                                }
                                else
                                {
                                    // Busquem últim comanda_id
                                    query = "SELECT comanda_id FROM comandes ORDER BY comanda_id DESC LIMIT 0, 1";
                                    stmt=conn.createStatement();  
                                    rs=stmt.executeQuery(query);
                                    int newComandaId = 1;
                                    if (rs.next()){
                                        newComandaId = rs.getInt("comanda_id")+1;
                                    }
                                    
                                    // Guardem data i hora actuals
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                                    LocalDateTime now = LocalDateTime.now();
                                    
                                    String insert = "INSERT INTO comandes(`COMANDA_ID`, `DATA_COMANDA`, `TAULA`, `CAMBRER`) VALUES ("+newComandaId+", '"+dtf.format(now)+"', "+t.taula_id+", "+c.cambrer_id+")";
                                    stmt=conn.createStatement();  
                                    if(stmt.executeUpdate(insert) > 0){
                                        
                                        String update = "UPDATE taules SET `COMANDA_ACTIVA`="+newComandaId+" WHERE `TAULA_ID`="+t.taula_id;
                                        stmt=conn.createStatement();  
                                        stmt.executeUpdate(update);
                                        
                                        // Hem insertat la comanda, anem a insertar-ne les línies
                                        for (LiniaComanda lc : linies){
                                            System.out.println(lc);
                                            insert = "INSERT INTO linies_comanda(`COMANDA`, `LINIA_COM_ID`, `PLAT`, `QUANTITAT`, `ESTAT`) VALUES ("+newComandaId+", "+lc.num+", "+lc.codi_plat+", "+lc.quantitat+", '"+"EN_PREPARACIO"+"')";
                                            stmt=conn.createStatement();  
                                            stmt.executeUpdate(insert);
                                        }
                                        oos.writeInt(newComandaId);
                                        oos.flush();                            
                                    }
                                    else{
                                        oos.writeInt(-1);
                                        oos.flush();
                                    }
                                }
                            }                                                 
                        }
                        catch(Exception e){
                            oos.writeInt(-1);
                            oos.flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }

        private void buidarTaula() {
            try {
                putTextOnScreen("Llegim sessió");
    //                System.out.println("Llegim sessió");
                int sessio = ois.readInt();
                System.out.println(sessio);
                putTextOnScreen("Llegim taula");
    //                System.out.println("Llegim comanda");
                Taula t = (Taula)ois.readObject();
                System.out.println(t);
                putTextOnScreen("Hem llegit dades");

                if (sessio <= 0){
                    oos.writeInt(-1);
                    oos.flush();
                }
                else
                {
                    Cambrer c = Server.sessionsActives.get(sessio);
                    if (c==null){
                        oos.writeInt(-1);
                        oos.flush();
                    }
                    else
                    {
                        Connection conn = null;
                        conn=DriverManager.getConnection("jdbc:mysql://51.68.224.27:3306/dam2_sgomez1","dam2-sgomez1","47107354L");
                        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                        // Comprovem que la taula existeix i no té comanda activa
                        String update = "UPDATE taules SET `COMANDA_ACTIVA` = NULL WHERE TAULA_ID ="+t.taula_id;
                        Statement stmt=conn.createStatement();  

                        if (stmt.executeUpdate(update) > 0){
                            oos.writeInt(0);
                            oos.flush();
                        }
                        else{
                            oos.writeInt(-1);
                            oos.flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.s);
            hash = 53 * hash + Objects.hashCode(this.ois);
            hash = 53 * hash + Objects.hashCode(this.oos);
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
            final Client other = (Client) obj;
            if (!Objects.equals(this.s, other.s)) {
                return false;
            }
            if (!Objects.equals(this.ois, other.ois)) {
                return false;
            }
            if (!Objects.equals(this.oos, other.oos)) {
                return false;
            }
            return true;
        }
        
    }
}