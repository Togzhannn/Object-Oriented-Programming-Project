package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.teacher.Teacher;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseId;
    private String name;
    private int credits;
    private int year;
    private String major;
    private boolean openForRegistration;
    private List<Teacher> instructors;
    private List<Student> enrolledStudents;

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

    public boolean addTeacher(Teacher t) {
        if (!instructors.contains(t)) { 
            instructors.add(t); 
            return true; 
        }
        return false;
    }

    public boolean addStudent(Student s) {
        if (!enrolledStudents.contains(s)) { 
            enrolledStudents.add(s); 
            return true; 
        }
        return false;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return Objects.equals(courseId, ((Course) o).courseId);
    }

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