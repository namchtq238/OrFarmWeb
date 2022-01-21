package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/")
    public String returnIndex(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String getCategoryInput(Model model){
        return "index";
    }
    @ModelAttribute
    public void addCategoryToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
    }
    @ModelAttribute
    public void addListProduct(Model model){
        List<Product> hotproductList = productService.getListProductByHot();
        model.addAttribute("listHotProduct", hotproductList.subList(0, 6));
        List<Product> productList = productService.getListSaleProduct();
        model.addAttribute("listSaleProduct", productList.subList(0,8));
    }

}
