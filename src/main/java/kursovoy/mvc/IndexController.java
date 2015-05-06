package kursovoy.mvc;

import kursovoy.model.User;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class IndexController {

    @RequestMapping("/userList")
    public String index(Model model) {
        //Get all users
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User("Dmitry", "Zaporozhec", 22));
        }
        model.addAttribute("users", userList);
        return "users";
    }

}
