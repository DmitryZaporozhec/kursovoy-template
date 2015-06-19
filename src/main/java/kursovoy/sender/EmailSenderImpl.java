package kursovoy.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static javax.mail.Message.RecipientType.TO;

/**
 * <p>
 * Simple api implementation for <a href="http://sms24x7.ru/">SMS 24x7 service</a>.
 * Not thread safe.
 * </p>
 * <p/>
 * Created 2/16/13 1:13 AM
 *
 * @author mike
 */
@Service("emailSenderImpl")
public class EmailSenderImpl implements Sender {
    private static final Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);
    private static final String API_URL = "http://api.sms24x7.ru/";
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private String port;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;

    @Override
    public void send(String to, String text) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.transport.protocol", "smtp");
        if (username != null && !username.isEmpty()) {
            properties.setProperty("mail.user", username);
            properties.setProperty("mail.password", password);
        }
        if (port != null && !port.isEmpty()) {
            properties.setProperty("mail.smtp.port", port);
        }
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.addRecipient(TO, new InternetAddress(to));
            message.setSubject("Cofirm link", "UTF-8");
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(text, "UTF-8");
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            if (username != null && !username.isEmpty()) {
                properties.setProperty("mail.user", username);
                properties.setProperty("mail.password", password);
                properties.put("mail.smtp.auth", auth);
                properties.put("mail.smtp.starttls.enable", starttls);
                Transport mailTransport = session.getTransport();
                mailTransport.connect(host, username, password);
                mailTransport.sendMessage(message, message.getAllRecipients());
            } else {
                Transport.send(message);
                log.debug("Message successfully sent.");
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}