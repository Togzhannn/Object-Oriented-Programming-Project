package model;

import enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private Course course;
    private LessonType type;
    private LocalDateTime dateTime;
    private String room;
    private List<Student> attendees;

    public Lesson(Course course, LessonType type, LocalDateTime dateTime, String room) {
        this.course = course;
        this.type = type;
        this.dateTime = dateTime;
        this.room = room;
        this.attendees = new ArrayList<>();
    }

    public void registerStudent(Student s) {
        if (!attendees.contains(s)) attendees.add(s);
    }

    public Course getCourse()         { return course; }
    public LessonType getType()       { return type; }
    public LocalDateTime getDateTime(){ return dateTime; }
    public String getRoom()           { return room; }
    public List<Student> getAttendees(){ return attendees; }

    @Override
    public String toString() {
        return "[Lesson] " + type + " | Course: " + course.getName()
                + " | " + dateTime + " | Room: " + room;
    }
}
