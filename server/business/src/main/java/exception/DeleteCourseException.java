package exception;

/**
 * Exception thrown when trying to delete a course that has enrolled users
 */
public class DeleteCourseException extends Exception {

    public DeleteCourseException(String message) {
        super(message);
    }
}

