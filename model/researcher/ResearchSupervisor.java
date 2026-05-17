package model.researcher;

import exceptions.LowHIndexException;
import model.Student;

import java.time.LocalDate;

public class ResearchSupervisor {

    private Researcher supervisor;
    private final Student student;
    private final LocalDate startDate;

    private static final int MIN_H_INDEX = 3;

    public ResearchSupervisor(Student student, Researcher supervisor) throws LowHIndexException {
        if (student.getYear() != 4) {
            throw new IllegalArgumentException("Only 4th year students can have a research supervisor.");
        }
        this.student = student;
        this.startDate = LocalDate.now();
        setSupervisor(supervisor);
    }

    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        int h = supervisor.getHIndex();
        if (h < MIN_H_INDEX) {
            String name = (supervisor instanceof model.User) ? supervisor.toString() : "Unknown";
            throw new LowHIndexException(name, h);
        }
        this.supervisor = supervisor;
    }

    public Researcher getSupervisor(){ 
        return supervisor; 
    }
    public Student getStudent(){ 
        return student; 
    }
    public LocalDate getStartDate(){ 
        return startDate; 
    }

    @Override
    public String toString() {
        return "Supervisor assignment: " + supervisor + "Student:" + student + "Start date: " + startDate;
    }
}
