package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepo categoryRepo;
//    @GetMapping("/category")
//    public String getCategoryInput(Model model){
//
//        return "raucusach";
//    }
    @GetMapping("/category/{id}")
    public String showViewProduct(@PathVariable("id") int id, @ModelAttribute("listCategory") Category category, Model model){
            List<Category> list = categoryRepo.findAll();
            Integer sum = productService.getTotal(category.getId());
            if(sum.equals(null)) sum = 0;
        model.addAttribute("sum", sum);
            model.addAttribute("listCategory",list);
            model.addAttribute("listProduct", productService.listAllByCategoryId(category.getId()));
        return "raucusach";
    }
    @GetMapping("/product/{id}")
    public String showViewProductDetail(@PathVariable int id, Model model){
        List<Category> list = categoryRepo.findAll();
        model.addAttribute("listCategory",list);
        model.addAttribute("product", productService.findById(id));
        return "productdetail";
    }
    @GetMapping("/testapi")
    public  @ResponseBody Product getApi(){
        Product p = new Product();
        p.setId(productService.listAllByCategoryId(1).get(0).getId());
        p.setName(productService.listAllByCategoryId(1).get(0).getName());
        return p;
    }
}
