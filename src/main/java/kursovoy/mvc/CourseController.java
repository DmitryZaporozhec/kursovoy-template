package kursovoy.mvc;

import kursovoy.jdbc.*;
import kursovoy.model.*;
import kursovoy.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/course")
public class CourseController extends BaseMenuController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model) {
        JDBCCourseUtil util = new JDBCCourseUtil();
        List<Course> allDisciplines = util.getAllCoursed();
        model.addAttribute("courses", allDisciplines);
        return "courses";
    }

    @RequestMapping(value = "/my/list", method = RequestMethod.GET)
    public String getMy(HttpServletRequest request, Model model) {
        String userId = CookieUtil.getCurrentUserId(request);
        List<Course> courses = new JDBCUserCourseUtil().get(Integer.parseInt(userId));
        model.addAttribute("courses", courses);
        model.addAttribute("my", "Y");
        return "courses";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "id", required = false) final String id) {
        Course d = new Course();
        if (id != null) {
            JDBCCourseUtil util = new JDBCCourseUtil();
            d = util.getCourse(id);
        }
        List<Discipline> disciplines = new JDBCDisciplineUtil().getAllDisciplines();
        model.addAttribute("course", d);
        model.addAttribute("disciplines", disciplines);
        getMenu(model, d.getId(), 0);
        return "course";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, @RequestParam(value = "id", required = true) final String id) {
        JDBCCourseUtil util = new JDBCCourseUtil();
        JDBCNewsUtil newsJDBC = new JDBCNewsUtil();
        Course d = util.getCourse(id);
        News delDisc = new News();
        delDisc.setCaption("Удаление курса");
        delDisc.setText("Курс " + d.getName() + " удален!");
        util.delete(Integer.valueOf(id));
        newsJDBC.saveNews(delDisc);
        return "redirect:/course/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(final HttpServletRequest request,
                final HttpServletResponse response, final @RequestBody Course d) throws Exception {
        JDBCCourseUtil util = new JDBCCourseUtil();
        try {
            if (d.getUserId() == 0) {
                d.setUserId(Integer.parseInt(CookieUtil.getCurrentUserId(request)));
            }
            d.setCreateDate(new Date());
            util.saveCourse(d);
            if (d.getId() == 0) {
                JDBCNewsUtil newsJDBC = new JDBCNewsUtil();
                News delDisc = new News();
                delDisc.setCaption("Создание Курса");
                delDisc.setText("Курс " + d.getName() + " создан!");
                newsJDBC.saveNews(delDisc);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
        return "OK";
    }

    @RequestMapping(value = "/addModule", method = RequestMethod.GET)
    public String addModule(Model model, @RequestParam(value = "parentId", required = false) final String id) {
        List<Module> modules = new JDBCModuleUtil().getModule("COURSE_ID", String.valueOf(id));
        int lastNum = 1;
        if (modules != null) {
            lastNum = modules.size() + 1;
            Module m = new Module();
            m.setCourseId(Integer.parseInt(id));
            m.setDisplaOrder(lastNum);
            new JDBCModuleUtil().saveModule(m);
        }
        return "redirect:/course/get?id=" + id;
    }

    @RequestMapping(value = "/add-to-my/{id}", method = RequestMethod.GET)
    public String addToList(HttpServletRequest request, Model model, @PathVariable("id") String id) {
        String userId = CookieUtil.getCurrentUserId(request);
        new JDBCUserCourseUtil().save(Integer.parseInt(userId), Integer.parseInt(id));
        return "redirect:/course/list";
    }

    @RequestMapping(value = "/delete-from-my/{id}", method = RequestMethod.GET)
    public String deleteFromList(HttpServletRequest request, Model model, @PathVariable("id") String id) {
        String userId = CookieUtil.getCurrentUserId(request);
        new JDBCUserCourseUtil().delete(Integer.parseInt(userId), Integer.parseInt(id));
        return "redirect:/course/my/list";
    }

    @RequestMapping(value = "/deleteModule", method = RequestMethod.GET)
    public String geleteModule(Model model, @RequestParam(value = "id", required = false) final String id) {
        Module m = new JDBCModuleUtil().getModule(id);
        new JDBCModuleUtil().delete(Integer.valueOf(id));
        return "redirect:/course/get?id=" + m.getCourseId();
    }

}
