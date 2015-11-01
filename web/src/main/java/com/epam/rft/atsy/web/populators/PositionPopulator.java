package com.epam.rft.atsy.web.populators;

import com.epam.rft.atsy.service.PositionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Ikantik.
 */
@Component
public class PositionPopulator implements ModelPopulator {
    private static final String ATTRIBUTE_NAME = "positions";
    @Resource
    private PositionService positionService;

    public void populate(ModelAndView modelAndView) {
        modelAndView.addObject(ATTRIBUTE_NAME, positionService.getAllPositions());
    }
}
