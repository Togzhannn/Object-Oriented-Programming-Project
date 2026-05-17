package model;

import java.io.Serializable;
import java.util.Objects;

public class Mark implements Serializable, Comparable<Mark> {
    private static final long serialVersionUID = 1L;

    private double att1;
    private double att2;
    private double finalExam;

    public Mark(double att1, double att2, double finalExam) {
        this.att1 = att1;
        this.att2 = att2;
        this.finalExam = finalExam;
    }

    public double getTotal() { return att1 + att2 + finalExam; }

    public boolean isPassed() { return getTotal() >= 50; }

    public String getLetterGrade() {
        double total = getTotal();
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 55) return "D";
        if (total >= 50) return "E";
        return "F";
    }

    public double getGradePoint() {
        double total = getTotal();
        if (total >= 90) return 4.0;
        if (total >= 80) return 3.0;
        if (total >= 70) return 2.0;
        if (total >= 55) return 1.0;
        if (total >= 50) return 1.0;
        return 0.0;
    }

    public double getAtt1()     { return att1; }
    public double getAtt2()     { return att2; }
    public double getFinalExam(){ return finalExam; }

    public void setAtt1(double att1)         { this.att1 = att1; }
    public void setAtt2(double att2)         { this.att2 = att2; }
    public void setFinalExam(double finalExam){ this.finalExam = finalExam; }

    @Override
    public int compareTo(Mark other) {
        return Double.compare(other.getTotal(), this.getTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark m = (Mark) o;
        return Double.compare(m.att1, att1) == 0 && Double.compare(m.att2, att2) == 0
                && Double.compare(m.finalExam, finalExam) == 0;
    }

    @Override
    public int hashCode() { return Objects.hash(att1, att2, finalExam); }

    @Override
    public String toString() {
        return String.format("ATT1: %.1f | ATT2: %.1f | Final: %.1f | Total: %.1f (%s)",
                att1, att2, finalExam, getTotal(), getLetterGrade());
    }
}
