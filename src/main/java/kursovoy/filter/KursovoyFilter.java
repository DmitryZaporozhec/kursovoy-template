package kursovoy.filter;

import javax.servlet.*;
import java.io.IOException;

public final class KursovoyFilter implements Filter {
    private FilterConfig filterConfig = null;
    final static String MY_COOKIE_NAME = "KursovoiCookie";

    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        if (filterConfig == null)
//            return;
//        try {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//            String requestedURI = httpRequest.getRequestURI();
//
//            boolean toRedirect = this.calcualate(requestedURI, isAuthorized(httpRequest));
//            if (toRedirect && !"POST".equals(httpRequest.getMethod())) {
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpRequest.getRequestDispatcher("/login").forward(httpRequest, httpResponse);
//            } else {
//                fillUIHeader(httpRequest);
//                chain.doFilter(request, response);
//            }
//        } catch (ClassCastException cce) {
//            System.out.println("Can't cast request/response to http");
//        }
        chain.doFilter(request, response);

    }

    private boolean calcualate(String requestedURI, boolean isAuthrized) {
        boolean toRedirect = !isAuthrized;
        if (toRedirect && requestedURI != null) {
            if (requestedURI.contains("/css/") || requestedURI.contains("/js/") || requestedURI.contains("/img/")
                    || requestedURI.contains("/login") || requestedURI.contains("/selfRegistration") || requestedURI.contains(".jsp"))
                toRedirect = false;
        } else {
            toRedirect = false;
        }
        return toRedirect;
    }
}