package kursovoy.sms;

import kursovoy.model.sms.BasicResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static kursovoy.sms.Util.encode;

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
@Service
public class SmsSenderImpl implements SmsSender {
    private static final Logger log = LoggerFactory.getLogger(SmsSenderImpl.class);
    private static final String API_URL = "http://api.sms24x7.ru/";
    @Value("${sms.sender.user.name}")
    private String email;
    @Value("${sms.sender.password}")
    private String password;

    private final HttpClient HTTP_CLIENT;

    {
        final PoolingClientConnectionManager ccm = new PoolingClientConnectionManager();
        ccm.setMaxTotal(1);
        HTTP_CLIENT = new DefaultHttpClient(ccm);
    }

    public static void main(String[] args) throws IOException {
        log.debug("Starting SMS24X7");
        if (args != null && args.length == 2) {
            final SmsSenderImpl sms = new SmsSenderImpl();
            sms.send(args[0], args[1]);
        } else {
            log.warn("Usage: SendSMS login password from to message_text");
        }
    }

    /**
     * Send SMS immediately. Use on-fly authentication. I.e. credentials will be send in the same request as sms text. Sends only one request.
     *
     * @param to   Recipient phone number in international format. E.g. 79991234567
     * @param text Text of the message
     * @return Server response body
     * @throws IOException if something bad happened with internet connection
     */
    @Override
    public String send(String to, String text) throws IOException {
        log.info("Sending sms to " + to + " using on-fly authentication");
        final HttpGet get;
        final String result;
        text = encode(text);
        to = encode(to);
        get = new HttpGet(API_URL + "?method=push_msg&email=" + encode(email) + "&password=" + encode(password) + "&text=" + text + "&phone=" + to + "&satellite_adv=OBLIGATORY");
        result = HTTP_CLIENT.execute(get, new BasicResponseHandler());
        log.trace(result);
        return result;
    }
}