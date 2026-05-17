package exceptions;

public class NonResearcherException extends Exception {

    private final int userId;

    public NonResearcherException(String userId) {
        super("User '" + userId + "' is not a Researcher and cannot join a research project.");
        this.userId = userId != null ? Integer.parseInt(userId) : -1;
    }

    public int getUserId() { 
        return userId; 
    }
}
