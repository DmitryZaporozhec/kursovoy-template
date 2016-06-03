package kursovoy.mvc;

import kursovoy.constants.ContentType;
import kursovoy.jdbc.*;
import kursovoy.model.*;
import kursovoy.utils.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/editor")
public class EditorController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "id", required = false) String id,
                          @RequestParam(value = "parentId", required = true) final int parentId,
                          @RequestParam(value = "type", required = true) final String type) {
        JDBCContentUtil contentUtil = new JDBCContentUtil();
        FloalaContent content = null;
        if (id == null) {
            content = new FloalaContent();
            content.setCourseId(parentId);
            content.setType(ContentType.valueOf(type));
        } else {
            content = contentUtil.get(id);

        }
        int idforMenu = content.getCourseId();
        //fill menu
        List<FloalaContent> con = contentUtil.get("COURSE_ID", String.valueOf(idforMenu));
        model.addAttribute("menu", con);
        model.addAttribute("content", content);
        return "editor";
    }

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public String getUser(HttpServletResponse response, @RequestParam("name") final String name) throws Exception {
        int BUFF_SIZE = 1024;
        Photo photo = new JDBCPhotoUtil().getPhoto(name);
        response.setContentType("image/" + ("jpg".equalsIgnoreCase(photo.getFileType())
                ? "jpeg" :
                photo.getFileType()));
        response.setHeader("Content-Disposition", String.format("filename=\"%s\"", name));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = photo.getContent().read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        try {
            response.setContentLength((byteArray.length));
            response.getOutputStream().write(byteArray);
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            photo.getContent().close();
        }
        return null;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    Response upload(MultipartHttpServletRequest request) throws Exception {
        MultipartFile file = request.getFile("file");
        String[] str = file.getOriginalFilename().split("\\.");
        Photo photo = new Photo();
        Response response = new Response();
        if (str != null && str.length > 1) {
            photo.setFileType(str[str.length - 1]);
        } else {
            response.setLink("/img/download.png");
            return response;
        }
        photo.setFileName(file.getOriginalFilename());
        photo.setContent(file.getInputStream());
        new JDBCPhotoUtil().save(photo);

        response.setLink("/editor/images?name=" + file.getOriginalFilename());

        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, FloalaContent content) throws Exception {
        JDBCContentUtil util = new JDBCContentUtil();
        int id = util.save(content).getId();
        return String.format("redirect:/editor/get?type=%s&parentId=%s&id=%s", content.getType().name(), content.getCourseId(), String.valueOf(id));
    }
}
