package model.researcher;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import enums .ResearchTopic;

public class ResearchPaper implements Comparable<ResearchPaper> {

    private final String title;
    private final List<String> authors;
    private final String journal;
    private final String doi;
    private final int pages;
    private final LocalDate date;
    private int citations;
    private final ResearchTopic researchTopic;

    public ResearchPaper(String title, List<String> authors, String journal, String doi, int pages, LocalDate date, int citations, ResearchTopic researchTopic) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        if (doi == null || doi.isBlank()) throw new IllegalArgumentException("DOI cannot be empty");
        if (pages <= 0) throw new IllegalArgumentException("Pages must be positive");

        this.title = title;
        this.authors = List.copyOf(authors);
        this.journal = journal;
        this.doi = doi;
        this.pages = pages;
        this.date = date;
        this.citations = citations;
        this.researchTopic = researchTopic;
    }

    public String getTitle(){ 
        return title;
    }
    public List<String> getAuthors(){ 
        return authors;
    }
    public String getJournal(){
        return journal; 
    }
    public String getDoi(){ 
        return doi; 
    }
    public int getPages(){ 
        return pages; 
    }
    public LocalDate getDate(){ 
        return date; 
    }
    public int getCitations(){ 
        return citations; 
    }
    public ResearchTopic getResearchTopic(){ 
        return researchTopic; 
    }

    public void addCitation(){ 
        this.citations++; 
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    @Override
    public String toString() {
        return "Date: " + date.getYear() + "Title: " + title + "Journal: " + journal + "Cituations: " + citations + "Pages: " + pages + "Topic: " + researchTopic;
        
    }
}
