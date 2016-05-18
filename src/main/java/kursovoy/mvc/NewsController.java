package kursovoy.mvc;

import kursovoy.jdbc.JDBCDisciplineUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.model.Discipline;
import kursovoy.model.News;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<News> get(HttpServletRequest request, Model model) {
        JDBCNewsUtil jdbcDisciplineUtil = new JDBCNewsUtil();
        List<News> allDisciplines = jdbcDisciplineUtil.getTop10News();
        return allDisciplines;

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    News getNews(Model model, @RequestParam(value = "id", required = false) final String id) {
        News d = new News();
        if (id != null) {
            JDBCNewsUtil jdbcUserUtil = new JDBCNewsUtil();
            d = jdbcUserUtil.getNews(id);
        }
        return d;
    }
}
