package kursovoy.mvc;

import kursovoy.jdbc.JDBCUtil;
import kursovoy.model.User;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class IndexController {

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String get(Model model) {

        JDBCUtil jdbcUtil = new JDBCUtil();
        List<User> allUsers = jdbcUtil.getAllUsers();
        //Get all users
        model.addAttribute("users", allUsers);
        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "userId", required = false) final String userId) {
        User u = new User();
        if (userId != null) {
            JDBCUtil jdbcUtil = new JDBCUtil();
            List<User> allUsers = jdbcUtil.getUser(userId);
            if (!org.springframework.util.CollectionUtils.isEmpty(allUsers))
                u = allUsers.get(0);
        }
        model.addAttribute("user", u);
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody User u) {
        System.out.println(u);
        return "User Saved!";
    }
}
