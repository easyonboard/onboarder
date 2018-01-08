package validator;

import dto.CourseDTO;
import exception.InvalidDataException;
import org.springframework.stereotype.Service;

@Service
public class CourseValidator {

    private static final String TITLE_ERROR="Course title too short";
    private static final int MIN_NUMBER_CHARACTERS_TITLE=5;
    private static final String OVERVIEW_TOO_SHORT_ERROR="Course overview is too short";
    private static final int MIN_CHARACTERS_OVERVIEW=500;

    public void validateCourseData(CourseDTO course) throws InvalidDataException {

        validateTitle(course.getTitleCourse());
        validateDescription(course.getOverview());

    }

    private void validateDescription(String overview) throws InvalidDataException {
        if(overview.length()<MIN_CHARACTERS_OVERVIEW)
            throw new InvalidDataException(OVERVIEW_TOO_SHORT_ERROR);
    }

    private void validateTitle(String titleCourse) throws InvalidDataException {
        if(titleCourse==null || titleCourse.length()<MIN_NUMBER_CHARACTERS_TITLE)
            throw new InvalidDataException(TITLE_ERROR);
    }
}
