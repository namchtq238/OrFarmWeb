package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.User;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import com.orfarmweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
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
    public String getCreateAccountPage(Model model){
        model.addAttribute("user", new User());
        return "createAccount";
    }
    @PostMapping("/processRegister")
    public String showCreateAccountInformation(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model){
        if(userService.checkExist(user.getEmail()))
            bindingResult.rejectValue("email","invalid","Email đã tồn tại");
        if (bindingResult.hasErrors()) {
            return "/createAccount";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
    @ModelAttribute
    public void addCategoryToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
    }
}
