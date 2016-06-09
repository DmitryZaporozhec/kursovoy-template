package kursovoy.mvc;

import kursovoy.jdbc.JDBCGroupUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.jdbc.JDBCUserUtil;
import kursovoy.model.Group;
import kursovoy.model.News;
import kursovoy.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/group")
public class GroupController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model) {
        JDBCGroupUtil jdbcDisciplineUtil = new JDBCGroupUtil();
        List<Group> groups = jdbcDisciplineUtil.getAll();
        model.addAttribute("groups", groups);
        return "groups";

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam(value = "id", required = false) final String id) {
        Group d = new Group();
        if (id != null) {
            JDBCGroupUtil util = new JDBCGroupUtil();
            d = util.get(id);
        }
        model.addAttribute("group", d);
        return "group";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody Group u) throws Exception {
        JDBCGroupUtil util = new JDBCGroupUtil();
        try {
            util.save(u);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
        return "OK";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, @RequestParam(value = "id", required = true) final int userId) {
        JDBCGroupUtil util = new JDBCGroupUtil();
        util.delete(userId);
        return "redirect:/group/list";
    }
}
