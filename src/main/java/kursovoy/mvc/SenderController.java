package kursovoy.mvc;

import kursovoy.model.SenderRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by zaporozhec on 6/19/15.
 */
@Controller
public class SenderController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SenderController.class);

    @RequestMapping(value = "/sender-auth", method = RequestMethod.POST)
    public
    @ResponseBody
    String postSMS(HttpServletRequest request, HttpServletResponse response, @RequestBody SenderRequest smsRequest
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

    @RequestMapping(value = "/sender-auth", method = RequestMethod.GET)
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
        StringBuilder text = new StringBuilder();
        text.append("Dear ");
        text.append(u.getFirstName());
        text.append(" ");
        text.append(u.getLastName());
        text.append("\n");
        text.append("Your access code is:");
        text.append(code);
        emailSenderImpl.send(u.getEmail(), text.toString());
        log.debug("CODE!!!!=" + code);
        model.addAttribute("login", u.getLogin());
        return "sender-auth";
    }
}
