package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.User;
import com.orfarmweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final OrderService orderService;
    private final FormatPrice formatPrice;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserController(UserService userService, CategoryService categoryService, CartService cartService, OrderService orderService, FormatPrice formatPrice) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void addToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
        model.addAttribute("format", formatPrice);
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/createAccount")
    public String getCreateAccountPage(Model model){
        model.addAttribute("user", new User());
        return "createAccount";
    }
    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader() {
        return cartService.countNumberOfItemInCart();
    }

    @PostMapping("/processRegister")
    public String showCreateAccountInformation(@ModelAttribute @Valid User user, BindingResult bindingResult){
        if(userService.checkExist(user.getEmail()))
            bindingResult.rejectValue("email","invalid","Email đã tồn tại");
        if (bindingResult.hasErrors()) {
            return "/createAccount";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
    @GetMapping("/user/personal-information")
    public String showUserInformation(Model model){
        User user = userService.getCurrentUser();
        User user1 = userService.findById(user.getId());
        model.addAttribute("userInfo", user1);
        return "personal-infor";
    }
    @PostMapping("/user/edit-user")
    public String handleEditUser(@ModelAttribute User user){
        userService.updateUser(userService.getCurrentUser().getId(), user);
        return "redirect:/user/personal-information";
    }
    @PostMapping("/user/edit-password")
    public String handleEditPassWord(Model model, @ModelAttribute User user, String password){
        User user1 = userService.findById(user.getId());
        if(passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(password))) userService.saveUser(user);
        return "redirect:/user/personal-information";
    }
    @GetMapping("/user/order-history")
    public String getOrderHistoryPage(Model model){
        model.addAttribute("listOrder", orderService.getOrderByCurrentUser());
        return "order-history";
    }
}
