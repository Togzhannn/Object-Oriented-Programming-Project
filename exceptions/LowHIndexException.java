package exceptions;

public class LowHIndexException extends Exception {

    private final int hIndex;
    private final String supervisorName;

    public LowHIndexException(String supervisorName, int hIndex) {
        super(String.format(
            "Cannot assign '%s' as supervisor: h-index is %d (minimum required: 3).",
            supervisorName, hIndex
        ));
        this.supervisorName = supervisorName;
        this.hIndex = hIndex;
    }

    public int getHIndex(){ 
        return hIndex; 
    }
    public String getSupervisorName(){ 
        return supervisorName; 
    }
}
