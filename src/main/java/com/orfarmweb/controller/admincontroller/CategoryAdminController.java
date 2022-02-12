package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
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
@Controller
public class CategoryAdminController {
    private final CategoryService categoryService;
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory+Paths.get("/target/classes/static/image/ImageOrFarm"));
    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
    public String handleAddCategory(Model model, @ModelAttribute @Valid Category category, @RequestParam MultipartFile photo, BindingResult result) {
        if(photo.isEmpty() || result.hasErrors()) return "redirect:/admin/category/add";
        try {
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            category.setImage(photo.getOriginalFilename());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    public String handleEditCategory(@PathVariable("id") int id, @ModelAttribute Category category, @RequestParam MultipartFile photo){
        if(!photo.isEmpty()) {
            try {
                InputStream inputStream = photo.getInputStream();
                Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                category.setImage(photo.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        categoryService.updateCategory(id, category);
        return "redirect:/admin/category";
    }

}
