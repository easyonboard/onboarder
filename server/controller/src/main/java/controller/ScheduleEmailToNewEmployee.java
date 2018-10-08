package controller;

import dao.UserInformationRepository;
import dao.UserRepository;
import entity.User;
import entity.UserInformation;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.CheckListService;
import utilityService.MailSender;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

//@Component
@Controller
public class ScheduleEmailToNewEmployee {
    private static final String BUDDY_MAIL_SUBJECT = "Detalii inceput angajat nou";
    private static final String NEW_EMPLOYEE_MAIL_SUBJECT = "Prima zi la MSG";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    public static final String START_HOUR = "09:00";
    public static final String BUDDY_EMAIL_TEMPLATE_FILE_NAME = "buddy_email_template";
    public static final String EMAIL_TEMPLATE_FILE_NAME = "email_template";
    public static final String EMAIL_BODY_VARIABLE_NAME = "email_body";

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private UserRepository userRepository;

    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    private final List<String> mandatoryFieldsFromUserEntity = Arrays.asList("name", "username", "password", "email");
    private final List<String> mandatoryFieldsFromUserInfoEntity = Arrays.asList("team", "floor", "startDate");


    /**
     * try to send email for new employees on 19:00 every weekday
     */

    // @Scheduled(cron = "0 0 19 * * MON-FRI")
    @RequestMapping(value = "/emailsch", method = RequestMethod.GET)
    public ResponseEntity reportCurrentTime() {
        List<UserInformation> usersInfoForUserWhoStartTwoWeeksAfterToday = userInformationRepository.findByStartDateBefore(getTwoWeeksAfterCurrentDate());
        List<User> usersWhoStartTwoWeeksAfterToday = new ArrayList<>();

        usersInfoForUserWhoStartTwoWeeksAfterToday.stream()
                .filter(userInformation -> hasNotNullFields(mandatoryFieldsFromUserInfoEntity, userInformation))
                .forEach(userInformation -> usersWhoStartTwoWeeksAfterToday.add(userInformation.getUserAccount()));

        usersWhoStartTwoWeeksAfterToday.stream()
                .filter(user -> {
                    try {
                        return !checkListService.isMailSentToUser(user);
                    } catch (EntityNotFoundException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .filter(user -> hasNotNullFields(mandatoryFieldsFromUserEntity, user))
                .forEach(user -> {
                    UserInformation ui = findUserInfoByUser(usersInfoForUserWhoStartTwoWeeksAfterToday, user).get();
                    String dateWithZeroTime = null;
                    dateWithZeroTime = FORMATTER.format(ui.getStartDate());
                    String emailBody = createEmailBody(user.getName(), dateWithZeroTime, START_HOUR, ui.getBuddyUser().getName(), ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getLocation().getLocationAddress());

                    MailSender sender = new MailSender();
                    sender.sendMail(user.getEmail(), NEW_EMPLOYEE_MAIL_SUBJECT, emailBody);
                    checkListService.updateFieldMailSent(user.getIdUser(), true);
//                    Optional<User> abteilungsleiterForUser = findAbteilungsleiter(ui);
//                    if (abteilungsleiterForUser.isPresent()) {
//                        sendEmail(user.getEmail(), abteilungsleiterForUser.get(), NEW_EMPLOYEE_MAIL_SUBJECT, emailBody);
//                    }
                    User buddy = ui.getBuddyUser();
                    if (buddy != null) {
                        String names[] = buddy.getName().split(" ");
                        String emailBodyForBuddy = createEmailBodyForBuddy(names[0], user.getName(), dateWithZeroTime, START_HOUR, ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getTeam());
                        sendEmail(buddy.getMsgMail(), null, BUDDY_MAIL_SUBJECT, emailBodyForBuddy);
                        checkListService.updateFieldMailSentToBuddy(user.getIdUser(),true);
                    }
//                    List<User> abteilungsleiters = userRepository.getAbteilungsleiters();
//                    for (User ab : abteilungsleiters) {
//                        if (ui.getBuddyUser().getUserAccount().getDepartment().equals(ab.getUserAccount().getDepartment())) {
//                            //sender.sendMail(ab.getEmail(), "", emailBody);
//                            //sender.sendMail(ui.getBuddyUser().getEmail(), "", emailBody);
//                            sender.sendMail(user.getEmail(), ab.getEmail(), ui.getBuddyUser().getEmail(), "", emailBody);
//                            checkListService.setValue(user, "mailSentToBuddy", true);
//                            checkListService.setValue(user, "mailSent", true);
//                            LOGGER.info("An email has been send to " + user.getName() + " at " + Calendar.getInstance().getTime());
//                            break;
//                        }
//                    }
                });


        return null;
    }


    private void sendEmail(String to, User personInCC, String subject, String body) {
        MailSender sender = new MailSender();
        if (personInCC != null) {
            sender.sendMail(to, personInCC.getMsgMail(), subject, body);
        } else {
            sender.sendMail(to, subject, body);
        }
    }


    private String createEmailBody(String name, String startDate, String startHour, String buddyName, String floor, String building, String locationAddress) {
        ResourceBundle bundle = ResourceBundle.getBundle(EMAIL_TEMPLATE_FILE_NAME, Locale.ROOT);
        String email_body = bundle.getString(EMAIL_BODY_VARIABLE_NAME);
        String names[] = name.split(" ");
        String formattedEmailBoddy = MessageFormat.format(email_body, names[0], startDate, startHour, building, buddyName, floor, building, locationAddress);
        return formattedEmailBoddy;
    }

    private String createEmailBodyForBuddy(String name, String newEmployeeName, String startDate, String time, String floor, String building, String team) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUDDY_EMAIL_TEMPLATE_FILE_NAME, Locale.ROOT);
        String email_body = bundle.getString(EMAIL_BODY_VARIABLE_NAME);
        String formattedEmailBoddy = MessageFormat.format(email_body, name, newEmployeeName, startDate, time, building, team, floor, building);
        return formattedEmailBoddy;
    }


    private Date getTwoWeeksAfterCurrentDate() {
        try {
            LocalDate today = LocalDate.now();
            return new SimpleDateFormat("yyyy-MM-dd").parse(today.plus(1, ChronoUnit.WEEKS).toString());
        } catch (ParseException e) {
            LOGGER.info("Error while parse date  " + e.getMessage());
            return null;
        }
    }

    private Optional<UserInformation> findUserInfoByUser(List<UserInformation> usersInfoForUserWhoStartNextWeek, User user) {
        return usersInfoForUserWhoStartNextWeek.stream()
                .filter(userInformation -> userInformation.getUserAccount() == user)
                .findFirst();
    }

    private boolean hasNotNullFields(List<String> mandatoryFieldsFromEntity, Object object) {
        try {
            for (String fieldName : mandatoryFieldsFromEntity) {
                Field field;
                field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.get(object) == null) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchFieldException e) {
            LOGGER.warning("Error while searching for fields in entities  " + e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            LOGGER.info("Error while searching for fields in entities  " + e.getMessage());
            return false;
        }

    }

    // TODO: findAbteilungsleiter ??
//    private Optional<User> findAbteilungsleiter(UserInformation user) {
//        return userRepository.getAbteilungsleiters().stream().filter(abteilungsleiter -> {
//            UserInformation userInfo = this.userInformationDAO.getUserInformationForUserAccount(abteilungsleiter);
//            return userInfo.getDepartment().equals(user.getDepartment());
//        }).findFirst();
//        return null;
}
