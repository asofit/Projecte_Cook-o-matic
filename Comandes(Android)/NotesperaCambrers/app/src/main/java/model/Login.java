package model;

import java.io.Serializable;

public class Login implements Serializable{

    public String user;
    public String password;
    public int session_id=0;
    public Cambrer cambrer;

    public Login() {
    }

    public Login(String user, String password) {
        this.user = user;
        this.password=password;
    }

    @Override
    public String toString() {
        return "Login{" + "user=" + user + ", password=" + password + ", session_id=" + session_id + ", cambrer=" + cambrer + '}';
    }
    
}
