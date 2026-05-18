package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents academic transcript of student.
 *
 * Transcript stores:
 * courses,
 * marks
 * and GPA information.
 *
 * Used for tracking student academic performance.
 */
public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentName; //full name of transcript owner.
    private Map<Course, Mark> records;
    /**
     * Creates new transcript object.
     * Initially transcript contains no records.
     * @param studentName student full name
     */
    public Transcript(String studentName) {
        this.studentName = studentName;
        this.records = new HashMap<>();
    }

    public void addRecord(Course c, Mark m) {
        records.put(c, m);
    }
    /**
     * calculates GPA using all transcript records.
     * GPA is calculated based on:
     * grade points and course credits.
     *
     * @return calculated GPA
     */
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
        if (totalCredits == 0){
            return 0.0;
        }
        else{
            return totalPoints / totalCredits;
        }
    }

    public void print() { // displays transcript information
        System.out.println("\n    Transcript: " + studentName + "     ");
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
    /**
     * @return transcript owner name
     */
    public String getStudentName(){ 
    	return studentName; 
    }
}
