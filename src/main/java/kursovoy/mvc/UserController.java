package kursovoy.mvc;

import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController extends AbstractController{

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model) {

        List<User> allUsers = userDao.getAllRecords();
        for (User u:allUsers){
            u.setUserIpHistoryList(userIpHistoryDao.get("USER_ID",String.valueOf(u.getId())));
        }
        //Get all users
        model.addAttribute("users", allUsers);
        return "users";

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "userId", required = false) final String userId) {
        User u = new User();
        if (userId != null) {
            List<User> allUsers = userDao.getRecordById(userId);
            if (!org.springframework.util.CollectionUtils.isEmpty(allUsers))
                u = allUsers.get(0);
        }
        if (u.getPassword() != null && u.getPassword().length() > 0)
            u.setPassword(new String(Base64.decodeBase64(u.getPassword())));
        model.addAttribute("user", u);
        return "user";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, @RequestParam(value = "userId", required = true) final int userId)
    {
        userDao.delete(userId);
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
                final HttpServletResponse response, final @RequestBody User u) throws Exception{
        try {
            u.setPassword(Base64.encodeBase64String(u.getPassword().getBytes()));
            userDao.save(u);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
        return "OK";
    }


}
