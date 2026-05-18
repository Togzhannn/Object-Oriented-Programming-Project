package model;

import enums.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Request class to track formal applications sent by users to managers.
 * It manages a simple workflow: PENDING -> SIGNED -> APPROVED, or it can be directly REJECTED.
 * It records the exact creation date and time automatically when someone makes the object.
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fromEmployee; // Full name of the person who sent the request
    private String description; // What they are asking for
    private RequestStatus status; // PENDING, SIGNED, APPROVED, or REJECTED
    private LocalDateTime createdAt; // Date and time stamp of when it was made

    /**
     * Constructor to create a new formal request.
     * Takes a User object to concatenate their full name string, 
     * sets the status to PENDING by default, and captures the current system clock time.
     */
    public Request(User from, String description) {
        this.fromEmployee = from.getFirstName() + " " + from.getLastName();
        this.description = description;
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Updates the status flag to SIGNED to show it was acknowledged by a supervisor.
     */
    public void sign() {
        this.status = RequestStatus.SIGNED;
    }

    /**
     * Updates the status flag to APPROVED to show the request was fully accepted.
     */
    public void approve() {
        this.status = RequestStatus.APPROVED;
    }

    /**
     * Updates the status flag to REJECTED to show the request was denied.
     */
    public void reject() {
        this.status = RequestStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "Request from " + fromEmployee + ", status is " + status + ", details: " + description;
    }

    public RequestStatus getStatus() { 
        return status; 
    }

    public String getFromEmployee() { 
        return fromEmployee; 
    }

    public String getDescription() { 
        return description; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
}