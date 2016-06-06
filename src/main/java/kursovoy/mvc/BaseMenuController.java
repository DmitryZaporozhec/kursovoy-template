package kursovoy.mvc;

import kursovoy.jdbc.JDBCDisciplineUtil;
import kursovoy.jdbc.JDBCModuleUtil;
import kursovoy.jdbc.JDBCNewsUtil;
import kursovoy.model.Discipline;
import kursovoy.model.Module;
import kursovoy.model.News;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class BaseMenuController {

    protected void getMenu(Model model, int courseId, int moduleId) {
        if (courseId != 0) {
            List<Module> con = new JDBCModuleUtil().getModule("COURSE_ID", String.valueOf(courseId));
            model.addAttribute("menu", con);
            model.addAttribute("courseId", courseId);
        }
        if (moduleId != 0) {
            List<Module> con = new JDBCModuleUtil().getMenu(moduleId);
            model.addAttribute("menu", con);
            if (con != null && con.size() > 0)
                model.addAttribute("courseId", con.get(0).getCourseId());
        }
    }

}
