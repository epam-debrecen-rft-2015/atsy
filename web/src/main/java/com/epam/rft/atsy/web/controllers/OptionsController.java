package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.populators.ModelPopulator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Ikantik.
 */
@Controller
@RequestMapping(path = "/secure/settings")
public class OptionsController {
    private static final String VIEW_NAME = "settings";
    @Resource(name = "positionPopulator")
    private ModelPopulator positionPopulator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        positionPopulator.populate(modelAndView);
        return modelAndView;
    }

}
