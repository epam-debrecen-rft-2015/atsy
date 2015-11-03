package com.epam.rft.atsy.web.filter;

import com.epam.rft.atsy.service.domain.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tothd on 2015. 10. 28..
 */
@WebFilter(urlPatterns = "/secure/*")
public class SecurityFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session =((HttpServletRequest)servletRequest).getSession(false);
        UserDTO currentUser = (UserDTO)session.getAttribute("user");

        if (currentUser == null) {
            ((HttpServletResponse)servletResponse).sendRedirect("/login");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
