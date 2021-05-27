package start;

import com.sun.prism.j2d.J2DPipeline;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Start {
    
    private static ServerSocket server = null;
    //socket server port on which it will listen
    private static int port = 9876;
    private static boolean on = true;

    private static JTextArea t;
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        JScrollPane s = new JScrollPane();
        JPanel p = new JPanel();
        
        t = new JTextArea();
        t.setEditable(false);
        t.setSize(4000,4000);
        t.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        
        s.add(t);
        s.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        
        JButton b = new JButton();
        b.setText("Iniciar");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (server == null){
                    startServer();
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
                    System.exit(0);
                }
            }
        });
        
        p.add(b,BorderLayout.WEST);
        p.add(bu,BorderLayout.WEST);
        
        f.add(s, BorderLayout.PAGE_START);
        f.add(p,BorderLayout.SOUTH);
        
        f.pack();
        f.setVisible(true);
        f.setResizable(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    private static void startServer() {
        try {
            //create the socket server object
            server = new ServerSocket(port);
            //keep listens indefinitely until receives 'exit' call or program terminates
            while(on){
                putTextOnScreen("Waiting for the client request");
                //creating socket and waiting for client connection
                Socket socket = server.accept();
                //read from socket to ObjectInputStream object
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //convert ObjectInputStream object to String
                String message = (String) ois.readObject();
                putTextOnScreen("Message Received: " + message);
                //create ObjectOutputStream object
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                //terminate the server if client sends exit request
                if(message.equalsIgnoreCase("exit")) break;
            }
            putTextOnScreen("Shutting down Socket server!!");
            //close the ServerSocket object
            server.close();
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    private static void putTextOnScreen(String text) {
        t.setText(t.getText()+"\n"+text);
    }
    private class client extends Thread{

        private Socket s; 
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        
        public client(Socket _s, ObjectInputStream _ois, ObjectOutputStream _oos) {
            s=_s;
            ois=_ois;
            oos=_oos;
        }
        
        public void run() {
            
        }
    }
}
