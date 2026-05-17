package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private int    id;
    private String name;       
    private int    credits;    
    private List<Lesson> lessons = new ArrayList<>();
  
    

    public Course(int id, String name, int credits) {
        this.id      = id;
        this.name    = name;
        this.credits = credits;
    }

  
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        System.out.println("Lesson added to course [" + name + "]: " + lesson.getTopic());
    }

    public void removeLesson(Lesson lesson) {
        if (lessons.remove(lesson)) {
            System.out.println("Lesson removed from course [" + name + "]: " + lesson.getTopic());
        } else {
            System.out.println("Lesson not found in course: " + name);
        }
    }

   
    @Override
    public String toString() {
        return "Course{id=" + id + ", name='" + name + "', credits=" + credits + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return id == c.id && Objects.equals(name, c.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    

    public int           getId()                    { return id; }
    public void          setId(int id)              { this.id = id; }
    public String        getName()                  { return name; }
    public void          setName(String name)       { this.name = name; }
    public int           getCredits()               { return credits; }
    public void          setCredits(int credits)    { this.credits = credits; }
    public List<Lesson>  getLessons()               { return lessons; }
}