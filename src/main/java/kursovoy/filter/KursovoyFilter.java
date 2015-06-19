package kursovoy.filter;

import kursovoy.jdbc.AbstractDao;
import kursovoy.jdbc.UserDao;
import kursovoy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public final class KursovoyFilter implements Filter {
    private FilterConfig filterConfig = null;
    private final static String AUTH_KEY = "AUTH_KEY";
    private final static String INFO_KEY = "INFO_KEY";
    @Autowired
    private UserDao userDao;

    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
    }

    // known cookie
    // fail count
    // IP history
    // Trust IP list
    // last login time
    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (filterConfig == null)
            return;
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String requestedURI = httpRequest.getRequestURI();

            boolean toRedirect = this.calcualate(requestedURI, isAuthorized(httpRequest));
            if (toRedirect && !"POST".equals(httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpRequest.getRequestDispatcher("/login").forward(httpRequest, httpResponse);
            } else {
                fillUIHeader(httpRequest);
                chain.doFilter(request, response);
            }
        } catch (ClassCastException cce) {
            System.out.println("Can't cast request/response to http");
        }
    }

    private boolean calcualate(String requestedURI, boolean isAuthrized) {
        boolean toRedirect = !isAuthrized;
        if (toRedirect && requestedURI != null) {
            if (requestedURI.contains("/css/") || requestedURI.contains("/js/") || requestedURI.contains("/img/") ||
                    requestedURI.contains("/pass-auth") || requestedURI.contains("/get-capcha") || requestedURI.contains("/sender-auth") || requestedURI.contains("/login") || requestedURI.contains("/selfRegistration") || requestedURI.contains(".jsp"))
                toRedirect = false;
        } else {
            toRedirect = false;
        }
        return toRedirect;
    }

    protected User getCurrentUser(HttpServletRequest request) {
        User result = null;
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cook : cookie) {
                if (AUTH_KEY.equals(cook.getName())) {
                    String value = cook.getValue();
                    List<User> userLIst = userDao.getRecordById(value);
                    if (!CollectionUtils.isEmpty(userLIst)) {
                        result = userLIst.get(0);
                        break;
                    }
                }
            }
        }
        return result;
    }

    protected void fillUIHeader(HttpServletRequest request) {
        User u = this.getCurrentUser(request);
        if (u != null) {
            request.setAttribute("HEADER_USER_NAME", u.getFirstName() + " " + u.getLastName());
            request.setAttribute("CURRENT_USER_ID", u.getId());
        }
    }

    protected boolean isAuthorized(HttpServletRequest request) {
        boolean result = false;
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cook : cookie) {
                if (AUTH_KEY.equals(cook.getName())) {
                    String value = cook.getValue();
                    List<User> userLIst = userDao.getRecordById(value);
                    if (!CollectionUtils.isEmpty(userLIst)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
}