package model;

import enums.RequestStatus;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee author;
    private String description;
    private RequestStatus status;

    public Request(Employee author, String description) {
        this.author = author;
        this.description = description;
        this.status = RequestStatus.PENDING;
    }

    public boolean isSigned() {
        return status == RequestStatus.APPROVED;
    }

    public RequestStatus status() {
        return status;
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
        System.out.println("Request by " + author.getFullName() + " has been approved.");
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
        System.out.println("Request by " + author.getFullName() + " has been rejected.");
    }

    @Override
    public String toString() {
        return "Request{author='" + author.getFullName() + "', description='" + description + "', status=" + status + "}";
    }

    public Employee getAuthor()      { return author; }
    public String getDescription()   { return description; }
    public RequestStatus getStatus() { return status; }
}
