package exceptions;

public class NonResearcherException extends Exception {

    private final int userId;

    public NonResearcherException(int userId) {
        super("User '" + userId + "' is not a Researcher and cannot join a research project.");
        this.userId = userId;
    }
    public NonResearcherException(int userId, String message) {
        super(message);
        this.userId = userId;
    }
    public NonResearcherException(String message) {
        this.userId = 0;
    }
    public int getUserId() { 
        return userId; 
    }
}
