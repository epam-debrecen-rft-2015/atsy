package com.epam.rft.atsy.web.filter;

import com.epam.rft.atsy.service.domain.UserDTO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by tothd on 2015. 11. 02..
 */
public class SecurityFilterTest {

    @InjectMocks
    SecurityFilter securityFilter;

    @Mock
    HttpServletRequest servletRequest;

    @Mock
    HttpServletResponse servletResponse;

    @Mock
    HttpSession session;

    @Mock
    UserDTO userDTO;

    @Mock
    FilterChain filterChain;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void redirectTest() throws IOException, ServletException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(servletRequest.getSession()).willReturn(session);
        given(servletRequest.getQueryString()).willReturn("");
        given(servletRequest.getContextPath()).willReturn("/atsy");
        given(servletRequest.getRequestURL()).willReturn(new StringBuffer("/atsy/secure/settings"));
        given(session.getAttribute("user")).willReturn(null);

        //when
        securityFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(servletResponse).sendRedirect(anyString());

    }

    @Test
    public void filterChainTest() throws IOException, ServletException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(servletRequest.getSession()).willReturn(session);
        given(session.getAttribute("user")).willReturn(userDTO);


        //when
        securityFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain).doFilter(servletRequest, servletResponse);

    }
}
