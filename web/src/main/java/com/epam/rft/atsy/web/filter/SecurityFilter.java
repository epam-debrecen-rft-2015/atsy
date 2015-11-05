package com.epam.rft.atsy.web.filter;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tothd on 2015. 10. 28..
 */
@WebFilter(urlPatterns = "/secure/*")
public class SecurityFilter implements Filter {
    private static final String LOGIN_PATH = "/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpSession session = request.getSession();
        Object currentUser = session.getAttribute("user");
        if (currentUser == null) {
            ((HttpServletResponse) servletResponse).sendRedirect(buildRedirect(request));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private String buildRedirect(HttpServletRequest request) {
        StringBuffer redirectBuffer = new StringBuffer(request.getContextPath()).append(LOGIN_PATH);
        String redirect = buildRedirectParam(request);
        return redirectBuffer.append(redirect).toString();
    }

    private String buildRedirectParam(HttpServletRequest request) {
        String encoded = "";
        StringBuffer buffer = new StringBuffer();
        if (request.getRequestURL().length() > 0) {
            buffer.append(request.getRequestURL());
            if (StringUtils.isNotBlank(request.getQueryString())) {
                buffer.append("?").append(request.getQueryString());
            }
            encoded = "?redirect=" + Base64.getEncoder().encodeToString(buffer.toString().getBytes());
        }
        return encoded;
    }

    @Override
    public void destroy() {

    }
}
