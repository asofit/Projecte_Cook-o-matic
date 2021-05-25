/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioescandalls.Model;

/**
 *
 * @author ADMIN
 */
public class Ingredient {
    private long id;
    private String nom;

    public Ingredient() {
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
