package validator;

import dto.MaterialDTO;
import exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for {@link MaterialDTO}
 */
@Service
public class MaterialValidator {

    private static final String MATERIAL_TITLE_TOO_SHORT = "Subject name is too short";
    private static final String MATERIAL_DESCRIPTION_TOO_SHORT = "Subject description is too short";
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION = 20;
    private static final int MIN_NUMBER_OF_CHARACTERS_FOR_TITLE = 5;
    private static final Pattern VALID_LINK_REGEX =
            Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
    private static final String NOT_A_VALID_LINK_EXCEPTION="Not a valid link";

    public void validateMaterial(MaterialDTO material) throws InvalidDataException {
        validateTitle(material.getTitle());
        validateDescription(material.getDescription());
        if (material.getMaterialType().toString().equals("LINK")) {
            validateLink(material.getLink());
        }
    }

    private void validateLink(String link) throws InvalidDataException {
        Matcher matcher=VALID_LINK_REGEX.matcher(link);
        if (!matcher.find())
            throw new InvalidDataException(NOT_A_VALID_LINK_EXCEPTION);
    }

    private void validateDescription(String description) throws InvalidDataException {
        if (description == null || description.length() < MIN_NUMBER_OF_CHARACTERS_FOR_DESCRIPTION)
            throw new InvalidDataException(MATERIAL_DESCRIPTION_TOO_SHORT);
    }

    private void validateTitle(String title) throws InvalidDataException {
        if (title == null || title.length() < MIN_NUMBER_OF_CHARACTERS_FOR_TITLE)
            throw new InvalidDataException(MATERIAL_TITLE_TOO_SHORT);

    }


}
