package com.epam.rft.atsy.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ikantik.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public Model getModel() {
        return new Model();
    }


    public static class Model {
        String attr1 = "ssfsdfsf";

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }
    }
}
