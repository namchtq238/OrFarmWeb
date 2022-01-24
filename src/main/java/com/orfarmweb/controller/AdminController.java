package com.orfarmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String Admin(){return "admin-page/admin";}

    @RequestMapping("/orderAdmin")
    public String orderAmin(){return "admin-page/order";}

    @RequestMapping("/productAdmin")
    public String productAdmin(){return "admin-page/product";}

    @RequestMapping("/addProductAdmin")
    public String addProductAdmin(){return "admin-page/add-product";}

    @RequestMapping("/hubAdmin")
    public String hubAdmin(){return "admin-page/hub";}

    @RequestMapping("/staffAdmin")
    public String staff(){return "admin-page/staff";}

    @RequestMapping("/userManagerAdmin")
    public String userManagerAdmin(){return "/admin-page/user";}
}
