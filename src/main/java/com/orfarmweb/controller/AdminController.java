package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.AdminService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    private final CategoryService categoryService;
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    private final ProductService productService;
    public AdminController(AdminService adminService, CategoryService categoryService, FormatPrice formatPrice, ProductService productService) {
        this.adminService = adminService;
        this.categoryService = categoryService;
        this.formatPrice = formatPrice;
        this.productService = productService;
    }
    @ModelAttribute
    public void getTopOrder(Model model){
        model.addAttribute("topOder", adminService.getTopOrderDetail());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countUser", adminService.countUserByRole());
        model.addAttribute("getRevenue", adminService.getRevenue());
        model.addAttribute("countOrder", adminService.countOrders());
    }
    @RequestMapping("/admin")
    public String Admin(){
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
    @GetMapping("/admin/order")
    public String orderAmin(Model model){
        model.addAttribute("orderAdmin", adminService.getOrderAdmin());
        return "admin-page/order";
    }

    @GetMapping("/admin/product")
    public String productAdmin(){return "redirect:/admin/product/1";}
    @GetMapping("/admin/product/{page}")
    public String productAdminPage(@PathVariable("page") long currentPage, Model model){
        long totalPage = adminService.getTotalPageProduct();
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        List<Product> dsProduct = adminService.getProductByPage(currentPage);
        model.addAttribute("dsProduct", dsProduct);
        return "admin-page/product";
    }
    @GetMapping("/admin/product/add")
    public String addProductAdmin(Model model) {
        model.addAttribute("categoryList", categoryService.getListCategory());
        model.addAttribute("product", new Product());
        return "admin-page/add-product";
    }
    @PostMapping("/admin/product/add")
    public String handleAddProduct(Model model, @ModelAttribute @Valid Product product, BindingResult result){
        if (result.hasErrors()) return "redirect:/admin/product/add";
        productService.addProduct(product);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String editProductAdmin(@PathVariable("id") int productId, Model model){
        model.addAttribute("product", productService.getProductById(productId));
        return "admin-page/add-product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductAdmin(@PathVariable("id") int productId, Model model){
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/hub")
    public String hubAdmin(){
        return "admin-page/hub";
    }

    @GetMapping("/admin/staffManager")
    public String staff(){
        return "admin-page/staff";
    }

    @GetMapping("/admin/userManager")
    public String userManagerAdmin(){
        return "/admin-page/user";
    }

    @GetMapping("/admin/view-order")
    public String viewOrderAdmin(){return "/admin-page/view-order";}

    @GetMapping("/admin/addStaff")
    public String addStaffAdmin(){return "/admin-page/add-staff";}

    @GetMapping("/admin/category")
    public String categoryAdminPage(){return "/admin-page/category";}

    @GetMapping("/admin/category/add")
    public String handleAddCategory(){return "/admin-page/add-category";}
}
