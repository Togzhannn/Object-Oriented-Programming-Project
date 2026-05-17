package exceptions;

import java.io.Serializable;

public class CreditLimitExceededException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    public CreditLimitExceededException(String message) { super(message); }
}
