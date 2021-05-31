/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioescandalls.Model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORIES")
public class Categoria implements Serializable{
    
    @Id
    @Column(length = 7, name = "CATEGORIA_ID")
    private Long codi;
    @Basic(optional = false)
    @Column(nullable = false, length = 25, name = "NOM")
    private String nom;
    @Basic(optional = false)
    @Column(nullable = false, length = 6, name = "COLOR")
    private String colorHex;

    public Categoria() {
    }

    public long getCodi() {
        return codi;
    }

    public String getNom() {
        return nom;
    }

    public Color getColor() {        
        return Color.decode("#"+colorHex.toUpperCase());
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setColor(String colorHex) {
        this.colorHex = colorHex;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codi);
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
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.codi, other.codi)) {
            return false;
        }
        return true;
    }
    
    
}
