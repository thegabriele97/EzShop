package it.polito.ezshop.model;
//Simone
import java.io.Serializable;
import it.polito.ezshop.data.*;

public class User implements Serializable, it.polito.ezshop.data.User {
    
    private int id;
    private String username;
    private String password;
    private String role;

    public User(int id, String username, String password, String role) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setId(Integer id) {
        if (id <= 0) throw new IllegalArgumentException();
        this.id = id;
        DataManager.getInstance().updateUser(this);
    }

    @Override
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) throw new IllegalArgumentException();
        this.password = password;
        DataManager.getInstance().updateUser(this);
    }

    @Override
    public void setRole(String role) {
        if (!(role.equals("Administrator") || role.equals("Cashier") || role.equals("ShopManager"))) {
            throw new IllegalArgumentException();
        }

        this.role = role;
        DataManager.getInstance().updateUser(this);
    }

    @Override
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();
        this.username = username;
        DataManager.getInstance().updateUser(this);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((User)obj).id;
    }

}
