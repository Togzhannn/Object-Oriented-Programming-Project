package model;

import enums.ManagerType;
import model.teacher.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a manager in the university system.
 * Extends {@link Employee} with administrative capabilities:
 * course management, student registration approval,
 * teacher assignment, complaint handling, and news broadcasting.
 *
 * <p>Implements the Observer pattern — the manager acts as publisher,
 * {@link Employee} instances act as subscribers.</p>
 *
 * @see enums.ManagerType
 */
public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private ManagerType managerType;
    private List<Course> managedCourses;
    private List<Complaint> receivedComplaints;
    private List<String> news;
    private List<Employee> observers;

    /**
     * Constructs a Manager with all required identity and role fields.
     *
     * @param id          unique numeric identifier
     * @param firstName   first name
     * @param lastName    last name
     * @param email       login email
     * @param password    login password
     * @param username    display username
     * @param department  department this manager belongs to
     * @param salary      monthly salary
     * @param managerType functional role of this manager (e.g., DEAN, DEPARTMENT_HEAD)
     */
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

    /**
     * Subscribes an {@link Employee} to receive news notifications.
     * Duplicate entries are silently ignored.
     *
     * @param e the employee to subscribe; must not be {@code null}
     */
    public void subscribe(Employee e) {
        if (!observers.contains(e)) {
            observers.add(e);
        }
    }

    /**
     * Unsubscribes an {@link Employee} from news notifications.
     * Does nothing if the employee is not subscribed.
     *
     * @param e the employee to unsubscribe
     */
    public void unsubscribe(Employee e) {
        observers.remove(e);
    }

    /**
     * Broadcasts a news item to all subscribed employees.
     * Stores the item internally, prints it to standard output,
     * and delivers a {@link Message} to every subscriber.
     *
     * @param newsItem the news text to broadcast; must not be {@code null}
     */
    public void publishNews(String newsItem) {
        news.add(newsItem);
        System.out.println("  [NEWS] " + newsItem);
        for (Employee e : observers) {
            e.receiveMessage(new Message(this, e, "[NEWS] " + newsItem));
        }
        System.out.println("  Notified " + observers.size() + " subscribers.");
    }

    /**
     * Opens a course for student registration and adds it to the managed-course list.
     * If the course is already tracked, it is not added again.
     *
     * @param course the course to open for registration; must not be {@code null}
     */
    public void addCourseForRegistration(Course course) {
        course.setOpenForRegistration(true);
        if (!managedCourses.contains(course)) {
            managedCourses.add(course);
        }
        System.out.println("  Course opened for registration: " + course.getName());
    }

    /**
     * Approves a student's pending registration request for a course.
     * The student must have an existing pending request for the given course.
     * On success, confirms enrollment on the student's side and adds
     * the student to the course roster.
     *
     * @param student the student whose request to approve; must not be {@code null}
     * @param course  the course being registered; must not be {@code null}
     * @return {@code true} if registration was approved; {@code false} if no pending request exists
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
     * Assigns a teacher to a course and prints a confirmation message.
     * Callers are responsible for updating any additional
     * teacher-course relationships outside this method.
     *
     * @param course  the course to assign the teacher to
     * @param teacher the teacher being assigned
     */
    public void assignTeacher(Course course, Teacher teacher) {
        System.out.println("  Assigned: " + teacher.getFullName() + " -> " + course.getName());
    }

    /**
     * Adds a {@link Complaint} to this manager's received-complaints list.
     *
     * @param c the complaint to add; must not be {@code null}
     */
    public void addComplaint(Complaint c) {
        receivedComplaints.add(c);
    }

    /**
     * Prints all received complaints to standard output.
     * Displays a "No complaints" message if the list is empty.
     */
    public void viewComplaints() {
        System.out.println("\n  Complaints    ");
        if (receivedComplaints.isEmpty()) {
            System.out.println("  No complaints.");
            return;
        }
        receivedComplaints.forEach(c -> System.out.println("  " + c));
    }

    /**
     * Prints all pending requests held by this manager to standard output.
     */
    public void viewRequests() {
        System.out.println("\n   Pending Requests    ");
        if (getRequests().isEmpty()) {
            System.out.println("  No requests.");
            return;
        }
        getRequests().forEach(r -> System.out.println("  " + r));
    }

    /**
     * Prints the given list of students sorted by GPA in descending order.
     *
     * @param students the list of students to display; must not be {@code null}
     */
    public void viewStudentsSortedByGPA(List<Student> students) {
        System.out.println("\n   Students by GPA    ");
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getGPA).reversed())
                .forEach(s -> System.out.println("  "
                        + s.getFirstName() + " " + s.getLastName()
                        + " | GPA: " + String.format("%.2f", s.getGPA())));
    }

    /**
     * Prints the given list of students sorted alphabetically by last name.
     *
     * @param students the list of students to display; must not be {@code null}
     */
    public void viewStudentsSortedByName(List<Student> students) {
        System.out.println("\n   Students by Name     ");
        students.stream()
                .sorted(Comparator.comparing(Student::getLastName))
                .forEach(s -> System.out.println("  " + s));
    }

    /**
     * Prints the given list of teachers sorted alphabetically by last name.
     *
     * @param teachers the list of teachers to display; must not be {@code null}
     */
    public void viewTeachersSortedByName(List<Teacher> teachers) {
        System.out.println("\n   Teachers by Name    ");
        teachers.stream()
                .sorted(Comparator.comparing(Teacher::getLastName))
                .forEach(t -> System.out.println("  " + t));
    }

    /**
     * Returns the functional role of this manager.
     *
     * @return {@link ManagerType} of this manager
     */
    public ManagerType getManagerType() { 
        return managerType; 
    }

    /**
     * Returns the list of courses managed by this manager.
     *
     * @return mutable list of managed courses
     */
    public List<Course> getManagedCourses() { 
        return managedCourses; 
    }

    /**
     * Returns the list of complaints received by this manager.
     *
     * @return mutable list of received complaints
     */
    public List<Complaint> getReceivedComplaints() { 
        return receivedComplaints; 
    }

    /**
     * Returns the list of news items published by this manager.
     *
     * @return mutable list of published news
     */
    public List<String> getNews() { 
        return news; 
    }

    @Override
    public String toString() {
        return super.toString() + " | ManagerType: " + managerType;
    }
}
