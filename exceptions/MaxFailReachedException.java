package exceptions;

import java.io.Serializable;

public class MaxFailReachedException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    public MaxFailReachedException(String message) { 
    		super(message);
    }
}
