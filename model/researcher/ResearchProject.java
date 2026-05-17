package model.researcher;

import exceptions.NonResearcherException;
import model.teacher.Teacher;
import model .User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResearchProject {

    private final String topic;
    private final List<ResearchPaper> papers;
    private final List<Researcher> participants;

    public ResearchProject(String topic) {
        if (topic == null || topic.isBlank()) throw new IllegalArgumentException("Topic cannot be empty");
        this.topic = topic;
        this.papers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public void addParticipant(User user) throws NonResearcherException {
        if (!(user instanceof Researcher)) {
            throw new NonResearcherException(user.getId());
        }
        if (user instanceof Teacher teacher && !teacher.isResearcher()) {
            throw new NonResearcherException(user.getId());
        }
        Researcher r = (Researcher) user;
        if (!participants.contains(r)) {
            participants.add(r);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("\n   Papers in project: " + topic + "    ");
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }

    public String getTopic(){ 
        return topic; 
    }
    public List<ResearchPaper> getPapers(){ 
        return Collections.unmodifiableList(papers); 
    }
    public List<Researcher> getParticipants(){ 
        return Collections.unmodifiableList(participants); 
    }

    @Override
    public String toString() {
        return "Project: " + topic + "Papers: "+ papers.size() + "Participants: "+ participants.size();
    }
}
