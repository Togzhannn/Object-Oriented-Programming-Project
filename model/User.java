package model;
import exceptions.InfoNotCorrectException;
import util.SystemLogger;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class representing a user in the university system.
 * Provides core identity fields and common operations
 * such as login and logout.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * Constructs a new User with the given credentials.
     *
     * @param id        unique numeric identifier
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param email     email address used for login
     * @param password  user's password
     */
    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Authenticates the user by comparing provided credentials
     * against stored values. Logs failed attempts via SystemLogger
     
     *
     * @param inputEmail    the email entered by the user
     * @param inputPassword the password entered by the user
     * @throws InfoNotCorrectException if email or password do not match
     */
    public void login(String inputEmail, String inputPassword) throws InfoNotCorrectException {
        if (!this.email.equals(inputEmail) || !this.password.equals(inputPassword)) {
            SystemLogger.getInstance().log("FAILED LOGIN attempt for email: " + inputEmail);
            throw new InfoNotCorrectException("Invalid email or password for: " + inputEmail);
        }
        SystemLogger.getInstance().log("LOGIN: " + getFullName() + " (id=" + id + ")");
        System.out.println("Welcome, " + getFullName() + "!");
    }

    /**
     * Logs the user out and records the event in SystemLogger.
     */
    public void logout() {
        SystemLogger.getInstance().log("LOGOUT: " + getFullName() + " (id=" + id + ")");
        System.out.println(getFullName() + " has logged out.");
    }

    /**
     * Returns the user's full name in "FirstName LastName" format.
     *
     * @return concatenated first and last name
     */
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

    /**
     * Returns the unique numeric ID of this user.
     *
     * @return user ID
     */
    public int getId() { 
        return id; 
    }

    /**
     * Sets the unique numeric ID of this user.
     *
     * @param id new ID to assign
     */
    public void setId(int id) { 
        this.id = id; 
    }

    /**
     * Returns the first name of this user.
     *
     * @return first name
     */
    public String getFirstName() { 
        return firstName; 
    }

    /**
     * Sets the first name of this user.
     *
     * @param fn new first name
     */
    public void setFirstName(String fn) { 
        this.firstName = fn; 
    }

    /**
     * Returns the last name of this user.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName; 
    }

    /**
     * Sets the last name of this user.
     *
     * @param ln new last name
     */
    public void setLastName(String ln) { 
        this.lastName = ln; 
    }

    /**
     * Returns the email address of this user.
     *
     * @return email address
     */
    public String getEmail() { 
        return email; 
    }

    /**
     * Sets the email address of this user.
     *
     * @param email new email address
     */
    public void setEmail(String email) { 
        this.email = email; 
    }

    /**
     * Returns the stored password of this user.
     *
     * @return password
     */
    public String getPassword(){ 
        return password; 
    }

    /**
     * Sets the password of this user.
     *
     * @param password new password to store
     */
    public void setPassword(String password){ 
        this.password = password; 
    }
}
