package kursovoy.mvc;

import kursovoy.model.LoginRequest;
import kursovoy.model.SmsRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import kursovoy.sms.SmsSender;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController extends AbstractController {
    final static String AUTH_KEY = "AUTH_KEY";
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SmsSender smsSender;

    @RequestMapping(value = "/login", method = RequestMethod.GET)

    public String get(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return this.get(request, response, model);
    }


    @RequestMapping(value = "/sms-auth", method = RequestMethod.POST)
    public
    @ResponseBody
    String postSMS(HttpServletRequest request, HttpServletResponse response, @RequestBody SmsRequest smsRequest
    ) throws Exception {
        List<User> userList = userDao.get("LOGIN", smsRequest.getLogin());
        User u = userList.get(0);
        UserIpHistory userIpHistory = this.getUserIpHistory(u, request);
        if (smsRequest.getSmsCode().equals(u.getSmsCode())) {
            u.setFailLoginCount(0);
            u.setLastLogin(new Date());
            u.setSmsCode(null);
            userIpHistory.setStatus(LoginStatus.SUCCESS_SMS);
            this.save(u, userIpHistory);
            this.saveCookie(u, response);
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            u.setFailLoginCount(u.getFailLoginCount() + 1);
            userIpHistory.setStatus(LoginStatus.FAIL_SMS);
            this.save(u, userIpHistory);
            return "Wrong Code!";
        }
    }

    @RequestMapping(value = "/sms-auth", method = RequestMethod.GET)
    public String getSMS(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "userId", required = true) final String userId) throws Exception {
        User u = null;
        try {
            u = userDao.getRecordById(userId).get(0);
        } catch (Exception e) {
            log.error("No such user! " + e);
        }
        int code = (int) (Math.random() * 1000) + 1000;
        u.setSmsCode(String.valueOf(code));
        userDao.save(u);
        smsSender.send(u.getPhone(), "Your code=" + code);
        log.debug("CODE!!!!=" + code);
        model.addAttribute("login", u.getLogin());
        return "sms-auth";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws Exception {
        List<User> userList = userDao.get("LOGIN", loginRequest.getLogin());
        if (CollectionUtils.isEmpty(userList)) {
            // no such user
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Wrong User Name!";
        }
        String encodedPasswordFromForm = Base64.encodeBase64String(loginRequest.getPassword().getBytes());
        User u = userList.get(0);
        UserIpHistory userIpHistory = this.getUserIpHistory(u, request);
        if (encodedPasswordFromForm.equals(u.getPassword())) {
            u.setLastLogin(new Date());
            userIpHistory.setStatus(LoginStatus.SUCCESSFUL);
            this.save(u, userIpHistory);
            if (u.getFailLoginCount() < 3) {
                u.setFailLoginCount(0);
                this.saveCookie(u, response);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                return "/sms-auth?userId=" + u.getId();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            //bad password
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            u.setFailLoginCount(u.getFailLoginCount() + 1);
            userIpHistory.setStatus(LoginStatus.FAILURE);
            this.save(u, userIpHistory);
            return "Wrong Password!";
        }
    }

    private void saveCookie(User u, HttpServletResponse response) {
        //Good password
        Cookie cook = new Cookie(AUTH_KEY, String.valueOf(u.getId()));
        //seconds
        cook.setMaxAge(300);
        response.addCookie(cook);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie(AUTH_KEY, ""));
        return "login";
    }

    private UserIpHistory getUserIpHistory(User u, HttpServletRequest request) {
        UserIpHistory userIpHistory = new UserIpHistory();
        userIpHistory.setUserId(u.getId());
        userIpHistory.setIpAddress(request.getRemoteHost());
        userIpHistory.setLoginDate(new Date());
        userIpHistory.setUserAgent(request.getHeader("User-Agent"));
        userIpHistory.setLocale(request.getLocale().getLanguage());
        return userIpHistory;
    }
}
