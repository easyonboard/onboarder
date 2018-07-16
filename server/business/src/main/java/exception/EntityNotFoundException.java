package exception;

/**
 * Exception thrown when a user entity is not found in the database
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
