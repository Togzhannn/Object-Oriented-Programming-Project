package model.teacher;

import exceptions.NonResearcherException;
import model.researcher.*;
import model.Student;
import model.User;

import java.util.*;

public class Teacher extends User implements Researcher {
	//Academic degree of the teacher
    private Title degree;
    private final List<ResearchPaper>  papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();
    private double averageRating = 0.0;
    private int    ratingCount   = 0;
    /**
     Creates a new teacher object.
	 @param id unique user id
     @param firstName teacher first name
     @param lastName teacher last name
     @param degree academic degree
     @param email email address
     @param password account password
     */
    public Teacher(int id, String firstName, String lastName,
                   Title degree, String email, String password) {
        super(id, firstName, lastName, email, password);
        this.degree = degree;
    }
    /**
     Puts a mark for a student in a course.
     Mark consists of:
     attendance 1, attendance 2 and final exam score.
      @param student student who receives the mark
      @param courseName name of the course
      @param att1 first attendance score
      @param att2 second attendance score
      @param finalExam final exam score
     */
    public void putMark(Student student, String courseName, int att1, int att2, int finalExam) {
        int total = att1 + att2 + finalExam;
        System.out.printf("  Mark recorded for %s in %s: %d+%d+%d = %d%n",
            student.getFullName(), courseName, att1, att2, finalExam, total);
    }
    //Shows teacher courses info.
    public void viewCourses() {
        System.out.println("  " + getFirstName() + "'s courses: see enrolled courses via University.");
    }
    /**
     Calculates H-index based on research papers citations.
     H-index is the maximum value h such that the teacher
     has at least h papers with h or more citations.
     @return H-index value
     */
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
    //Returns list of research projects.
    @Override
    public List<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableList(projects);
    }
    //Returns list of research papers.
    @Override
    public List<ResearchPaper> getResearchPapers() {
        return Collections.unmodifiableList(papers);
    }
    /**
     Prints all research papers using given sorting rule.
     @param comparator rule for sorting papers
     */
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("  Papers by " + getFullName() + ":");
        if (papers.isEmpty()) { System.out.println("  No papers published."); return; }
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }
    /**
     Adds a research paper to teacher profile.
     Duplicate papers are not added.
      @param paper research paper
     */
    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
    }
    /**
      Adds teacher to a research project.
      Teacher must be a researcher to join.
      @param project research project
      @throws NonResearcherException if teacher is not allowed to join
     */
    @Override
    public void joinProject(ResearchProject project) throws NonResearcherException {
        if (!isResearcher()) {
            throw new NonResearcherException(
                getId(), "Teacher " + getFullName() + " is not a researcher and cannot join projects."
            );
        }
        if (!projects.contains(project)) {
            projects.add(project);
        }
        project.addParticipant(this); 
        
        System.out.println("  " + getFullName() + " joined project: " + project.getTopic());
    }
    /**
     Checks if teacher is considered a researcher.
     Teacher is a researcher if:
     degree allows research OR
     teacher has published at least one paper
     
      @return true if researcher
     */
    public boolean isResearcher() {
        return degree.isAlwaysResearcher() || !papers.isEmpty();
    }
    /**
     Receives rating from students and updates average rating.
     Rating affects teacher reputation score.
     @param rating rating value (1-5)
     */
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