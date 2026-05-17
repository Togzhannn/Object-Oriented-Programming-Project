package model;

import util.SystemLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String department;
    private double salary;
    private List<Message> inbox = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

    public Employee(int id, String firstName, String lastName,
                    String email, String password,
                    String department, double salary) {
        super(id, firstName, lastName, email, password);
        this.department = department;
        this.salary = salary;
    }


    public void sendMessage(Employee to, String text) {
        Message message = new Message(this, to, text);
        to.receiveMessage(message);
        SystemLogger.getInstance().log("MESSAGE: " + getFullName() + " -> " + to.getFullName() + " | " + text);
        System.out.println("Message sent from " + getFullName() + " to " + to.getFullName());
    }

    public void sendComplaint(Manager to, String text) {
        Complaint complaint = new Complaint(this, text);
        SystemLogger.getInstance().log("COMPLAINT: " + getFullName() + " -> Manager " + to.getFullName() + " | " + text);
        System.out.println("Complaint sent from " + getFullName() + " to manager " + to.getFullName());
    }

    public void sendRequest(String description) {
        Request request = new Request(this, description);
        requests.add(request);
        SystemLogger.getInstance().log("REQUEST: " + getFullName() + " submitted a request: " + description);
        System.out.println("Request submitted by " + getFullName());
    }

    public void receiveMessage(Message message) {
        inbox.add(message);
    }

    public String receiveMessage() {
        for (Message msg : inbox) {
            if (!msg.isRead()) {
                msg.mark();
                return "New message from " + msg.getSender().getFullName() + ": " + msg.getContent();
            }
        }
        return "No new messages.";
    }

    public String getDepartment()         { return department; }
    public void setDepartment(String dep) { this.department = dep; }
    public double getSalary()             { return salary; }
    public void setSalary(double salary)  { this.salary = salary; }
    public List<Message> getInbox()       { return inbox; }
    public List<Request> getRequests()    { return requests; }
}
