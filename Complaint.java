package model;

import enums.ComplaintStatus;

public class Complaint {

    private Employee author;
    private String text;
    public ComplaintStatus status;

    public Complaint(Employee author, String text) {
        this.author = author;
        this.text = text;
        this.status = ComplaintStatus.PENDING;
    }

    public boolean sign() {
        if (status == ComplaintStatus.PENDING) {
            status = ComplaintStatus.SIGNED;
            System.out.println("Complaint by " + author.getFullName() + " has been signed.");
            return true;
        }
        System.out.println("Complaint already processed. Status: " + status);
        return false;
    }

    public boolean reject() {
        if (status == ComplaintStatus.PENDING) {
            status = ComplaintStatus.REJECTED;
            System.out.println("Complaint by " + author.getFullName() + " has been rejected.");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Complaint{author='" + author.getFullName() + "', text='" + text + "', status=" + status + "}";
    }

    public Employee getAuthor()        { return author; }
    public String getText()            { return text; }
    public ComplaintStatus getStatus() { return status; }
}
