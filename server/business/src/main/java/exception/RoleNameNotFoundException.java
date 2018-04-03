package exception;

public class RoleNameNotFoundException extends Exception{

    private static final String ROLE_NAME_NOT_FOUND_ERROR = "Role name not found";

    public RoleNameNotFoundException() {
        super(ROLE_NAME_NOT_FOUND_ERROR);
    }

}
