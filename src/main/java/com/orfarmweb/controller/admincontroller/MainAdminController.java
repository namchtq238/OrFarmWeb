package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.modelutil.ChartDTO;
import com.orfarmweb.modelutil.DateFilterDTO;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;

    public MainAdminController(AdminService adminService, FormatPrice formatPrice) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void getTopOrder(Model model) {
        model.addAttribute("topOder", adminService.getTopOrderDetail());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countUser", adminService.countCustomer());
        model.addAttribute("getRevenue", adminService.getRevenue());
        model.addAttribute("countOrder", adminService.countOrders());
        model.addAttribute("getCostOfProduct", adminService.getCostOfProduct());
    }
    @GetMapping("/admin")
    public String getViewMainAdmin(Model model) {
        model.addAttribute("dateFill", new DateFilterDTO());
        model.addAttribute("dsProduct", adminService.getListProduct());
        return "admin-page/admin";
    }
    @PostMapping("/admin/fill")
    public String getViewStatisticAdmin(Model model, @ModelAttribute DateFilterDTO dateFilterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/admin";
        model.addAttribute("totalFill", adminService.getTotalPriceByDate(dateFilterDTO.getStartFill(),dateFilterDTO.getEndFill()));
        model.addAttribute("importFill", adminService.getImportPriceByDate(dateFilterDTO.getStartFill(),dateFilterDTO.getEndFill()));
        model.addAttribute("countOrdersFill", adminService.getTotalOrdersByDate(dateFilterDTO.getStartFill(),dateFilterDTO.getEndFill()));
        model.addAttribute("countUserFill", adminService.getTotalUserId(dateFilterDTO.getStartFill(),dateFilterDTO.getEndFill()));
        model.addAttribute("dateFill", dateFilterDTO);
        model.addAttribute("dateParam", dateFilterDTO);
        adminService.findOrderDetailByDay(dateFilterDTO.getStartFill(), dateFilterDTO.getEndFill()).forEach(orderAdmin -> System.err.println(orderAdmin.toString()));
        model.addAttribute("dsProduct", adminService.findOrderDetailByDay(dateFilterDTO.getStartFill(), dateFilterDTO.getEndFill()));

        return "admin-page/admin2";
    }

    @PostMapping("/get-chart-information")
    @ResponseBody
    public ChartDTO handleChartInformation() {
        return adminService.getInformationForChart();
    }
}
