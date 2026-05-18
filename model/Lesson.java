package model;

import enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lesson class representing a single scheduled class session for a course.
 * It tracks basic info like when and where the class happens, if it's a 
 * lecture, practice, or lab, and maintains a list of students who attended.
 */
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private Course course; // The course module this lesson is linked to
    private LessonType type; // LECTURE, PRACTICE, or LAB
    private LocalDateTime dateTime; // Day and time of the schedule
    private String room; // Room number or auditorium string
    private List<Student> attendees; // Array list to track attendance roll call

    /**
     * Constructor to schedule a new lesson session.
     * Automatically sets up an empty ArrayList to start collecting student attendance.
     */
    public Lesson(Course course, LessonType type, LocalDateTime dateTime, String room) {
        this.course = course;
        this.type = type;
        this.dateTime = dateTime;
        this.room = room;
        this.attendees = new ArrayList<>();
    }

    /**
     * Adds a student to the attendance list for this lesson.
     * Checks if they are already on the list so we don't count them twice.
     */
    public void registerStudent(Student s) {
        if (!attendees.contains(s)) {
            attendees.add(s);
        }
    }

    @Override
    public String toString() {
        return "Lesson type: " + type + " for course " + course.getName()
                + " on " + dateTime + " in room " + room;
    }

    public Course getCourse() { 
        return course; 
    }

    public LessonType getType() { 
        return type; 
    }

    public LocalDateTime getDateTime() { 
        return dateTime; 
    }

    public String getRoom() { 
        return room; 
    }

    public List<Student> getAttendees() { 
        return attendees; 
    }
}