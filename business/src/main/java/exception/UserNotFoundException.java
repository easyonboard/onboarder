package exception;

/**
 * Exception thrown when a user entity is not found in the database
 */
public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message) {
        super(message);
    }

}
