package it.polito.ezshop.model;
//Simone
import java.io.Serializable;
import it.polito.ezshop.data.*;

public class User implements Serializable, it.polito.ezshop.data.User {
    
    @Override
    public int hashCode() {
        // TODO: return directly the user's ID
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: return true if users'ID are equals, false otherwise
        return super.equals(obj);
    }

}
