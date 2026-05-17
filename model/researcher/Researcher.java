package model.researcher;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int getHIndex();

    List<ResearchProject> getResearchProjects();
    List<ResearchPaper> getResearchPapers();

    void printPapers(Comparator<ResearchPaper> comparator);
    void addPaper(ResearchPaper paper);
    void joinProject(ResearchProject project) throws exceptions.NonResearcherException;
}
