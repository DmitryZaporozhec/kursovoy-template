package kursovoy.mvc;


import kursovoy.model.CapchaRequest;
import kursovoy.model.LoginRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by zaporozhec on 6/5/15.
 */
@Controller
public class PassPhraseController extends AbstractController {

    @RequestMapping(value = "/pass-auth", method = RequestMethod.POST)
    public
    @ResponseBody
    String postCapcha(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest loginRequest
    ) throws Exception {
        List<User> userList = userDao.getRecordById(loginRequest.getLogin());
        User u = userList.get(0);
        UserIpHistory userIpHistory = this.getUserIpHistory(u, request);
        if (loginRequest.getPassword().equals(u.getPassPhrase())) {
            u.setFailLoginCount(0);
            u.setLastLogin(new Date());
            u.setSmsCode(null);
            userIpHistory.setStatus(LoginStatus.SUCCESS_PASS);
            this.save(u, userIpHistory);
            this.saveCookie(u, response);
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            u.setFailLoginCount(u.getFailLoginCount() + 1);
            userIpHistory.setStatus(LoginStatus.FAIL_PASS);
            this.save(u, userIpHistory);
            return "Wrong Pass Phrase!";
        }
    }

    @RequestMapping(value = "/pass-auth", method = RequestMethod.GET)
    public String processRequest(HttpServletRequest request,
                                 HttpServletResponse response, Model model, @RequestParam(value = "userId", required = true) String userId)
            throws ServletException, IOException {
        model.addAttribute("userId", userId);
        return "passPhrase";
    }

}
