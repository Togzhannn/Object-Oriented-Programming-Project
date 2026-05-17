package model;

import exceptions.NonResearcherException;
import model.researcher.ResearchPaper;
import model.researcher.ResearchProject;
import model.researcher.Researcher;

import java.util.*;

public class ResearcherEmployee extends Employee implements Researcher {
    private static final long serialVersionUID = 1L;

    private final String position; 
    private final List<ResearchPaper>   papers   = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public ResearcherEmployee(int id, String firstName, String lastName,
                               String email, String password,
                               String department, double salary,
                               String position) {
        super(id, firstName, lastName, email, password, department, salary);
        this.position = position;
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
    }

    @Override
    public void joinProject(ResearchProject project) throws NonResearcherException {
        project.addParticipant(this);
        if (!projects.contains(project)) projects.add(project);
        System.out.println("  " + getFullName() + " joined project: " + project.getTopic());
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
        System.out.println("  Papers by " + getFullName() + " [" + position + "]:");
        if (papers.isEmpty()) { System.out.println("  No papers published."); return; }
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }

    public String getPosition() { return position; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " | Position: " + position
            + " | Dept: " + getDepartment()
            + " | H-index: " + getHIndex();
    }
}