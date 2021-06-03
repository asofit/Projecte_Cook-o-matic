package model;

import java.io.Serializable;

public class Plat implements Serializable{

    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public int getMida_bytes_foto() {
        return mida_bytes_foto;
    }

    public void setMida_bytes_foto(int mida_bytes_foto) {
        this.mida_bytes_foto = mida_bytes_foto;
    }

    public byte[] getStream_foto() {
        return stream_foto;
    }

    public void setStream_foto(byte[] stream_foto) {
        this.stream_foto = stream_foto;
    }

    private long codi;
    
    private String nom;

    private double preu;
    private int mida_bytes_foto;
    private byte[] stream_foto;

    public Plat() {
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.codi ^ (this.codi >>> 32));
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
        final Plat other = (Plat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nom;
    }
    
    
    
}
