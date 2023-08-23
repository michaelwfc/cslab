package com.controller;

import com.annotations.GetMapping;
import com.framework.ModelAndView;

public class IndexController {

    // 使用注解记录 path
    @GetMapping("/hello")
    public ModelAndView hello(String name) {
        if (name == null) {
            name = "World";
        }
        return new ModelAndView("/hello.html", "name", name);
    }

}
