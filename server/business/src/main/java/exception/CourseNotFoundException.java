package exception;

/**
 * Exception thrown when a course entity is not found in the database
 */
public class CourseNotFoundException extends Exception{

    public CourseNotFoundException(String message){
        super(message);
    }
}
