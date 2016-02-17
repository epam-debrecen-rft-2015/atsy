package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Locale;

/**
 * Created by Ikantik.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static final String VIEW_NAME = "login";
    private static final String DEFAULT_REDIRECT = "/secure/welcome";
    private static final String REDIRECT_PARAM = "redirect";
    @Resource
    private UserService userService;

    @Resource
    private EncryptionUtil encryptionUtil;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pageLoad(HttpServletRequest request) {
        ModelAndView model = new ModelAndView(VIEW_NAME);
        request.getSession().invalidate();
        return model;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleLogin(@Valid @ModelAttribute UserDTO userDTO, Locale userLocale, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView model = new ModelAndView(VIEW_NAME);

        if (bindingResult.hasErrors()) {
            model.addObject("loginErrorKey", "login.backend.validation");
        } else {
            try {
                userDTO.setPassword(encryptionUtil.hash(userDTO.getPassword()));

                UserDTO foundUser = userService.login(userDTO);

                request.getSession().setAttribute("user", foundUser);

                model.setViewName("redirect:" + resolveRedirect(request));

            } catch (UserNotFoundException e) {
                model.addObject("loginErrorKey", "login.backend.auth.failed");
            } catch (BackendException backendException) {
                model.addObject("loginErrorKey", "login.backend.error");
            }
        }
        return model;
    }


    private String resolveRedirect(HttpServletRequest request) {
        String redirectParam = request.getParameter(REDIRECT_PARAM);
        String redirectTo = DEFAULT_REDIRECT;
        if (StringUtils.isNotBlank(redirectParam)) {
            redirectTo = new String(Base64.getDecoder().decode(redirectParam));
        }
        return redirectTo;
    }

}
