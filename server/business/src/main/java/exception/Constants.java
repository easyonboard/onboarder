package exception;

/**
 * class for storing all constant messages used in exception handling
 */
public class Constants {

    public static final String PARSING_EXCEPTION = "Something when wrong when parsing request";

    /**
     * TODO:
     * constantele trebuie sa fie grupate pe cat posibil (nu vrem sa avem mesaje duplicate) in functie de Service-ul
     * in care sunt folosite si in functie de type: NOT_FOUND, DATABASE sau INVALID_DATA
     * - asa o sa fie mai usor de gasit si/sau modifcat (si e OCD-approved :D)
     */

    public static final String NOT_FOUND_EXCEPTION = " could not be found in the database";

    public static final String userNotFound(String username) {

        return "User " + username + NOT_FOUND_EXCEPTION;
    }

    public static final String userForDepartmentNotFound(String department) {

        return "Users in the " + department + " department  " + NOT_FOUND_EXCEPTION;
    }

    public static final String departmentForUserNotFound(String username) {

        return "The user " + username + " doesn't have a department assigned.";
    }

    public static final String checklistForUserNotFound(String username) {

        return "The user " + username + " doesn't have a checklist.";
    }

    public static final String checklistForUserNotSaved(String username) {

        return "The user " + username + " cannot be saved.";
    }

    /**
     * Used in EventService
     */
    // NOT_FOUND
    public static final String LOCATIONS_NOT_FOUND_EXCEPTION = "Locations could not be found in the database";
    public static final String HALLS_NOT_FOUND_EXCEPTION = "Meeting halls could not be found in the database";
    public static final String UPCOMING_EVENTS_NOT_FOUND_EXCEPTION = "There are no upcoming events";
    public static final String PAST_EVENTS_NOT_FOUND_EXCEPTION = "There are no past events";
    // DATABASE
    public static final String EVENT_SAVE_DATABASE_EXCEPTION = "The event could not be saved. Please retry later.";

    /**
     * Used in UserService
     */
    // NOT_FOUND
    public static final String USERS_NOT_FOUND_EXCEPTION = "No users in the database.";
    public static final String NEW_USERS_NOT_FOUND_EXCEPTION = "No new users in the database.";
    public static final String USERS_MATCHING_STRING_NOT_FOUND_EXCEPTION = "No users matching the entered value.";
    // DATABASE
    public static final String USER_SAVE_DATABASE_EXCEPTION = "The user could not be saved. Please retry later.";

    /**
     * constants for tutorial service exceptions
     */

    //not found
    private static final String NO_MATERIALS = " does not have materials added";
    public static final String NO_DRAFT = "No tutorials with draft status";
    public static final String TUTORIAL_NOT_FOUND = "Tutorial not found";

    //no data
    public static String noMaterials(String title) {

        return "Tutorial " + title + NO_MATERIALS;
    }

}
