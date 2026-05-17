package model;

import enums.StudentStatus;
import exceptions.CreditLimitExceededException;
import exceptions.LowHIndexException;
import exceptions.NonResearcherException;
import exceptions.MaxFailReachedException;
import model.researcher.ResearchPaper;
import model.researcher.ResearchProject;
import model.researcher.Researcher;
import model.teacher.*;

import java.io.Serializable;
import java.util.*;

public class Student extends User implements Serializable, Researcher {
    private static final long serialVersionUID = 1L;

    private int year;
    private String major;
    private int currentCredits;
    private int failCount;
    private StudentStatus status;
    private Transcript transcript;
    private List<Course> registeredCourses;
    private List<Course> pendingCourses;
    private Researcher supervisor;

    private final List<ResearchPaper>   papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public Student(int id, String firstName, String lastName,
                   String email, String password, int year, String major) {
        super(id, firstName, lastName, email, password);
        this.year = year;
        this.major = major;
        this.currentCredits = 0;
        this.failCount = 0;
        this.status = StudentStatus.ACTIVE;
        this.transcript = new Transcript(getFirstName() + " " + getLastName());
        this.registeredCourses = new ArrayList<>();
        this.pendingCourses = new ArrayList<>();
    }

   
    @Override
    public void joinProject(ResearchProject project) throws NonResearcherException {
        if (papers.isEmpty()) {
            throw new NonResearcherException(
                getFullName() + " cannot join a project: no research papers published.");
        }
        project.addParticipant(this);
        if (!projects.contains(project)) projects.add(project);
        System.out.println("  " + getFullName() + " joined project: " + project.getTopic());
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
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
    public List<ResearchPaper> getResearchPapers() {
        return Collections.unmodifiableList(papers);
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableList(projects);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("  Papers by " + getFullName() + ":");
        if (papers.isEmpty()) { System.out.println("  No papers."); return; }
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }

    public boolean requestCourseRegistration(Course c) throws CreditLimitExceededException {
        if (!c.isOpenForRegistration()) {
            System.out.println("  Course " + c.getName() + " is not open for registration.");
            return false;
        }
        if (currentCredits + c.getCredits() > 21) {
            throw new CreditLimitExceededException("Cannot register for " + c.getName()
                    + ": credit limit (21) would be exceeded. Current: " + currentCredits);
        }
        if (pendingCourses.contains(c) || registeredCourses.contains(c)) {
            System.out.println("  Already registered or pending for " + c.getName());
            return false;
        }
        pendingCourses.add(c);
        System.out.println("  Registration request sent for: " + c.getName());
        return true;
    }

    public void confirmCourseRegistration(Course c) {
        if (pendingCourses.remove(c)) {
            registeredCourses.add(c);
            currentCredits += c.getCredits();
        }
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("  Rating must be between 1 and 5.");
            return;
        }
        teacher.receiveRating(rating);
        System.out.println("  You rated " + teacher.getFirstName() + ": " + rating + "/5");
    }

    public void viewMarks() {
        System.out.println(" Marks for " + getFirstName());
        transcript.getRecords().forEach((course, mark) ->
                System.out.println("  " + course.getName() + ": " + mark));
    }

    public void viewTranscript() { transcript.print(); }

    public Transcript getTranscript() { return transcript; }

    public void incrementFailCount() throws MaxFailReachedException {
        failCount++;
        if (failCount >= 3) {
            status = StudentStatus.EXPELLED;
            throw new MaxFailReachedException(
                getFullName() + " has failed 3 times and is expelled.");
        }
    }

    public void assignSupervisor(Researcher supervisor) throws LowHIndexException {
        if (year != 4) {
            System.out.println("  Only 4th year students can have a supervisor.");
            return;
        }
        if (supervisor.getHIndex() < 3) {
            throw new LowHIndexException(supervisor.toString(), supervisor.getHIndex());
        }
        this.supervisor = supervisor;
        System.out.println("  Supervisor assigned for " + getFirstName() + ".");
    }

    public int getYear(){ 
        return year; 
    }
    public String getMajor(){ 
        return major; 
    }
    public int getCurrentCredits(){ 
        return currentCredits; 
    }
    public int getFailCount(){ 
        return failCount; 
    }
    public StudentStatus getStatus(){ 
        return status; 
    }
    public List<Course> getRegisteredCourses(){ 
        return registeredCourses; 
    }
    public List<Course> getPendingCourses(){ 
        return pendingCourses; 
    }
    public Researcher getSupervisor(){ 
        return supervisor; 
    }
    public double getGPA(){ 
        return transcript.getGPA(); 
    }

    @Override
    public String toString() {
        return super.toString() + " | Year: " + year + " | Major: " + major
                + " | Credits: " + currentCredits + " | GPA: " + String.format("%.2f", getGPA())
                + " | Status: " + status;
    }
    public boolean isPassed() {
        throw new UnsupportedOperationException("Unimplemented method 'isPassed'");
    }
}
