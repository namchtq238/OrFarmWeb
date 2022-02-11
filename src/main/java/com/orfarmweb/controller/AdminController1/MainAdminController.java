package com.orfarmweb.controller.AdminController1;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.ChartDTO;
import com.orfarmweb.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class MainAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;

    public MainAdminController(AdminService adminService, FormatPrice formatPrice) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
    }
    @ModelAttribute
    public void getTopOrder(Model model){
        model.addAttribute("topOder", adminService.getTopOrderDetail());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countUser", adminService.countUserByRole());
        model.addAttribute("getRevenue", adminService.getRevenue());
        model.addAttribute("countOrder", adminService.countOrders());
        model.addAttribute("getCostOfProduct",adminService.getCostOfProduct());
    }
    @GetMapping("/admin")
    public String getMainAdminPage(Model model){
        model.addAttribute("dsProduct", adminService.getProduct());
        return "admin-page/admin";
    }
    @PostMapping("/get-chart-information")
    @ResponseBody
    public ChartDTO getChartInfor(){
        return adminService.getInformationForChart();
    }
}
