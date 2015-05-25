package kursovoy.mvc;

import kursovoy.model.LoginRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import org.apache.tomcat.util.codec.binary.Base64;
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
public class LoginController extends AbstractController{
    final static String AUTH_KEY = "AUTH_KEY";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return this.get(request, response, model);
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
        UserIpHistory userIpHistory = new UserIpHistory();
        userIpHistory.setUserId(u.getId());
        userIpHistory.setIpAddress(request.getRemoteHost());
        userIpHistory.setLoginDate(new Date());
        userIpHistory.setUserAgent(request.getHeader("User-Agent"));
        userIpHistory.setLocale(request.getLocale().getLanguage());
        if (encodedPasswordFromForm.equals(u.getPassword())) {
            //Good password
            Cookie cook = new Cookie(AUTH_KEY, String.valueOf(userList.get(0).getId()));
            //seconds
            cook.setMaxAge(300);
            response.addCookie(cook);
            u.setFailLoginCount(0);
            u.setLastLogin(new Date());
            userIpHistory.setStatus(LoginStatus.SUCCESSFUL);
            this.save(u,userIpHistory);
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            //bad password
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            u.setFailLoginCount(u.getFailLoginCount()+1);
            userIpHistory.setStatus(LoginStatus.FAILURE);
            this.save(u,userIpHistory);
            return "Wrong Password!";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie(AUTH_KEY, ""));
        return "login";
    }
}
