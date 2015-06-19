package kursovoy.mvc;

import kursovoy.core.intf.RiskCalculator;
import kursovoy.jdbc.UserDao;
import kursovoy.jdbc.UserIpHistoryDao;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zaporozhec on 5/25/15.
 */

public abstract class AbstractController {
    final static String AUTH_KEY = "AUTH_KEY";

    @Autowired
    protected UserDao userDao;
    @Autowired
    protected UserIpHistoryDao userIpHistoryDao;
    @Autowired
    protected RiskCalculator riskCalculator;


    @Autowired
    protected Sender emailSenderImpl;

    protected void save(User u, UserIpHistory history) throws Exception {
        userDao.save(u);
        userIpHistoryDao.save(history);
    }

    protected UserIpHistory getUserIpHistory(User u, HttpServletRequest request) {
        UserIpHistory userIpHistory = new UserIpHistory();
        userIpHistory.setUserId(u.getId());
        userIpHistory.setIpAddress(request.getRemoteHost());
        userIpHistory.setLoginDate(new Date());
        userIpHistory.setUserAgent(request.getHeader("User-Agent"));
        userIpHistory.setLocale(request.getLocale().getLanguage());
        return userIpHistory;
    }

    protected void saveCookie(User u, HttpServletResponse response) {
        //Good password
        Cookie cook = new Cookie(AUTH_KEY, String.valueOf(u.getId()));
        //seconds
        cook.setMaxAge(300);
        response.addCookie(cook);
    }
}
