package model;

import enums.ManagerType;
import model.teacher.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private ManagerType managerType;
    private List<Course> managedCourses;
    private List<Complaint> receivedComplaints;
    private List<String> news;
    private List<Employee> observers;

    public Manager(int id, String firstName, String lastName,
                   String email, String password, String username,
                   String department, double salary, ManagerType managerType) {
        super(id, firstName, lastName, email, password, department, salary);
        this.managerType = managerType;
        this.managedCourses = new ArrayList<>();
        this.receivedComplaints = new ArrayList<>();
        this.news = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void subscribe(Employee e) {
        if (!observers.contains(e)) {
            observers.add(e);
        }
    }

    public void unsubscribe(Employee e) {
        observers.remove(e);
    }

    public void publishNews(String newsItem) {
        news.add(newsItem);
        System.out.println("  [NEWS] " + newsItem);
        for (Employee e : observers) {
            e.receiveMessage(new Message(this, e, "[NEWS] " + newsItem));
        }
        System.out.println("  Notified " + observers.size() + " subscribers.");
    }

    
    public void addCourseForRegistration(Course course) {
        course.setOpenForRegistration(true);
        if (!managedCourses.contains(course)) {
            managedCourses.add(course);
        }
        System.out.println("  Course opened for registration: " + course.getName());
    }
    
    public boolean approveRegistration(Student student, Course course) {
        if (!student.getPendingCourses().contains(course)) {
            System.out.println("  No pending request from "
                    + student.getFirstName() + " for " + course.getName());
            return false;
        }
        student.confirmCourseRegistration(course);
        course.addStudent(student);
        System.out.println("  Approved: " + student.getFirstName() + " -> " + course.getName());
        return true;
    }

    public void assignTeacher(Course course, Teacher teacher) {
        System.out.println("  Assigned: " + teacher.getFullName() + " -> " + course.getName());
    }

    public void addComplaint(Complaint c) {
        receivedComplaints.add(c);
    }

    public void viewComplaints() {
        System.out.println("\n  Complaints    ");
        if (receivedComplaints.isEmpty()) {
            System.out.println("  No complaints.");
            return;
        }
        receivedComplaints.forEach(c -> System.out.println("  " + c));
    }

    public void viewRequests() {
        System.out.println("\n   Pending Requests    ");
        if (getRequests().isEmpty()) {
            System.out.println("  No requests.");
            return;
        }
        getRequests().forEach(r -> System.out.println("  " + r));
    }

   
    public void viewStudentsSortedByGPA(List<Student> students) {
        System.out.println("\n   Students by GPA    ");
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getGPA).reversed())
                .forEach(s -> System.out.println("  "
                        + s.getFirstName() + " " + s.getLastName()
                        + " | GPA: " + String.format("%.2f", s.getGPA())));
    }

  
    public void viewStudentsSortedByName(List<Student> students) {
        System.out.println("\n   Students by Name     ");
        students.stream()
                .sorted(Comparator.comparing(Student::getLastName))
                .forEach(s -> System.out.println("  " + s));
    }

    public void viewTeachersSortedByName(List<Teacher> teachers) {
        System.out.println("\n   Teachers by Name    ");
        teachers.stream()
                .sorted(Comparator.comparing(Teacher::getLastName))
                .forEach(t -> System.out.println("  " + t));
    }

    public ManagerType getManagerType() { 
        return managerType; 
    }
    
    public List<Course> getManagedCourses() { 
        return managedCourses; 
    }
    
    public List<Complaint> getReceivedComplaints() { 
        return receivedComplaints; 
    }
    
    public List<String> getNews() { 
        return news; 
    }

    @Override
    public String toString() {
        return super.toString() + " | ManagerType: " + managerType;
    }
}