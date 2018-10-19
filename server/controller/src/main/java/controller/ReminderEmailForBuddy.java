package controller;

import dao.UserRepository;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilityService.MailSender;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

//@Component
@Controller
public class ReminderEmailForBuddy {
    private static final String BUDDY_MAIL_SUBJECT = "Detalii inceput angajat nou";
    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    MailSender sender = new MailSender();


    @Autowired
    private UserRepository userRepository;


    // @Scheduled(cron = "0 59 8 * * MON-FRI")
    @RequestMapping(value = "/reminderBuddy", method = RequestMethod.GET)
    public void reminderForBuddy() {
        List<User> usersWhoStartTomorrow = userRepository.findByStartDateBefore(getTomorrowDate());

        usersWhoStartTomorrow.stream()
                .filter(ui -> ui.getMateUsername() != null);

        usersWhoStartTomorrow
                .forEach(ui -> {
                    Optional<User> userOptional = userRepository.findByUsername(ui.getMateUsername());
                    if (userOptional.isPresent()) {
                        User mateUser=userOptional.get();
                        String emailBody = createEmailBodyForBuddy(mateUser.getFirstName(), ui.getFirstName(), "09:00", ui.getFloor(), ui.getLocation().getLocationName().name(), ui.getTeam());
                        sendEmail(mateUser.getEmail(), BUDDY_MAIL_SUBJECT, emailBody);
                    }
                        }
                );
    }

    private String createEmailBodyForBuddy(String name, String newEmployeeName, String time, String floor, String building, String team) {
        ResourceBundle bundle = ResourceBundle.getBundle("buddy_reminder_email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        return MessageFormat.format(email_body, name, newEmployeeName, time, building, team, floor);
    }


    private void sendEmail(String to, String subject, String body) {
        sender.sendMail(to, subject, body);
    }

    private Date getTomorrowDate() {
        LocalDate today = LocalDate.now();
        return Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
