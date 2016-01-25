package kursovoy.mvc;

import kursovoy.jdbc.JDBCUtil;
import kursovoy.model.LoginRequest;
import kursovoy.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {
    final static String MY_COOKIE_NAME = "KursovoiCookie";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws Exception {
        JDBCUtil util = new JDBCUtil();
        List<User> userList = util.getUser("LOGIN", loginRequest.getLogin());
        if (CollectionUtils.isEmpty(userList)) {
            // no such user
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Wrong User Name!";
        }

        String encodedPasswordFromForm = Base64.encodeBase64String(loginRequest.getPassword().getBytes());
        if (encodedPasswordFromForm.equals(userList.get(0).getPassword())) {
            //Good password
            Cookie cook = new Cookie(MY_COOKIE_NAME, String.valueOf(userList.get(0).getUserId()));
            //seconds
            cook.setMaxAge(300);
            response.addCookie(cook);
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            //bad password
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Wrong Password!";
        }
    }
}
