package com.orfarmweb.controller.AdminController1;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ProductAdminController {
    private final CategoryService categoryService;
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    private final ProductService productService;
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory+Paths.get("/target/classes/static/image/ImageOrFarm"));
    public ProductAdminController(AdminService adminService, CategoryService categoryService,
                                  FormatPrice formatPrice, ProductService productService) {
        this.adminService = adminService;
        this.categoryService = categoryService;
        this.formatPrice = formatPrice;
        this.productService = productService;
    }
    @ModelAttribute
    public void getTopOrder(Model model){
        model.addAttribute("format", formatPrice);
    }
    @GetMapping("/admin/product")
    public String productAdmin(Model model){
        model.addAttribute("dsProduct", adminService.getProduct());
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
}
