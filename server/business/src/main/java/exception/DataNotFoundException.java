package exception;

/**
 * Exception thrown when an entry is not found in database
 */
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message){
        super(message);
    }
}
