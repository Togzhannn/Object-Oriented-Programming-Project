package model.teacher;

import exceptions.NonResearcherException;
import model.researcher.*;
import model.Student;
import model.User;

import java.util.*;

public class Teacher extends User implements Researcher {

    private Title degree;
    private final List<ResearchPaper>  papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();
    private double averageRating = 0.0;
    private int    ratingCount   = 0;

    public Teacher(int id, String firstName, String lastName,
                   Title degree, String email, String password) {
        super(id, firstName, lastName, email, password);
        this.degree = degree;
    }

    public void putMark(Student student, String courseName, int att1, int att2, int finalExam) {
        int total = att1 + att2 + finalExam;
        System.out.printf("  Mark recorded for %s in %s: %d+%d+%d = %d%n",
            student.getFullName(), courseName, att1, att2, finalExam, total);
    }

    public void viewCourses() {
        System.out.println("  " + getFirstName() + "'s courses: see enrolled courses via University.");
    }

    @Override
    public int getHIndex() {
        int[] sorted = papers.stream()
            .mapToInt(ResearchPaper::getCitations)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .mapToInt(Integer::intValue)
            .toArray();
        int h = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableList(projects);
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return Collections.unmodifiableList(papers);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("  Papers by " + getFullName() + ":");
        if (papers.isEmpty()) { System.out.println("  No papers published."); return; }
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
    }

    @Override
    public void joinProject(ResearchProject project) throws NonResearcherException {
        if (!isResearcher()) {
            throw new NonResearcherException(
                getId() + " is not a researcher (needs PROFESSOR title or at least one paper).");
        }
        project.addParticipant(this);
        if (!projects.contains(project)) projects.add(project);
        System.out.println("  " + getFullName() + " joined project: " + project.getTopic());
    }

    public boolean isResearcher() {
        return degree.isAlwaysResearcher() || !papers.isEmpty();
    }

    public void receiveRating(int rating) {
        ratingCount++;
        averageRating = ((averageRating * (ratingCount - 1)) + rating) / ratingCount;
        System.out.println("  Rating received. New average: "
            + String.format("%.1f", averageRating) + "/5");
    }

    public Title  getDegree(){
        return degree; 
    }
    public void   setDegree(Title d){
        this.degree = d; 
    }
    public double getAverageRating(){ 
        return averageRating; }
    public int    getRatingCount(){ 
        return ratingCount; 
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " | Degree: " + degree + " | H-index: " + getHIndex();
    }
}