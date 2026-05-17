package university3.model;

import university3.enums.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fromEmployee;
    private String description;
    private RequestStatus status;
    private LocalDateTime createdAt;

    public Request(User from, String description) {
        this.fromEmployee = from.getName() + " " + from.getSurname();
        this.description = description;
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void sign()    { this.status = RequestStatus.SIGNED; }
    public void approve() { this.status = RequestStatus.APPROVED; }
    public void reject()  { this.status = RequestStatus.REJECTED; }

    public RequestStatus getStatus()  { return status; }
    public String getFromEmployee()   { return fromEmployee; }
    public String getDescription()    { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "[Request] From: " + fromEmployee + " | Status: " + status + "\n  " + description;
    }
}
