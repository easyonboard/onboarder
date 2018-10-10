package controller;

import dao.UserRepository;
import entity.User;
import entity.enums.RoleType;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.CheckListService;
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

@Controller
public class ScheduleEmailToNewEmployee {
    private static final String BUDDY_MAIL_SUBJECT = "Detalii inceput angajat nou";
    private static final String NEW_EMPLOYEE_MAIL_SUBJECT = "Data inceput msg";


    @Autowired
    private CheckListService checkListService;

    @Autowired
    private UserRepository userRepository;

    private final Logger LOGGER = Logger.getLogger(ScheduleEmailToNewEmployee.class.getName());
    private final List<String> mandatoryFieldsFromUserEntity = Arrays.asList("name", "username", "password", "email", "team", "floor", "startDate");


    /**
     * try to send email for new employees on 19:00 every weekday
     */

    // @Scheduled(cron = "0 0 19 * * MON-FRI")
    @RequestMapping(value = "/emailsch", method = RequestMethod.GET)
    public ResponseEntity reportCurrentTime() {
        List<User> usersInfoForUserWhoStartNextWeek = userRepository.findByStartDateAfter(new Date());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


        usersInfoForUserWhoStartNextWeek.stream()
                .filter(user -> hasNotNullFields(mandatoryFieldsFromUserEntity, user))
                .filter(user -> {
                            try {
                                return !checkListService.isMailSentToUser(user);
                            } catch (EntityNotFoundException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                )
                .forEach(user -> {
                    String dateWithZeroTime = null;
                    dateWithZeroTime = formatter.format(user.getStartDate());
                    String emailBody = createEmailBody(user.getName(), dateWithZeroTime, "09:00", user.getMate().getName(), user.getFloor(), user.getLocation().getLocationName().name(), user.getLocation().getLocationAddress());
                    String emailBodyBuddy = createEmailBodyForBuddy(user.getName(), user.getName(), dateWithZeroTime, user.getMate().getName(), user.getFloor(), user.getLocation().getLocationName().name(), user.getTeam());

                    User abteilungsleiter = userRepository.findUserByRoleAndDepartment(RoleType.ROLE_ABTEILUNGSLEITER,user.getDepartment());
                    sendEmail(user.getEmail(), abteilungsleiter, NEW_EMPLOYEE_MAIL_SUBJECT, emailBody);
                    checkListService.updateFieldMailSent(user.getIdUser(),true);
                    sendEmail(user.getMate().getMsgMail(), null, BUDDY_MAIL_SUBJECT, emailBodyBuddy);
                    checkListService.updateFieldMailSentToBuddy(user.getIdUser(),true);

                });


        return null;
    }

    // TODO: findAbteilungsleiter ??
    private Optional<User> findAbteilungsleiter(User user) {
//        return userRepository.getAbteilungsleiters().stream().filter(abteilungsleiter -> {
//            UserInformation userInfo = this.userInformationDAO.getUserInformationForUserAccount(abteilungsleiter);
//            return userInfo.getDepartment().equals(user.getDepartment());
//        }).findFirst();
        return null;
    }


    /**
     * Modificat parametrul "to" din user -> string email pentru ca buddy sa primeasca email pe mail-ul de msg iar angajatul nou pe mailul personal
     */
    private void sendEmail(String to, User personInCC, String subject, String body) {
        MailSender sender = new MailSender();
        if (personInCC != null) {
            sender.sendMail(to, personInCC.getEmail(), subject, body);
        } else {
            sender.sendMail(to, subject, body);
        }
    }


    private String createEmailBody(String name, String startDate, String s, String buddyName, String floor, String building, String locationAddress) {
        ResourceBundle bundle = ResourceBundle.getBundle("email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String names[] = name.split(" ");
        String formattedEmailBoddy = MessageFormat.format(email_body, names[0], startDate, s, building, buddyName, floor, building, locationAddress);
        return formattedEmailBoddy;
    }

    private String createEmailBodyForBuddy(String name, String newEmployeeName, String startDate, String time, String floor, String building, String team) {
        ResourceBundle bundle = ResourceBundle.getBundle("buddy_email_template", Locale.ROOT);
        String email_body = bundle.getString("email_body");
        String formattedEmailBoddy = MessageFormat.format(email_body, name, newEmployeeName, startDate, time, building, team, floor, building);
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

    // TODO: getAbteilungsleiters ?
//    public List<User> getAbteilungsleiters() {
//        //        String queryString = "select u from User u where u.role.role=:role";
//        //        Query query = this.em.createQuery(queryString);
//        //        query.setParameter("role", ROLE_ABTEILUNGSLEITER);
//        //        return query.getResultList();
//        CriteriaBuilder cb = this.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
//        Root<User> rootUser = criteriaQuery.from(User.class);
//        criteriaQuery.select(
//                cb.construct(User.class, rootUser.get("idUser"), rootUser.get("name"), rootUser.get("username"),
//                             rootUser.get("email"), rootUser.get("msgMail"))).
//                where(cb.equal(rootUser.get("role").get("role"), ROLE_ABTEILUNGSLEITER));
//        List<User> users = this.executeCriteriaQuery(criteriaQuery);
//        return users;
//    }
}