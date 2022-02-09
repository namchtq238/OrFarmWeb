package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.ChartDTO;
import com.orfarmweb.modelutil.ProductAdminDTO;
import com.orfarmweb.modelutil.SearchDTO;
import com.orfarmweb.service.AdminService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
        model.addAttribute("getCostOfProduct",adminService.getCostOfProduct());
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
        model.addAttribute("categoryList", categoryService.getListCategory());
        model.addAttribute("product", productService.getProductById(productId));
        return "admin-page/add-product";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String handleEditProductAdmin(@PathVariable("id") int productId, @ModelAttribute Product product, Model model){
        productService.updateProduct(productId, product);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductAdmin(@PathVariable("id") int productId, Model model){
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
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
    @PostMapping("/test")
    @ResponseBody
    public List<ProductAdminDTO> hubAdmin(){
        return productService.findAll();
    }
    @PostMapping("/get-chart-information")
    @ResponseBody
    public ChartDTO getChartInfor(){
        return adminService.getInformationForChart();
    }
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
    @GetMapping("/admin/staffManager")
    public String staff(){
        return "admin-page/staff";
    }

    @GetMapping("/admin/userManager")
    public String userManagerAdmin() {
        return "/admin-page/user";
    }

    @GetMapping("/admin/view-order")
    public String viewOrderAdmin() {
        return "/admin-page/view-order";
    }

    @GetMapping("/admin/addStaff")
    public String addStaffAdmin(){
        return "/admin-page/add-staff";
    }

    @GetMapping("/admin/category")
    public String categoryAdminPage(Model model){
        model.addAttribute("categoryList", categoryService.getListCategory());
        return "/admin-page/category";
    }

    @GetMapping("/admin/category/add")
    public String showAddCategory(Model model) {
        model.addAttribute("category", new Category());
        return "/admin-page/add-category";
    }
    @PostMapping("/admin/category/add")
    public String handleAddCategory(Model model, @ModelAttribute @Valid Category category, BindingResult result) {
        if(result.hasErrors()) return "redirect:/admin/category/add";
        categoryService.addCategory(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/delete/{id}")
    public String handleDeleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/edit/{id}")
    public String showEditCategory(@PathVariable("id") int id, Model model){
        if(categoryService.findById(id).isPresent()){
            model.addAttribute("category", categoryService.findById(id).get());
            return "/admin-page/add-category";
        }
        return "redirect:/admin/category";
    }
    @PostMapping("/admin/category/edit/{id}")
    public String handleEditCategory(@PathVariable("id") int id, @ModelAttribute Category category, Model model){
        categoryService.updateCategory(id, category);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/personal-infor")
    public String personalInfoAdmin() {
        return "/admin-page/personal-infor-admin";
    }
}
