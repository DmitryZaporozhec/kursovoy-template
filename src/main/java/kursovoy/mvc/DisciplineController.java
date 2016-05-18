package kursovoy.mvc;

import kursovoy.constants.UserType;
import kursovoy.jdbc.JDBCDisciplineUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.jdbc.JDBCUserUtil;
import kursovoy.model.Discipline;
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
@RequestMapping(value = "/discipline")
public class DisciplineController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model) {
        JDBCDisciplineUtil jdbcDisciplineUtil = new JDBCDisciplineUtil();
        List<Discipline> allDisciplines = jdbcDisciplineUtil.getAllDisciplines();
        model.addAttribute("disciplines", allDisciplines);
        return "disciplines";

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "id", required = false) final String id) {
        Discipline d = new Discipline();
        if (id != null) {
            JDBCDisciplineUtil jdbcUserUtil = new JDBCDisciplineUtil();
            d = jdbcUserUtil.getDiscipline(id);
        }
        model.addAttribute("discipline", d);
        return "discipline";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, @RequestParam(value = "id", required = true) final String id) {
        JDBCDisciplineUtil jdbcUserUtil = new JDBCDisciplineUtil();
        JDBCNewsUtil newsJDBC = new JDBCNewsUtil();
        Discipline d = jdbcUserUtil.getDiscipline(id);
        News delDisc = new News();
        delDisc.setCaption("Удаление дисциплины");
        delDisc.setText("Дисциплина " + d.getName() + " удалена!");
        jdbcUserUtil.delete(Integer.valueOf(id));
        newsJDBC.saveNews(delDisc);
        return "redirect:/discipline/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody Discipline d) throws Exception {
        JDBCDisciplineUtil jdbcUserUtil = new JDBCDisciplineUtil();
        try {
            jdbcUserUtil.saveUser(d);
            if (d.getDisciplineId() == 0) {
                JDBCNewsUtil newsJDBC = new JDBCNewsUtil();
                News delDisc = new News();
                delDisc.setCaption("Создание дисциплины");
                delDisc.setText("Дисциплина " + d.getName() + " создана!");
                newsJDBC.saveNews(delDisc);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
        return "OK";
    }


}
