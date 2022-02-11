package com.orfarmweb.controller;

import com.orfarmweb.config.OrderDataExcelExport;
import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.constaint.Role;
import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.*;
import com.orfarmweb.modelutil.*;
import com.orfarmweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    private final CategoryService categoryService;
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    private final ProductService productService;
    private final OrderService orderService;
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory+Paths.get("/target/classes/static/image/ImageOrFarm"));
    private final UserService userService;
    public AdminController(AdminService adminService, CategoryService categoryService, FormatPrice formatPrice, ProductService productService, OrderService orderService, UserService userService) {
        this.adminService = adminService;
        this.categoryService = categoryService;
        this.formatPrice = formatPrice;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
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
    /*---------------------------Các View Admin----------------------------*/
    @GetMapping("/admin")
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
    @PostMapping("/get-chart-information")
    @ResponseBody
    public ChartDTO getChartInfor(){
        return adminService.getInformationForChart();
    }

    /*-----------------------------Các View Order---------------------------*/
    @GetMapping("/admin/order")
    public String orderAdmin(Model model){
        model.addAttribute("orderAdmin", adminService.getOrderAdmin());
        return "admin-page/order";
    }

    @GetMapping("/admin/order/{id}")
    public String viewOrderAdmin(@PathVariable int id, Model model) {
        Orders orders = orderService.findById(id);
        OrderDetail orderDetail = new OrderDetail();
        model.addAttribute("order",orders);
        return "admin-page/view-order";
    }
    @PostMapping("/admin/order/edit/{id}")
    public String handleEditStatusOrderAdmin(@PathVariable int id, @ModelAttribute Orders orders, @RequestParam Status status, Model model){
        orders.setStatus(status);
        orderService.updateStatus(id, orders);
        return "redirect:/admin/order/{id}";
    }
    @GetMapping("/admin/export")
    public String exportToExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd_HH");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String fileName = "order_"+ currentDateTime +".xlsx";
        String headerValue = "attachement; filename= "+fileName;
        response.setHeader(headerKey,headerValue);

        List<OrderAdmin> orderAdmins = adminService.getOrderAdmin();
        OrderDataExcelExport orderDataExcelExport = new OrderDataExcelExport(orderAdmins);
        System.err.println(orderAdmins.toString());
        orderDataExcelExport.export(response);
        return "admin-page/order";
    }
    /*-----------------------------Các View product---------------------------*/
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
    public String handleAddProduct(Model model, @ModelAttribute @Valid Product product, @RequestParam MultipartFile photo, BindingResult result){
        if(photo.isEmpty()||result.hasErrors()) return "redirect:/admin/product/add";
        try {
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(photo.getOriginalFilename());
            product.setImage(photo.getOriginalFilename());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    public String handleEditProductAdmin(@PathVariable("id") int productId, @ModelAttribute Product product, @RequestParam MultipartFile photo, Model model){
        if(photo.isEmpty()) return "redirect:/admin/product/edit/{id}";
        try {
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(photo.getOriginalFilename());
            product.setImage(photo.getOriginalFilename());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        productService.updateProduct(productId, product);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductAdmin(@PathVariable("id") int productId, Model model){
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }

    /*------------------------------Các View Category------------------------------*/
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

    /*------------------------------Các view HUB-----------------------------------*/
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
    /*-------------------------------------Các View Quản Lý Nhân Sự------------------------------*/
    @GetMapping("/admin/staffManager")
    public String showViewStaff(Model model){
        model.addAttribute("staffList", adminService.getUserByRole(Role.STAFF));
        return "admin-page/staff";
    }

    @GetMapping("/admin/staffManager/addStaff")
    public String addStaffAdmin(Model model){
        model.addAttribute("staff", new User());
        return "/admin-page/add-staff";
    }
    @PostMapping("/admin/staffManager/addStaff")
    public String handleAddStaff(RedirectAttributes redirectAttributes, @ModelAttribute User user){
        if(adminService.addStaff(user))
        redirectAttributes.addFlashAttribute("msg", "Thêm nhân viên thành công");
        return "redirect:/admin/staffManager";
    }
    @GetMapping("/admin/staffManager/editStaff/{id}")
    public String showEditStaff(Model model, @PathVariable("id") int id){
        model.addAttribute("staff", adminService.getUserById(id));
        return "/admin-page/add-staff";
    }
    @PostMapping("/admin/staffManager/editStaff/{id}")
    public String handleEditStaff(RedirectAttributes redirectAttributes,
                                  @ModelAttribute User user, @PathVariable("id") int id){
        if(adminService.updateStaff(id, user))
            redirectAttributes.addFlashAttribute("msg", "Thêm nhân viên thành công");
        return "redirect:/admin/staffManager";
    }
    @GetMapping("admin/staffManager/deleteStaff/{id}")
    public String handleDeleteStaff(@PathVariable("id") int id){
        adminService.deleteStaff(id);
        return "redirect:/admin/staffManager";
    }

    @GetMapping("/admin/userManager")
    public String showViewCustomer(Model model) {
         model.addAttribute("customerList", adminService.getUserByRole(Role.CUSTOMER));
        return "/admin-page/user";
    }

    @GetMapping("/admin/personal-infor")
    public String personalInfoAdmin(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("checkpassword", new PasswordDTO());
        return "/admin-page/personal-infor-admin";
    }
    @PostMapping("/admin/edit-user")
    public String handleEditUser(@ModelAttribute User user){
        userService.updateUser(userService.getCurrentUser().getId(), user);
        return "redirect:/admin/personal-information";
    }

    @PostMapping("/admin/edit-password")
    public String handleEditPassword(RedirectAttributes redirectAttributes, @ModelAttribute PasswordDTO passwordDTO) {
        String msg = null;
        if (userService.updatePassword(passwordDTO))
            msg = "Thay đổi mật khẩu thành công";
        else msg = "Thay đổi mật khẩu thất bại";
        redirectAttributes.addAttribute("msg", msg);
        return "redirect:/admin/personal-infor";
    }
}
