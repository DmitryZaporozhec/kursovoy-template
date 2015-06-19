package kursovoy.model;

/**
 * Created by zaporozhec on 6/5/15.
 */
public class SenderRequest {
    String login;
    String smsCode;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
