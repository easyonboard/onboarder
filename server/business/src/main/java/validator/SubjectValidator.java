package validator;

import dto.SubjectDTO;
import exception.InvalidDataException;
import org.springframework.stereotype.Service;

/**
 * Validator for {@link SubjectDTO}
 */
@Service
public class SubjectValidator {
    private static final String SUBJECT_TITLE_TOO_SHORT="Subject name is too short";
    private static final String SUBJECT_DESCRIPTION_TOO_SHORT="Subject description is too short";
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION=20;
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_TITLE=5;
    private static final String NUMBER_OF_DAYS_EXCEPTION="Select number of days to complete subject";


    public void validateSubject(SubjectDTO subjectDTO) throws InvalidDataException {

        validateSubjectName(subjectDTO.getName());
        validateSubjectDescription(subjectDTO.getDescription());
        validateNumberOFDays(subjectDTO.getNumberOfDays());
    }

    private void validateNumberOFDays(int numberOfDays) throws InvalidDataException {
        if(numberOfDays==0)
            throw new InvalidDataException(NUMBER_OF_DAYS_EXCEPTION);
    }

    private void validateSubjectDescription(String description) throws InvalidDataException {
        if(description==null || description.length()<MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION)
            throw new InvalidDataException(SUBJECT_DESCRIPTION_TOO_SHORT);
    }

    private void validateSubjectName(String name) throws InvalidDataException {
        if(name==null || name.length()<MIN_NUMBER_OF_CHARACTERS_FOR_TITLE)
            throw new InvalidDataException(SUBJECT_TITLE_TOO_SHORT);
    }
}
