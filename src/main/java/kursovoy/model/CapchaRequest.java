package kursovoy.model;

/**
 * Created by zaporozhec on 6/5/15.
 */
public class CapchaRequest {
    String userId;
    String captcha;
    String inputCaptcha;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getInputCaptcha() {
        return inputCaptcha;
    }

    public void setInputCaptcha(String inputCaptcha) {
        this.inputCaptcha = inputCaptcha;
    }
}
