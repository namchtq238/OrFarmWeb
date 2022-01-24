package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
public class ShowViewController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @ModelAttribute
    public void addCategoryToHeader(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
    }
    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader(Model model) {
        return cartService.countNumberOfItemInCart();
    }

    @GetMapping("/blog")
    public String showViewBlog(){
        return "blog";
    }
    @GetMapping("/information")
    public String showViewInfo(){
        return "information";
    }
    @GetMapping("/license")
    public String showViewLicense(){
        return "licence";
    }
    @GetMapping("/policy")
    public String showViewPolicy(){
        return "policy";
    }
    @GetMapping("/condition")
    public String showViewCondition(){
        return "condition";
    }
}
