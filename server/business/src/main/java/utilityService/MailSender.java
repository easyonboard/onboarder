package utilityService;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class MailSender {
    final String username;
    final String password;
    final Properties props;

    public MailSender(String username, String password) {
        this.username = username;
        this.password = password;
        props = new Properties();
        if (username.contains("google")){
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
        }
        else if (username.contains("yahoo")){
            props.put("mail.smtp.host", "smtp.mail.yahoo.com");
            props.put("mail.smtp.port", "587");
        }
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "15000");
        //props.setProperty("mail.smtp.proxy.host", "proxy.msg.de");
        //props.setProperty("mail.smtp.proxy.port", "3128");
        props.put("mail.debug", "true");
    }

    public void sendMail(String to, String subject, String content) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            System.out.println("Mail sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MailSender sender = new MailSender("senderMail","senderPassword");
        sender.sendMail("receiverMail","subject","content");
    }
}

