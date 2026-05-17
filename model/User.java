package model;

import exceptions.InfoNotCorrectException;
import util.SystemLogger;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public void login(String inputEmail, String inputPassword) throws InfoNotCorrectException {
        if (!this.email.equals(inputEmail) || !this.password.equals(inputPassword)) {
            SystemLogger.getInstance().log("FAILED LOGIN attempt for email: " + inputEmail);
            throw new InfoNotCorrectException("Invalid email or password for: " + inputEmail);
        }
        SystemLogger.getInstance().log("LOGIN: " + getFullName() + " (id=" + id + ")");
        System.out.println("Welcome, " + getFullName() + "!");
    }

    public void logout() {
        SystemLogger.getInstance().log("LOGOUT: " + getFullName() + " (id=" + id + ")");
        System.out.println(getFullName() + " has logged out.");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User: " + getFullName() + " (ID: " + id + ", Email: " + email + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public void setFirstName(String fn) { 
        this.firstName = fn; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String ln) { 
        this.lastName = ln; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
}
