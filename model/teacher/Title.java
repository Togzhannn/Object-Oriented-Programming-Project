package model.teacher;

public enum Title {
    TUTOR("Tutor"),
    LECTOR("Lecturer"),
    SENIOR_LECTOR("Senior Lecturer"),
    PROFESSOR("Professor");

    private final String displayName;

    Title(String displayName) {
        this.displayName = displayName;
    }

    public boolean isAlwaysResearcher() {
        return this == PROFESSOR;
    }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
