package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Student> students;
    private List<Course> courses;

    public Report(List<Student> students, List<Course> courses) {
        this.students = students;
        this.courses = courses;
    }

    public double getAverageGpa() {
        return students.stream()
                .mapToDouble(Student::getGPA)
                .average().orElse(0.0);
    }

    public List<Student> getTopStudents(int n) {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getGPA).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Student> getFailingStudents() {
        return students.stream()
                .filter(s -> s.getFailCount() > 0)
                .collect(Collectors.toList());
    }

    public void printReport() {
        System.out.println("    Academic Report     ");
        System.out.printf("  Total Students: %d%n", students.size());
        System.out.printf("  Average GPA: %.2f%n", getAverageGpa());
        System.out.println("  Top 3 Students: ");
        getTopStudents(3).forEach(s -> System.out.println("    " + s.getFirstName()
                + " " + s.getLastName() + " | GPA: " + String.format("%.2f", s.getGPA())));
        System.out.println("  Failing Students: " + getFailingStudents().size());
        getFailingStudents().forEach(s -> System.out.println("    " + s.getLastName()
                + " | Fails: " + s.getFailCount()));
    }
}
