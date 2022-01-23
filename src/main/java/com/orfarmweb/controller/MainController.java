package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import com.orfarmweb.service.UserService;
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
    private CartService cartService;

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
    @GetMapping("/createAccount")
    public String getCreateAccountPage(){
        return "createAccount";
    }
    @ModelAttribute
    public void addCategoryToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
    }
    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader(Model model){
        return cartService.countNumberOfItemInCart();
    }
    @ModelAttribute
    public void addListProduct(Model model){
        List<Product> hotproductList = productService.getListProductByHot();
        int numberOfHotProduct = 6;
        Collections.shuffle(hotproductList);
        if(hotproductList.size()<7) model.addAttribute("listHotProduct", hotproductList);
        else model.addAttribute("listHotProduct", hotproductList.subList(0, numberOfHotProduct));
        List<Product> productList = productService.getListSaleProduct();
        int numberOfSaleProduct = 8;
        HashMap<Integer,String> getListSale = new HashMap<>();
        HashMap<Integer,String> getListDiscount = new HashMap<>();
        Collections.shuffle(productList);
        if(productList.size()<9) model.addAttribute("listSaleProduct",productList);
        else{
        for (Product p : productList.subList(0, numberOfSaleProduct)) {
            getListSale.put(p.getId(), productService.getSalePriceById(p.getId()));
            getListDiscount.put(p.getId(),productService.getDiscountPriceById(p.getId()));
        }
        model.addAttribute("listSaleProduct", productList.subList(0, numberOfSaleProduct));
        model.addAttribute("getListSale",getListSale);
        model.addAttribute("getListDiscount",getListDiscount);
        }
//        List<Product> products = Stream.concat(hotproductList.stream(), productList.stream())
//                .collect(Collectors.toList());
//        Collections.shuffle(products);
//        model.addAttribute("products", products.subList(0, 8));
    }
}
