package kursovoy.sender;

import java.io.IOException;

/**
 * Created by zaporozhec on 6/5/15.
 */
public interface Sender {
    public void send(String to, String text) throws IOException;
}
