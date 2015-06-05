package kursovoy.sms;

import java.io.IOException;

/**
 * Created by zaporozhec on 6/5/15.
 */
public interface SmsSender {
    public String send(String to, String text) throws IOException;
}
