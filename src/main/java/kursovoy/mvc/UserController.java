package kursovoy.mvc;

import kursovoy.constants.UserType;
import kursovoy.jdbc.JDBCGroupUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.jdbc.JDBCUserUtil;
import kursovoy.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model) {

        JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();
        List<User> allUsers = jdbcUserUtil.getAllUsers();
        //Get all users
        model.addAttribute("users", allUsers);
        return "users";

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "userId", required = false) final String userId) {
        User u = new User();
        if (userId != null) {
            JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();
            List<User> allUsers = jdbcUserUtil.getUser(userId);
            if (!org.springframework.util.CollectionUtils.isEmpty(allUsers))
                u = allUsers.get(0);
        }
        if (u.getPassword() != null && u.getPassword().length() > 0)
            u.setPassword(new String(Base64.decodeBase64(u.getPassword())));
        model.addAttribute("userTypes", UserType.values());
        model.addAttribute("user", u);
        model.addAttribute("groups",new JDBCGroupUtil().getAll());
        return "user";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, @RequestParam(value = "userId", required = true) final int userId) {
        JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();
        jdbcUserUtil.delete(userId);
        return "redirect:/userList";
    }


    @RequestMapping(value = "/selfRegistration", method = RequestMethod.GET)
    public String selfRegistration(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody User u) throws Exception {
        JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();
        try {
            u.setPassword(Base64.encodeBase64String(u.getPassword().getBytes()));
            jdbcUserUtil.saveUser(u);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
        return "OK";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(Model model) {
        JDBCNewsUtil jdbcUserUtil = new JDBCNewsUtil();
        model.addAttribute("newsList", jdbcUserUtil.getTop10News());
        return "index";
    }


}
