package kursovoy.mvc;


import kursovoy.model.CapchaRequest;
import kursovoy.model.SenderRequest;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by zaporozhec on 6/5/15.
 */
@Controller
public class CapchaController extends AbstractController {

    @RequestMapping(value = "/captcha-auth", method = RequestMethod.POST)
    public
    @ResponseBody
    String postCapcha(HttpServletRequest request, HttpServletResponse response, @RequestBody CapchaRequest smsRequest
    ) throws Exception {
        List<User> userList = userDao.getRecordById(smsRequest.getUserId());
        User u = userList.get(0);
        UserIpHistory userIpHistory = this.getUserIpHistory(u, request);
        if (smsRequest.getCaptcha().equals(smsRequest.getInputCaptcha())) {
            u.setFailLoginCount(0);
            u.setLastLogin(new Date());
            u.setSmsCode(null);
            userIpHistory.setStatus(LoginStatus.SUCCESS_CAPCHA);
            this.save(u, userIpHistory);
            this.saveCookie(u, response);
            response.setStatus(HttpServletResponse.SC_OK);
            return "/userList";
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            u.setFailLoginCount(u.getFailLoginCount() + 1);
            userIpHistory.setStatus(LoginStatus.FAIL_CAPCHA);
            this.save(u, userIpHistory);
            return "/get-capcha?userId=" + u.getId();
        }
    }

    @RequestMapping(value = "/get-capcha", method = RequestMethod.GET)
    public String processRequest(HttpServletRequest request,
                                 HttpServletResponse response, Model model, @RequestParam(value = "userId", required = true) String userId)
            throws ServletException, IOException {

        int width = 206;
        int height = 80;
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font("Arial", Font.ITALIC, ((int) (Math.random() * 12) + 20));
        g2d.setFont(font);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0,
                new Color(221, 221, 221), width / 2, height / 2,
                new Color(255, 255, 255), true);

        g2d.setPaint(gp);
        Random r = new Random();
        g2d.fillRect(0, 0, width, height);
        Color c = new Color(Math.abs(r.nextInt()) % 100, Math.abs(r.nextInt()) % 100, Math.abs(r.nextInt()) % 100);
        g2d.setColor(c);


        String captcha = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        char[] data = captcha.toCharArray();

        int x = 0;
        int y = 0;

        for (int i = 0; i < data.length; i++) {
            x += 14 + (Math.abs(r.nextInt()) % 15);
            y = 40 + Math.abs(r.nextInt()) % 20;
            g2d.drawChars(data, i, 1, x, y);
        }
        g2d.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        ImageIO.write(bufferedImage, "png", b64);
        String result = os.toString("UTF-8");
        model.addAttribute("image", result);
        model.addAttribute("captcha", captcha);
        model.addAttribute("userId", userId);
        os.close();
        return "capcha";
    }

}