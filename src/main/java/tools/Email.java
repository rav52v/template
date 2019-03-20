package main.java.tools;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static main.java.utils.ConfigService.getConfigService;

public class Email {

  private String to;
  private String from;
  private String username;
  private String password;
  private String host;

  private Properties props;
  private Session session;
  private Message message;

  public Email() {
    to = getConfigService().getStringProperty("email.receiverEmail");
    from = getConfigService().getStringProperty("email.senderEmail");
    username = getConfigService().getStringProperty("email.login");
    password = getConfigService().getStringProperty("email.password");
    host = "smtp.gmail.com";

    props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "25");

    session = Session.getInstance(props,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
              }
            });
    message = new MimeMessage(session);
  }

  public void sendEmail(String subject, String value) {

    try {
      message.setFrom(new InternetAddress(from));
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(to));
      message.setSubject(subject);
      message.setText(value);
      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("");
    }

  }
}