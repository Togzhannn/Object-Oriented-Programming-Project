package model;
import enums.ComplaintStatus;
import java.io.Serializable;

/**
 * Represents a formal complaint filed by an {@link Employee} in the system.
 * A complaint starts in {@link enums.ComplaintStatus#PENDING PENDING} state
 * and can be signed or rejected exactly once.
 *
 * @see Manager#addComplaint(Complaint)
 * @see Manager#viewComplaints()
 */
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;
    private Employee author;
    private String text;

    /** Current processing status of this complaint. */
    public ComplaintStatus status;

    /**
     * Creates a new complaint with {@code PENDING} status.
     *
     * @param author the {@link Employee} who filed the complaint; must not be {@code null}
     * @param text   description of the complaint; must not be blank
     */
    public Complaint(Employee author, String text) {
        this.author = author;
        this.text = text;
        this.status = ComplaintStatus.PENDING;
    }

    /**
     * Approves (signs) the complaint if it is still {@code PENDING}.
     * Transitions status to {@link enums.ComplaintStatus#SIGNED}.
     * Does nothing and returns {@code false} if already processed.
     *
     * @return {@code true} if successfully signed; {@code false} otherwise
     */
    public boolean sign() {
        if (status == ComplaintStatus.PENDING) {
            status = ComplaintStatus.SIGNED;
            System.out.println("Complaint by " + author.getFullName() + " has been signed.");
            return true;
        }
        System.out.println("Complaint already processed. Status: " + status);
        return false;
    }

    /**
     * Rejects the complaint if it is still {@code PENDING}.
     * Transitions status to {@link enums.ComplaintStatus#REJECTED}.
     * Does nothing and returns {@code false} if already processed.
     *
     * @return {@code true} if successfully rejected; {@code false} otherwise
     */
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
        return "Complaint by " + author.getFullName() + ": " + text + " (Status: " + status + ")";
    }

    /**
     * Returns the employee who filed this complaint.
     *
     * @return complaint author
     */
    public Employee getAuthor() { 
        return author; 
    }

    /**
     * Returns the text description of this complaint.
     *
     * @return complaint text
     */
    public String getText() { 
        return text; 
    }

    /**
     * Returns the current status of this complaint.
     *
     * @return current {@link enums.ComplaintStatus}
     */
    public ComplaintStatus getStatus() { 
        return status; 
    }
}
