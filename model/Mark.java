package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Mark class to track a student's grade for a specific course.
 * It breaks the total grade down into Attestation 1, Attestation 2, and the Final Exam.
 * Implements Comparable so we can sort marks from highest to lowest total score.
 */
public class Mark implements Serializable, Comparable<Mark> {
    private static final long serialVersionUID = 1L;

    private double att1;      // First attestation score (Midterm 1)
    private double att2;      // Second attestation score (Midterm 2)
    private double finalExam; // Final exam score

    /**
     * Simple constructor to initialize all three parts of the grade.
     */
    public Mark(double att1, double att2, double finalExam) {
        this.att1 = att1;
        this.att2 = att2;
        this.finalExam = finalExam;
    }

    /**
     * Helper method to calculate the total overall score out of 100.
     */
    public double getTotal() { 
        return att1 + att2 + finalExam; 
    }

    /**
     * Checks if the student passed. 
     * Passing mark threshold is 50 points or above.
     */
    public boolean isPassed() { 
        return getTotal() >= 50; 
    }

    /**
     * Converts the total numeric score into a standard university letter grade.
     */
    public String getLetterGrade() {
        double total = getTotal();
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 55) return "D";
        if (total >= 50) return "E";
        return "F";
    }

    /**
     * Maps the total score to numerical GPA points on a standard 4.0 scale.
     */
    public double getGradePoint() {
        double total = getTotal();
        if (total >= 90) return 4.0;
        if (total >= 80) return 3.0;
        if (total >= 70) return 2.0;
        if (total >= 55) return 1.0;
        if (total >= 50) return 1.0;
        return 0.0;
    }

    /**
     * Compares this mark object to another one. 
     * We pass 'other' first to get a descending order sort (highest totals come first).
     */
    @Override
    public int compareTo(Mark other) {
        return Double.compare(other.getTotal(), this.getTotal());
    }

    /**
     * Compares if two mark objects are identical by checking all three score parts.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark m = (Mark) o;
        return Double.compare(m.att1, att1) == 0 
            && Double.compare(m.att2, att2) == 0
            && Double.compare(m.finalExam, finalExam) == 0;
    }

    @Override
    public int hashCode() { 
        return Objects.hash(att1, att2, finalExam); 
    }

    @Override
    public String toString() {
        return String.format("1st attestation: %.1f | 2nd attestation: %.1f | final exam: %.1f | Total mark: %.1f (%s)",
                att1, att2, finalExam, getTotal(), getLetterGrade());
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public double getAtt1() { return att1; }
    public void setAtt1(double att1) { this.att1 = att1; }

    public double getAtt2() { return att2; }
    public void setAtt2(double att2) { this.att2 = att2; }

    public double getFinalExam() { return finalExam; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }
}