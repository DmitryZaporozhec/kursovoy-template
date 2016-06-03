package kursovoy.utils;

import kursovoy.jdbc.JDBCUserUtil;
import kursovoy.model.User;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zaporozhec on 6/2/16.
 */
public class CookieUtil {

    public final static String MY_COOKIE_NAME = "KursovoiCookie";

    public static User getCurrentUser(HttpServletRequest request) {
        User result = null;
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cook : cookie) {
                if (MY_COOKIE_NAME.equals(cook.getName())) {
                    String value = cook.getValue();
                    JDBCUserUtil util = new JDBCUserUtil();
                    List<User> userLIst = util.getUser(value);
                    if (!CollectionUtils.isEmpty(userLIst)) {
                        result = userLIst.get(0);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static String getCurrentUserId(HttpServletRequest request) {
        String result = null;
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cook : cookie) {
                if (MY_COOKIE_NAME.equals(cook.getName())) {
                    result = cook.getValue();
                }
            }
        }
        return result;
    }
}
