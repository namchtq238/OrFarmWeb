package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FormatPrice formatPrice;
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
    @GetMapping("/")
    public String getIndex(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String getHomePage(){
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
        int numberOfHotProduct = 6;
        Collections.shuffle(hotproductList);
        model.addAttribute("listHotProduct", hotproductList.subList(0, numberOfHotProduct));
        List<Product> productList = productService.getListSaleProduct();
        int numberOfSaleProduct = 8;
        Collections.shuffle(productList);
        HashMap<Integer,String> getListSale = new HashMap<>();
        HashMap<Integer,String> getListDiscount = new HashMap<>();
        for (Product p :productList.subList(0, numberOfSaleProduct)) {
            getListSale.put(p.getId(), formatPrice.formatPrice(p.getSalePrice()));
            getListDiscount.put(p.getId(),formatPrice.formatPrice(p.getSalePrice()*(1-p.getPercentDiscount()/100)));
        }
        model.addAttribute("getListSale",getListSale);
        model.addAttribute("getListDiscount",getListDiscount);
        model.addAttribute("listSaleProduct", productList.subList(0, numberOfSaleProduct));
    }
}
