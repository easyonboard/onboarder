package exception.types;

/**
 * Exception thrown when a view/read query returns 0 objects
 */
public class NoDataException extends Exception {

    public NoDataException(String message) {

        super(message);
    }
}