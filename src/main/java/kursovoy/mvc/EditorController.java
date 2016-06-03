package kursovoy.mvc;

import kursovoy.constants.ContentType;
import kursovoy.jdbc.*;
import kursovoy.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/editor")
public class EditorController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(Model model, @RequestParam(value = "id", required = false) String id,
                          @RequestParam(value = "parentId", required = false) final Integer parentId,
                          @RequestParam(value = "type", required = false) final String type) {
        JDBCContentUtil contentUtil = new JDBCContentUtil();
        FroalaModel content = null;
        if (id == null) {
            content = new FroalaModel();
            content.setCourseId(parentId);
            content.setType(ContentType.valueOf(type));
        } else {
            content = contentUtil.get(id);

        }
        int idforMenu = content.getCourseId();
        //fill menu
        List<FroalaModel> con = contentUtil.get("COURSE_ID", String.valueOf(idforMenu));
        model.addAttribute("menu", con);
        model.addAttribute("content", content);
        return "editor";
    }

    @RequestMapping(value = "/get/body", method = RequestMethod.GET)
    public
    @ResponseBody
    String getBody(Model model, @RequestParam(value = "id", required = true) int id) {
        if (id == 0) {
            //FIXME add here template
            return "";
        }
        JDBCContentUtil contentUtil = new JDBCContentUtil();
        FroalaModel content = null;
        content = contentUtil.get(String.valueOf(id));
        return content.getBody();
    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET)
    public String getUser(HttpServletResponse response, @PathVariable("id") final String name) throws Exception {
        int BUFF_SIZE = 1024;
        Photo photo = new JDBCPhotoUtil().getPhoto(Integer.valueOf(name));
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
        photo.setFileName(UUID.randomUUID().toString());
        photo.setContent(file.getInputStream());
        photo = new JDBCPhotoUtil().save(photo);
        response.setLink("/editor/images/" + photo.getId());
        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(HttpServletRequest request, @RequestBody FroalaModel content) throws Exception {
        JDBCContentUtil util = new JDBCContentUtil();
        int id = util.save(content).getId();
        return String.format("/editor/get?id=%s", String.valueOf(id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
        JDBCContentUtil contentUtil = new JDBCContentUtil();
        FroalaModel content = null;
        content = contentUtil.get(id);
        contentUtil.delete(Integer.valueOf(id));
        return String.format("redirect:/course/get?id=%s", content.getCourseId());
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public String preview(Model model, @PathVariable("id") String id) throws Exception {
        JDBCContentUtil contentUtil = new JDBCContentUtil();
        FroalaModel content = null;
        content = contentUtil.get(id);
        model.addAttribute("content", content);
        return "preview";
    }
}
