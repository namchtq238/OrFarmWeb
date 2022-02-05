package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private FormatPrice formatPrice;
    @ModelAttribute
    public void getFormatPrice(Model model){
        model.addAttribute("format", formatPrice);
    }
    @GetMapping("/admin")
    public String showViewAdmin(Model model){
        model.addAttribute("countOrder", adminService.countOrders());
        model.addAttribute("countUser", adminService.countUserByRole());
        model.addAttribute("getRevenue", adminService.getRevenue());
        return "redirect:/admin/1";
    }
    @GetMapping("/admin/{page}")
    public String showViewAdminPage(@PathVariable("page") long currentPage, Model model){
        long totalPage = adminService.getTotalPageProduct();

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        List<Product> dsProduct = adminService.getProductByPage(currentPage);
        model.addAttribute("dsProduct", dsProduct);
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
