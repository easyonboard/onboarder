package validator;

import dto.MaterialDTO;
import exception.InvalidDataException;
import org.springframework.stereotype.Service;

/**
 * Validator for {@link MaterialDTO}
 */
@Service
public class MaterialValidator {

    private static final String MATERIAL_TITLE_TOO_SHORT="Subject name is too short";
    private static final String MATERIAL_DESCRIPTION_TOO_SHORT="Subject description is too short";
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION=20;
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_TITLE=5;

    public void validateMaterial(MaterialDTO material) throws InvalidDataException {
        validateTitle(material.getTitle());
        validateDescription(material.getDescription());
    }

    private void validateDescription(String description) throws InvalidDataException {
        if(description==null || description.length()< MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION)
            throw new InvalidDataException(MATERIAL_DESCRIPTION_TOO_SHORT);
    }

    private void validateTitle(String title) throws InvalidDataException {
        if(title==null || title.length()<MIN_NUMBER_OF_CHARACTERS_FOR_TITLE)
            throw new InvalidDataException(MATERIAL_TITLE_TOO_SHORT);

    }


}
