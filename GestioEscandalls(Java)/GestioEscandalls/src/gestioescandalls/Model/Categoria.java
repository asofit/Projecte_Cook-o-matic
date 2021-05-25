/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioescandalls.Model;

import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class Categoria {
    private long codi;
    private String nom;
    private Color color;

    public Categoria() {
    }

    public long getCodi() {
        return codi;
    }

    public String getNom() {
        return nom;
    }

    public Color getColor() {
        return color;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
