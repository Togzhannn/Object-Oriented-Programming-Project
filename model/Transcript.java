package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentName;
    private Map<Course, Mark> records;

    public Transcript(String studentName) {
        this.studentName = studentName;
        this.records = new HashMap<>();
    }

    public void addRecord(Course c, Mark m) {
        records.put(c, m);
    }

    public double getGPA() {
        if (records.isEmpty()) return 0.0;
        double totalPoints = 0;
        int totalCredits = 0;
        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            int credits = entry.getKey().getCredits();
            double grade = entry.getValue().getGradePoint();
            totalPoints += grade * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public void print() {
        System.out.println("  === Transcript: " + studentName + " ===");
        if (records.isEmpty()) { System.out.println("  No records."); return; }
        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            System.out.println("  " + entry.getKey().getName()
                    + " (" + entry.getKey().getCredits() + " cr): " + entry.getValue());
        }
        System.out.printf("  GPA: %.2f%n", getGPA());
    }

    public Map<Course, Mark> getRecords() { 
    	return records; 
    }
    public String getStudentName(){ 
    	return studentName; 
    }
}
