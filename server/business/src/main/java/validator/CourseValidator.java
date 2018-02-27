package validator;

import dto.CourseDTO;
import dto.UserDTO;
import exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Validator for {@link CourseDTO}
 */
@Service
public class CourseValidator {

    private static final String TITLE_ERROR="Course title too short";
    private static final int MIN_NUMBER_CHARACTERS_TITLE=5;
    private static final String OVERVIEW_TOO_SHORT_ERROR="Course overview is too short";
    private static final int MIN_CHARACTERS_OVERVIEW=500;
    private static final String NO_OWNER_SET_EXCEPTION="Please select course owner";
    private static final String NO_CONTACT_PERSON_SET_EXCEPTION="Please select at least one contact person";

    public void validateCourseData(CourseDTO course) throws InvalidDataException {

        validateTitle(course.getTitleCourse());
        validateDescription(course.getOverview());


    }

    public void validateContactPersons(List<Integer> contactPersons) throws InvalidDataException {
        if(contactPersons==null || contactPersons.size()==0)
            throw new InvalidDataException(NO_CONTACT_PERSON_SET_EXCEPTION);
    }

    public void validateOwners(List<Integer> owners) throws InvalidDataException {
        if(owners==null || owners.size()==0)
            throw new InvalidDataException(NO_OWNER_SET_EXCEPTION);
    }

    private void validateDescription(String overview) throws InvalidDataException {
        if(overview==null|| overview.length()<MIN_CHARACTERS_OVERVIEW)
            throw new InvalidDataException(OVERVIEW_TOO_SHORT_ERROR);
    }

    private void validateTitle(String titleCourse) throws InvalidDataException {
        if(titleCourse==null || titleCourse.length()<MIN_NUMBER_CHARACTERS_TITLE)
            throw new InvalidDataException(TITLE_ERROR);
    }
}
