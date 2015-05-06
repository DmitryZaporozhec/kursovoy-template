package kursovoy.mvc;

import kursovoy.model.User;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class IndexController {

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String get(Model model) {
        //Get all users
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User("Dmitry", "Zaporozhec", "22"));
        }
        model.addAttribute("users", userList);
        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(Model model) {
        model.addAttribute("CURRENT_TIME",new Date());
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody String save(final HttpServletRequest request,
                       final HttpServletResponse response, final @RequestBody User u) {
        System.out.println(u);
        return "User Saved!";
    }
}
