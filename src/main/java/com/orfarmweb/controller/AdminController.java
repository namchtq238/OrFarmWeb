package com.orfarmweb.controller;

import com.orfarmweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/admin")
    public String Admin(Model model){
        model.addAttribute("countUser", adminService.countUserByRole());
        return "admin-page/admin";
    }

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
