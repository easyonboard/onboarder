package exception.types;

/**
 * Exception thrown when the data to be inserted in the database does not respect the required format
 */
public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

}
