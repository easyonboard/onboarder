package validator;

import dao.UserRepository;
import dto.UserDto;
import exception.types.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for {@link UserDto}
 */
@Service
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_MSG_MAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@msg.group$", Pattern.CASE_INSENSITIVE);
    private static final String EMAIL_FORMAT_ERROR = "Personal e-mail not valid ";
    private static final String MSG_EMAIL_FORMAT_ERROR = ".msg e-mail not valid ";
    private static final String EMAIL_EMPTY_ERROR = "Personal e-mail can not be empty";
    private static final String MSG_EMAIL_EMPTY_ERROR = ".msg e-mail can not be empty";
    private static final String NAME_EMPTY_ERROR = "Name can not be empty";
    private static final String PASSWORD_LENGTH_ERROR = "Password must have at least 6 characters";
    private static final String PASSWORD_EMPTY_ERROR = "Password  can not be empty";
    private static final String USERNAME_LENGTH_ERROR = "Username must have at least 6 characters";
    private static final String USERNAME_EXISTS_ERROR = "Username already exists ";
    private static final int MIN_NUMBER_CHARACTERS = 6;

    public void validateUserData(UserDto user) throws InvalidDataException {

        validateName(user.getName());
        validateEmail(user.getEmail());
        validateMsgMail(user.getMsgMail());
        validatePassword(user.getPassword());
    }

    public void validateUsername(String username) throws InvalidDataException {

        if (username.length() < MIN_NUMBER_CHARACTERS)
            throw new InvalidDataException(USERNAME_LENGTH_ERROR);
        if (userRepository.findByUsername(username).isPresent())
            throw new InvalidDataException(USERNAME_EXISTS_ERROR);
    }

    private void validatePassword(String password) throws InvalidDataException {

        if (password.length() < MIN_NUMBER_CHARACTERS)
            throw new InvalidDataException(PASSWORD_LENGTH_ERROR);
        if (checkIfEmpty(password))
            throw new InvalidDataException(PASSWORD_EMPTY_ERROR);
    }

    private void validateEmail(String email) throws InvalidDataException {

        if (checkIfEmpty(email))
            throw new InvalidDataException(EMAIL_EMPTY_ERROR);

        if (!emailPatternMatcher(email)) {
            throw new InvalidDataException(EMAIL_FORMAT_ERROR);
        }
    }

    private void validateMsgMail(String msgMail) throws InvalidDataException {

        if (checkIfEmpty(msgMail))
            throw new InvalidDataException(MSG_EMAIL_EMPTY_ERROR);
        if (!msgMailPatternMatcher(msgMail)) {
            throw new InvalidDataException(MSG_EMAIL_FORMAT_ERROR);
        }
    }

    private void validateName(String name) throws InvalidDataException {

        if (checkIfEmpty(name))
            throw new InvalidDataException(NAME_EMPTY_ERROR);
    }

    private boolean checkIfEmpty(String value) {

        return (value == null || value == "");
    }

    private boolean msgMailPatternMatcher(String msgMail) {

        Matcher matcher = VALID_MSG_MAIL_ADDRESS_REGEX.matcher(msgMail);
        return matcher.find();
    }

    private boolean emailPatternMatcher(String email) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
