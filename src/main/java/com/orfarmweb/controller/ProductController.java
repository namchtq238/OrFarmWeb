package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @ModelAttribute
    public void addCategoryToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
    }
    @GetMapping("/category/{id}")
    public String showViewProduct(@PathVariable("id") int id, Model model){
            Integer sum = productService.getTotal(id);
            if(sum.equals(null)) sum = 0;
            model.addAttribute("sum", sum);
            model.addAttribute("listProduct", productService.listAllByCategoryId(id));
        return "raucusach";
    }
    @GetMapping("/product/{id}")
    public String showViewProductDetail(@PathVariable int id, Model model){
        model.addAttribute("productDetail", productService.findById(id));
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
