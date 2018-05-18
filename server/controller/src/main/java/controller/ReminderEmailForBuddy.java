package controller;

import dao.UserInformationDAO;
import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilityService.MailSender;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

//@Component
@Controller
public class ReminderEmailForBuddy {
    private static final String BUDDY_MAIL_SUBJECT = "Detalii inceput angajat nou";
    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    MailSender sender = new MailSender();

    @Autowired
    private UserInformationDAO userInformationDAO;

    // @Scheduled(cron = "0 59 8 * * MON-FRI")
    @RequestMapping(value = "/reminderBuddy", method = RequestMethod.GET)
    public void reminderForBuddy() {
        List<UserInformation> usersInfoForUserWhoStartTomorrow = userInformationDAO.usersWhoStartOnGivenDate(getTomorrowDate());
        usersInfoForUserWhoStartTomorrow.stream()
                .filter(ui -> ui.getBuddyUser() != null);

        usersInfoForUserWhoStartTomorrow
                .forEach(ui -> {
                            String emailBody = createEmailBodyForBuddy(ui.getBuddyUser().getName(), ui.getUserAccount().getName(), "09:00", ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getTeam() );
                            sendEmail(ui.getBuddyUser().getEmail(), BUDDY_MAIL_SUBJECT, emailBody);
                        }
                );
    }

    private String createEmailBodyForBuddy(String name, String newEmployeeName, String time, String floor, String building, String team) {
        ResourceBundle bundle = ResourceBundle.getBundle("buddy_reminder_email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String formattedEmailBody = MessageFormat.format(email_body, name, newEmployeeName, time, building, team, floor);
        return formattedEmailBody;
    }


    private void sendEmail(String to, String subject, String body) {
        sender.sendMail(to, subject, body);
    }

    public Date getTomorrowDate() {
        try {
            LocalDate today = LocalDate.now();
            return new SimpleDateFormat("yyyy-MM-dd").parse(today.plus(1, ChronoUnit.DAYS).toString());
        } catch (ParseException e) {
            LOGGER.info("Error while parse date  " + e.getMessage());
            return null;
        }
    }

}
