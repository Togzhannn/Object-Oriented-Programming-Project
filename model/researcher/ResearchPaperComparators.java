package model.researcher;

import java.util.Comparator;

public final class ResearchPaperComparators {

    private ResearchPaperComparators() {}

    public static final Comparator<ResearchPaper> BY_CITATIONS =
        Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_DATE =
        Comparator.comparing(ResearchPaper::getDate).reversed();

    public static final Comparator<ResearchPaper> BY_PAGES =
        Comparator.comparingInt(ResearchPaper::getPages).reversed();

    public static final Comparator<ResearchPaper> BY_TITLE =
        Comparator.comparing(ResearchPaper::getTitle);
}
