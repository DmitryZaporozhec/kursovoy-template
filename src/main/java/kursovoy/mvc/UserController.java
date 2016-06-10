package kursovoy.mvc;

import kursovoy.constants.UserType;
import kursovoy.jdbc.JDBCGroupUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.jdbc.JDBCUserUtil;
import kursovoy.model.User;
import kursovoy.utils.Catalogue;
import kursovoy.utils.Translit;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model, @RequestParam(value = "groupId", required = false) String groupId) {

        JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();

        List<User> allUsers = null;
        if (groupId == null)
            allUsers = jdbcUserUtil.getAllUsers();
        else {
            allUsers = jdbcUserUtil.getUser("GROUP_ID", groupId);
            model.addAttribute("currentGroup", groupId);
        }
        //Get all users
        model.addAttribute("users", allUsers);
        model.addAttribute("groups", new JDBCGroupUtil().getAll());
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
        model.addAttribute("groups", new JDBCGroupUtil().getAll());
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
        model.addAttribute("reader", "Y");
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody User u) throws Exception {
        JDBCUserUtil jdbcUserUtil = new JDBCUserUtil();
        if (u.getGroupId() == 0) {
            u.setGroupId(9);
        }
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

    @RequestMapping(value = "/randomfill", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> random(Model model) throws Exception {
        List<User> result = new ArrayList<User>();
        List<String> fmns = this.readLinesFile("/home/zaporozhec/Desktop/1_2016/fmn.txt");
        List<String> mns = this.readLinesFile("/home/zaporozhec/Desktop/1_2016/mn.txt");
        List<String> sns = this.readLinesFile("/home/zaporozhec/Desktop/1_2016/sns.txt");
        Catalogue catalogue = new Catalogue();
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                User u = new User();
                u.setGroupId(i);
                u.setPassword(Base64.encodeBase64String("1234".getBytes()));
                String firstName = catalogue.anyItem(fmns);
                String lastName = catalogue.anyItem(sns);
                while ('а' != (lastName.charAt(lastName.length() - 1))) {
                    lastName = catalogue.anyItem(sns);
                }
                String login = firstName.substring(0, 2) + lastName + (int) ((Math.random() % 100) * 100);
                login = Translit.cyr2lat(login).toLowerCase();
                u.setLogin(login);
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setUserType(UserType.STUDENT);
                result.add(u);
            }
            for (int j = 0; j < 10; j++) {
                User u = new User();
                u.setGroupId(i);
                u.setPassword(Base64.encodeBase64String("1234".getBytes()));
                String firstName = catalogue.anyItem(mns);
                String lastName = catalogue.anyItem(sns);
                while ('а' == (lastName.charAt(lastName.length() - 1))) {
                    lastName = catalogue.anyItem(sns);
                }
                String login = firstName.substring(0, 2) + lastName + (int) ((Math.random() % 100) * 100);
                login = Translit.cyr2lat(login).toLowerCase();
                u.setLogin(login);
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setUserType(UserType.STUDENT);
                result.add(u);
            }
        }
        JDBCUserUtil userUtil = new JDBCUserUtil();
        for (User user : result) {
            userUtil.saveUser(user);
        }
        return result;
    }

    private List<String> readLinesFile(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> vals = new ArrayList<String>();
        try {
            String line = br.readLine();
            while (line != null) {
                vals.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return vals;
    }

}
