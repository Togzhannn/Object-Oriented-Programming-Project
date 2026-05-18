package model;

import exceptions.InfoNotCorrectException;
import exceptions.UserNotFoundException; 
import util.Command;
import util.SystemLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents admin of our university system.
 * Admin can manage users like adding, removing, updating and search them.
 * Also admin can: view system logs, block users and reset passwords.
 */

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * List of users managed by admin.
     */

    private List<User> users;

    public Admin(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password);
        this.users = new ArrayList<>();
    }
    /**
     * Adds new user into the system. Action is also saved into logs.
     */

    public boolean addUser(User user) {
        Command addCommand = () ->
            SystemLogger.getInstance().log("Admin " + getFullName() + " created a new user: " + user.getFullName());
        if (users.contains(user)) {
            System.out.println("User " + user.getFullName() + " already exists.");
            return false;
        }
        users.add(user);
        addCommand.execute();
        System.out.println("User " + user.getFullName() + " added successfully.");
        return true;
    }

    /** @throws UserNotFoundException if user not found
    */
    public boolean removeUser(String userId) throws UserNotFoundException {
        int id = parseId(userId);
        User toRemove = findById(id);
        Command removeCommand = () ->
            SystemLogger.getInstance().log("ADMIN [" + getFullName() + "] removed user id=" + userId);
        users.remove(toRemove);
        removeCommand.execute();
        System.out.println("User id=" + userId + " removed.");
        return true;
    }
    /**
     * Updates information of existing user.
     * @param userId target user id

    public void updateUser(String userId, User updatedUser) throws UserNotFoundException {
        int id = parseId(userId);
        User existing = findById(id);
        int index = users.indexOf(existing);
        users.set(index, updatedUser);
        SystemLogger.getInstance().log("ADMIN [" + getFullName() + "] updated user id=" + userId);
        System.out.println("User id=" + userId + " updated.");
    }
    /**
     * Searches user by first name, last name or id.
     * @param keyword search keyword
     * @return found user
     * @throws UserNotFoundException if user does not exist
     */

    public User searchUser(String keyword) throws UserNotFoundException {
        return users.stream()
                .filter(u -> u.getFirstName().equalsIgnoreCase(keyword)
                          || u.getLastName().equalsIgnoreCase(keyword)
                          || String.valueOf(u.getId()).equals(keyword))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found for keyword: " + keyword));
    }

    public List<String> viewLogs() {
        List<String> logs = SystemLogger.getInstance().getLogs();
        System.out.println("System logs (" + logs.size() + " entries):");
        logs.forEach(System.out::println);
        return logs;
    }

    public boolean resetPassword(String userId, String newPassword)
            throws UserNotFoundException, InfoNotCorrectException {
        if (newPassword == null || newPassword.length() < 6) {
            throw new InfoNotCorrectException("Password must be at least 6 characters.");
        }
        int id = parseId(userId);
        User user = findById(id);
        user.setPassword(newPassword);
        SystemLogger.getInstance().log("Admin " + getFullName() + " reset password for user ID " + userId);
        System.out.println("Password reset successfully for id=" + userId);
        return true;
    }

    public boolean blockUser(String userId) throws UserNotFoundException {
        int id = parseId(userId);
        User user = findById(id);
        SystemLogger.getInstance().log("ADMIN [" + getFullName() + "] BLOCKED user: " + user.getFullName() + " (id=" + userId + ")");
        System.out.println("User " + user.getFullName() + " has been blocked.");
        return true;
    }

    public boolean unblockUser(String userId) throws UserNotFoundException {
        int id = parseId(userId);
        User user = findById(id);
        SystemLogger.getInstance().log("Admin " + getFullName() + " unblocked user " + user.getFullName() + " with ID " + userId);
        System.out.println("User " + user.getFullName() + " has been unblocked.");
        return true;
    }

    private User findById(int id) throws UserNotFoundException {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with id=" + id + " not found."));
    }

    private int parseId(String userId) throws UserNotFoundException {
        try {
            return Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new UserNotFoundException("Invalid ID format: " + userId);
        }
    }

    @Override
    public String toString() {
        return "Admin: " + getFullName() + " (ID: " + getId() + ", managing " + users.size() + " users)";
    }
    /**
     * Returns list of all users.
     * @return users list
     */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
