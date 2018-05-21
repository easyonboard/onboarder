package controller;

import dao.UserDAO;
import dao.UserInformationDAO;
import entity.User;
import entity.UserInformation;
import entity.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.CheckListService;
import service.UserService;
import utilityService.MailSender;

import java.lang.reflect.Field;
import java.text.DateFormat;
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
    private static final String NEW_EMPLOYEE_MAIL_SUBJECT = "Data inceput msg";
    @Autowired
    private UserInformationDAO userInformationDAO;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private UserDAO userDAO;

    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    private final List<String> mandatoryFieldsFromUserEntity = Arrays.asList("name", "username", "password", "email");
    private final List<String> mandatoryFieldsFromUserInfoEntity = Arrays.asList("team", "floor", "startDate");


    /**
     * try to send email for new employees on 19:00 every weekday
     */

    // @Scheduled(cron = "0 0 19 * * MON-FRI")
    @RequestMapping(value = "/emailsch", method = RequestMethod.GET)
    public ResponseEntity reportCurrentTime() {
        List<UserInformation> usersInfoForUserWhoStartNextWeek = userInformationDAO.usersWhoStartOnGivenDate(getNextWeekDate());
        List<User> usersWhoStartNextWeek = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        usersInfoForUserWhoStartNextWeek.stream()
                .filter(userInformation -> hasNotNullFields(mandatoryFieldsFromUserInfoEntity, userInformation))
                .forEach(userInformation -> usersWhoStartNextWeek.add(userInformation.getUserAccount()));

        usersWhoStartNextWeek.stream()
                .filter(user -> !checkListService.isMailSentToUser(user))
                .filter(user -> hasNotNullFields(mandatoryFieldsFromUserEntity, user))
                .forEach(user -> {
                    UserInformation ui = findUserInfoByUser(usersInfoForUserWhoStartNextWeek, user).get();
                    String dateWithZeroTime = null;
                    dateWithZeroTime = formatter.format(ui.getStartDate());
                    String emailBody = createEmailBody(user.getName(), dateWithZeroTime, "09:00", ui.getBuddyUser().getName(), ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getLocation().getLocationAddress());

//                    MailSender sender = new MailSender();
//                    sender.sendMail(user.getEmail(), null, "", emailBody);

                    Optional<User> abteilungsleiterForUser = findAbteilungsleiter(ui);
                    if (abteilungsleiterForUser.isPresent()) {
                        sendEmail(user.getEmail(), abteilungsleiterForUser.get(), NEW_EMPLOYEE_MAIL_SUBJECT, emailBody);
                    }
                    User buddy = ui.getBuddyUser();
                    if (buddy != null){
                        String names[]=buddy.getName().split(" ");
                        String emailBodyForBuddy = createEmailBodyForBuddy(names[1],user.getName(), dateWithZeroTime, "09:00", ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getTeam());
                        sendEmail(buddy.getMsgMail(), null, BUDDY_MAIL_SUBJECT, emailBodyForBuddy);
                    }
//                    List<User> abteilungsleiters = userDAO.getAbteilungsleiters();
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

    private Optional<User> findAbteilungsleiter(UserInformation user) {
        return userDAO.getAbteilungsleiters().stream().filter(abteilungsleiter -> {
            UserInformation userInfo = this.userInformationDAO.getUserInformationForUserAccount(abteilungsleiter);
            return userInfo.getDepartment().equals(user.getDepartment());
        }).findFirst();
    }


    /**
     *
             Modificat parametrul "to" din user -> string email pentru ca buddy sa primeasca email pe mail-ul de msg iar angajatul nou pe mailul personal

     */
    private void sendEmail(String to, User personInCC, String subject, String body) {
        MailSender sender = new MailSender();
        if (personInCC!=null){
        sender.sendMail(to, personInCC.getEmail(), subject, body);}
        else{
            sender.sendMail(to,subject, body);
        }
    }


    private String createEmailBody(String name, String startDate, String s, String buddyName, String floor, String building, String locationAddress) {
        ResourceBundle bundle = ResourceBundle.getBundle("email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String names[]=name.split(" ");
        String formattedEmailBoddy = MessageFormat.format(email_body, names[1], startDate, s, building, buddyName, floor, building, locationAddress);
        return formattedEmailBoddy;
    }

    private String createEmailBodyForBuddy(String name,String newEmployeeName, String startDate, String time, String floor, String building, String team) {
        ResourceBundle bundle = ResourceBundle.getBundle("buddy_email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String formattedEmailBoddy = MessageFormat.format(email_body, name, newEmployeeName,startDate, time, building, team, floor, building);
        return formattedEmailBoddy;
    }


    public Date getNextWeekDate() {
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
            LOGGER.info("Error while searching for fields in entities  " + e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            LOGGER.info("Error while searching for fields in entities  " + e.getMessage());
            return false;
        }

    }


}
