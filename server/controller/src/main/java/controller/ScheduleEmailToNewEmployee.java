package controller;

import dao.UserInformationDAO;
import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private UserInformationDAO userInformationDAO;

    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    private final List<String> mandatoryFieldsFromUserEntity = Arrays.asList("name", "username", "password", "email");
    private final List<String> mandatoryFieldsFromUserInfoEntity = Arrays.asList("team", "building", "floor", "startDate");


    /**
     * try to send email for new employees on 7:00 every weekday
     */

//    @Scheduled(cron = "0 0 19 * * MON-FRI")
    @RequestMapping(value = "/emailsch", method = RequestMethod.GET)
    public ResponseEntity reportCurrentTime() {
        List<UserInformation> usersInfoForUserWhoStartNextWeek = userInformationDAO.usersWhoStartOnGivenDate(getNextWeekDate());
        List<User> usersWhoStartNextWeek = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        usersInfoForUserWhoStartNextWeek.stream()
                .filter(userInformation -> !userInformation.isMailSent())
                .filter(userInformation -> hasNotNullFields(mandatoryFieldsFromUserInfoEntity, userInformation))
                .forEach(userInformation -> usersWhoStartNextWeek.add(userInformation.getUserAccount()));


        usersWhoStartNextWeek.stream()
                .filter(user -> hasNotNullFields(mandatoryFieldsFromUserEntity, user))
                .forEach(user -> {
                    UserInformation ui = findUserInfoByUser(usersInfoForUserWhoStartNextWeek, user).get();

                    String dateWithZeroTime = null;
                    dateWithZeroTime = formatter.format(ui.getStartDate());

                    System.out.println(dateWithZeroTime);
                    String emailBody = createEmailBody(user.getName(), dateWithZeroTime, "09:00", "aici", ui.getBuddyUser().getName(), ui.getFloor(), ui.getBuilding());

                    MailSender sender = new MailSender();
                    sender.sendMail(user.getEmail(), "Inceput .msg", emailBody);

                    LOGGER.info(emailBody);
                    LOGGER.info("An email has been send to " + user.getName() + " at " + Calendar.getInstance().getTime());
                    userInformationDAO.setEmailSendFlag(ui, true);
                });


        return null;
    }

    private String createEmailBody(String name, String startDate, String s, String aici, String name1, String floor, String building) {
        ResourceBundle bundle = ResourceBundle.getBundle("email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String formattedEmailBoddy = MessageFormat.format(email_body, name, startDate, s, aici, name1, floor, building);
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