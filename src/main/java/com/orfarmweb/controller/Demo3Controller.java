package com.orfarmweb.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo3")
public class Demo3Controller {
    @RequestMapping
    public String demo(){
        return "resetPass";
    }
}
