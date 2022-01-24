package com.orfarmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String Admin(){return "admin-page/admin";}

    @RequestMapping("/orderAdmin")
    public String orderAmin(){return "admin-page/order";}
}
