package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.teacher.Teacher;
/**
 Represents university course in the system.
 Course contains information about:name, credits, year of study, major,instructors and enrolled students.
 One course can have several teachers and many enrolled students.
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    //Unique course id.
    private String courseId;
    //Course name.
    private String name;
    //Number of course credits.
    private int credits;
    //Study year of the course.
    private int year;
    private String major;
    //Shows whether course is open for registration.
    private boolean openForRegistration;
    private List<Teacher> instructors;
    private List<Student> enrolledStudents;
    /**
     Creates new course object.By default course is not open for registration.
     @param courseId unique course id
     @param name course name
     @param credits number of credits
     @param year study year
     @param major course major
     */
    public Course(String courseId, String name, int credits, int year, String major) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.year = year;
        this.major = major;
        this.openForRegistration = false;
        this.instructors = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }
    /**
     Adds teacher to the course.
     One course may have more than one instructor.
     Duplicate teachers are not added.
     @param t teacher object
     @return true if teacher added successfully
     */
    public boolean addTeacher(Teacher t) {
        if (!instructors.contains(t)) { 
            instructors.add(t); 
            return true; 
        }
        return false;
    }
    /**
     Enrolls student into the course.
     Student is added only if not already enrolled.
     @param s student object
     @return true if student enrolled successfully
     */
    public boolean addStudent(Student s) {
        if (!enrolledStudents.contains(s)) { 
            enrolledStudents.add(s); 
            return true; 
        }
        return false;
    }
    /**
     Removes student from the course.
     @param s student object
     @return true if student removed
     */
    public boolean removeStudent(Student s) { 
        return enrolledStudents.remove(s); 
    }

    public String getCourseId() { 
        return courseId; 
    }

    public String getName() { 
        return name; 
    }

    public int getCredits() { 
        return credits; 
    }

    public int getYear() { 
        return year; 
    }

    public String getMajor() { 
        return major; 
    }
    /**
     Opens or closes course registration.
     @param open new registration status
     */
    public boolean isOpenForRegistration() { 
        return openForRegistration; 
    }

    public List<Teacher> getInstructors() { 
        return instructors; 
    }

    public List<Student> getEnrolledStudents() { 
        return enrolledStudents; 
    }

    public void setOpenForRegistration(boolean open) { 
        this.openForRegistration = open; 
    }
    /**
     Compares courses by course id.
     Two courses are equal if they have same id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return Objects.equals(courseId, ((Course) o).courseId);
    }
    /**
      Generates hash code for course object.
      Based on course id.
     */
    @Override
    public int hashCode() { 
        return Objects.hash(courseId); 
    }

    @Override
    public String toString() {
        return "Course: " + name + " [" + courseId + "], Credits: " + credits 
                + ", Year: " + year + ", Major: " + major 
                + ", Registration Open: " + openForRegistration 
                + ", Students Enrolled: " + enrolledStudents.size();
    }
}