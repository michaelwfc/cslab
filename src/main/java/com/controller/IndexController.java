package com.controller;

import com.framework.GetMapping;
import com.framework.ModelAndView;

public class IndexController {

    @GetMapping("/hello")
    public ModelAndView hello(String name) {
        if (name == null) {
            name = "World";
        }
        return new ModelAndView("/hello.html", "name", name);
    }

}
