package exceptions;

public class NonResearcherException extends Exception {

    private final int userId;

    public NonResearcherException(int userId) {
        super("User '" + userId + "' is not a Researcher and cannot join a research project.");
        this.userId = userId;
    }

    public int getUserId() { 
        return userId; 
    }
}
