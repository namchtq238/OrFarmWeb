package com.orfarmweb.controller.AdminController1;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.modelutil.ProductAdminDTO;
import com.orfarmweb.modelutil.SearchDTO;
import com.orfarmweb.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
@Controller

public class HubAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    public HubAdminController(AdminService adminService, FormatPrice formatPrice) {
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
    @GetMapping("/admin/hub")
    public String showViewHub(){
        return "redirect:/admin/hub/1";
    }
    @GetMapping("/admin/hub/{page}")
    public String showViewHubPage(@PathVariable("page") long currentPage, Model model){
        long totalPage = adminService.getTotalPageProduct();
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        List<ProductAdminDTO> dsProduct = adminService.getHubByPage(currentPage);
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("dsProduct", dsProduct);
        return "admin-page/hub";
    }
    //    @PostMapping("/test")
//    @ResponseBody
//    public List<ProductAdminDTO> hubAdmin(){
//        return productService.findAll();
//    }
    @GetMapping("/admin/hub/fillByName")
    public String showViewSearchByName(){
        return "redirect:/admin/hub/fillByName/1";
    }
    @GetMapping("/admin/hub/fillByName/{page}")
    public String handleViewSearchByName(@PathVariable("page") long currentPage, Model model, @ModelAttribute SearchDTO searchDTO){
        long totalPage = adminService.getTotalPageHubByKeyWord(searchDTO.getName());
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        List<ProductAdminDTO> dsProduct = adminService.searchHubByNameAndPage(searchDTO.getName(),currentPage);
        model.addAttribute("dsProduct",dsProduct);
        model.addAttribute("currentFilter", searchDTO);
        return "admin-page/hub-name";
    }
}
