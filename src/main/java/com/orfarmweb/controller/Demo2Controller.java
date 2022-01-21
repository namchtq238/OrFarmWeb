package com.orfarmweb.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo2")
public class Demo2Controller {
    @RequestMapping
    public String demo(){
        return "admin-page/admin";
    }
}
