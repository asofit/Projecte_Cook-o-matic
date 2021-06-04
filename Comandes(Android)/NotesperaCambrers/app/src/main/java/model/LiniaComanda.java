package model;

import java.io.Serializable;
import java.util.Objects;

public class LiniaComanda implements Serializable{
    
    public int num;
    public int quantitat;
    public int codi_plat;

    public LiniaComanda() {
    }

    @Override
    public String toString() {
        return "LiniaComanda{" + "num=" + num + ", quantitat=" + quantitat + ", codi_plat=" + codi_plat + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.num;
        hash = 67 * hash + this.codi_plat;
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
        final LiniaComanda other = (LiniaComanda) obj;
        if (this.num != other.num) {
            return false;
        }
        if (this.codi_plat != other.codi_plat) {
            return false;
        }
        return true;
    }
}
