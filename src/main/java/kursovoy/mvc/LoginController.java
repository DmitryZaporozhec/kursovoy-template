package kursovoy.mvc;

import kursovoy.model.LoginRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import kursovoy.model.constants.Risk;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

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
        UserIpHistory userIpHistory = this.getUserIpHistory(u, request);
        if (encodedPasswordFromForm.equals(u.getPassword())) {
            u.setLastLogin(new Date());
            userIpHistory.setStatus(LoginStatus.SUCCESSFUL);
            this.save(u, userIpHistory);
            Risk risk = riskCalculator.process(u);
            String retVal = "/userList";
            switch (risk) {
                case NO:
                    u.setFailLoginCount(0);
                    this.saveCookie(u, response);
                    break;
                case LOW:
                    response.setStatus(HttpServletResponse.SC_OK);
                    retVal = "/pass-auth?userId=" + u.getId();
                    break;
                case MEDIUM:
                    response.setStatus(HttpServletResponse.SC_OK);
                    retVal = "/get-capcha?userId=" + u.getId();
                    break;
                case HIGH:
                    response.setStatus(HttpServletResponse.SC_OK);
                    retVal = "/sender-auth?userId=" + u.getId();
                    break;
                default:
                    break;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return retVal;
        } else {
            //bad password
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            u.setFailLoginCount(u.getFailLoginCount() + 1);
            userIpHistory.setStatus(LoginStatus.FAILURE);
            this.save(u, userIpHistory);
            return "Wrong Password!";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie(AUTH_KEY, ""));
        return "login";
    }


}
