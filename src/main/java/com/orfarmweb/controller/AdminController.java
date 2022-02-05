package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final FormatPrice formatPrice;
    public AdminController(AdminService adminService, FormatPrice formatPrice) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
    }
    @ModelAttribute
    public void getTopOrder(Model model){
        model.addAttribute("topOder", adminService.getTopOrderDetail());
        model.addAttribute("format", formatPrice);
    }
    @RequestMapping("/admin")
    public String Admin(Model model){
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
