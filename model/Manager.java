package model;

import enums.ManagerType;
import model.teacher.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a university manager (OR, Department head, Dean, Rector).
 * Responsible for course management, student registration approval,
 * teacher assignments, and news publishing.
 *
 * <p>Implements Observer pattern as publisher — notifies subscribed employees via inbox.</p>
 *
 * @author Dev3
 * @version 1.0
 * @see ManagerType
 */
public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The type/role of this manager (OR, DEPARTMENT, DEAN, RECTOR). */
    private ManagerType managerType;

    /** Courses this manager is responsible for. */
    private List<Course> managedCourses;

    /** Complaints received from employees. */
    private List<Complaint> receivedComplaints;

    /** Published news items. */
    private List<String> news;

    /**
     * Employees subscribed to receive news notifications (Observer pattern).
     * When publishNews() is called, all observers get the news in their inbox.
     */
    private List<Employee> observers;

    /**
     * Constructs a new Manager.
     *
     * @param id          unique identifier
     * @param firstName   first name
     * @param lastName    last name
     * @param email       email for login
     * @param password    password for login
     * @param username    system username
     * @param department  department name
     * @param salary      monthly salary
     * @param managerType the role/type of this manager
     */
    public Manager(int id, String firstName, String lastName,
                   String email, String password, String username,
                   String department, double salary, ManagerType managerType) {
        super(id, firstName, lastName, email, password, username, department, salary, username);
        this.managerType = managerType;
        this.managedCourses = new ArrayList<>();
        this.receivedComplaints = new ArrayList<>();
        this.news = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // ===================== Observer Pattern =====================

    /**
     * Subscribes an employee to receive news notifications.
     *
     * @param e the employee to subscribe
     */
    public void subscribe(Employee e) {
        if (!observers.contains(e)) observers.add(e);
    }

    /**
     * Unsubscribes an employee from news notifications.
     *
     * @param e the employee to unsubscribe
     */
    public void unsubscribe(Employee e) {
        observers.remove(e);
    }

    /**
     * Publishes a news item and notifies all subscribed employees via their inbox.
     * This is the core of the Observer pattern.
     *
     * @param newsItem the news content to publish
     */
    public void publishNews(String newsItem) {
        news.add(newsItem);
        System.out.println("  [NEWS] " + newsItem);
        for (Employee e : observers) {
            e.receiveMessage(new Message(this, e, "[NEWS] " + newsItem));
        }
        System.out.println("  Notified " + observers.size() + " subscribers.");
    }

    // ===================== Course Management =====================

    /**
     * Opens a course for student registration.
     * Sets the course's registration flag to true and adds it to managed courses.
     *
     * @param course the course to open for registration
     */
    public void addCourseForRegistration(Course course) {
        course.setOpenForRegistration(true);
        if (!managedCourses.contains(course)) managedCourses.add(course);
        System.out.println("  Course opened for registration: " + course.getName());
    }

    /**
     * Approves a student's pending course registration request.
     * Moves the course from the student's pending list to their registered list.
     *
     * @param student the student requesting registration
     * @param course  the course to approve
     * @return true if approved, false if no pending request was found
     */
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

    /**
     * Assigns a teacher to a course (multiple teachers per course are allowed).
     *
     * @param course  the course to assign the teacher to
     * @param teacher the teacher to assign
     */
    public void assignTeacher(Course course, Teacher teacher) {
        course.addTeacher(teacher);
        System.out.println("  Assigned: " + teacher.getFullName() + " -> " + course.getName());
    }

    // ===================== Complaints & Requests =====================

    /**
     * Adds a complaint to this manager's received complaints list.
     *
     * @param c the complaint to add
     */
    public void addComplaint(Complaint c) {
        receivedComplaints.add(c);
    }

    /**
     * Displays all received complaints.
     */
    public void viewComplaints() {
        System.out.println("  === Complaints ===");
        if (receivedComplaints.isEmpty()) {
            System.out.println("  No complaints.");
            return;
        }
        receivedComplaints.forEach(c -> System.out.println("  " + c));
    }

    /**
     * Displays all pending requests from employees that require signing.
     */
    public void viewRequests() {
        System.out.println("  === Pending Requests ===");
        if (getRequests().isEmpty()) {
            System.out.println("  No requests.");
            return;
        }
        getRequests().forEach(r -> System.out.println("  " + r));
    }

    // ===================== Sorting / Viewing =====================

    /**
     * Displays students sorted by GPA in descending order (highest first).
     *
     * @param students list of students to sort and display
     */
    public void viewStudentsSortedByGPA(List<Student> students) {
        System.out.println("  === Students by GPA ===");
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getGPA).reversed())
                .forEach(s -> System.out.println("  "
                        + s.getFirstName() + " " + s.getLastName()
                        + " | GPA: " + String.format("%.2f", s.getGPA())));
    }

    /**
     * Displays students sorted alphabetically by last name.
     *
     * @param students list of students to sort and display
     */
    public void viewStudentsSortedByName(List<Student> students) {
        System.out.println("  === Students by Name ===");
        students.stream()
                .sorted(Comparator.comparing(Student::getLastName))
                .forEach(s -> System.out.println("  " + s));
    }

    /**
     * Displays teachers sorted alphabetically by last name.
     *
     * @param teachers list of teachers to sort and display
     */
    public void viewTeachersSortedByName(List<Teacher> teachers) {
        System.out.println("  === Teachers by Name ===");
        teachers.stream()
                .sorted(Comparator.comparing(Teacher::getLastName))
                .forEach(t -> System.out.println("  " + t));
    }

    // ===================== Getters =====================

    /** @return the manager's role type */
    public ManagerType getManagerType()            { return managerType; }

    /** @return list of courses managed by this manager */
    public List<Course> getManagedCourses()        { return managedCourses; }

    /** @return list of received complaints */
    public List<Complaint> getReceivedComplaints() { return receivedComplaints; }

    /** @return list of published news items */
    public List<String> getNews()                  { return news; }

    @Override
    public String toString() {
        return super.toString() + " | ManagerType: " + managerType;
    }
}