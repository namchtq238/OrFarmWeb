package com.orfarmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping
    public String init(){
        return "index";
    }

    @RequestMapping("/createAccount")
    public String demo() {return "createAccount";}
}
