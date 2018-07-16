package exception;

/**
 * Exception thrown when an entry is not found in database
 */
public class FieldNotFoundException extends Exception {

    public FieldNotFoundException(String message) {

        super(message);
    }
}
