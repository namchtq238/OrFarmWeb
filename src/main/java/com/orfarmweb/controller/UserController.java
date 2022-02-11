package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PasswordDTO;
import com.orfarmweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final OrderService orderService;
    private final FormatPrice formatPrice;
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
    public String showCreateAccountInformation(@ModelAttribute @Valid User user,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes){
        if(userService.checkExist(user.getEmail()))
            bindingResult.rejectValue("email","invalid","Email đã tồn tại");
        if (bindingResult.hasErrors()) {
            return "/createAccount";
        }
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("msg", "Tạo tài khoản thành công");
        return "redirect:/login";
    }
    @GetMapping("/user/personal-information")
    public String showUserInformation(Model model){
        User user = userService.getCurrentUser();
        model.addAttribute("userInfo", user);
        model.addAttribute("checkpassword", new PasswordDTO());
        return "personal-infor";
    }
    @PostMapping("/user/edit-user")
    public String handleEditUser(@ModelAttribute User user){

        userService.updateUser(userService.getCurrentUser().getId(), user);
        return "redirect:/user/personal-information";
    }
    @PostMapping("/user/edit-password")
    public String handleEditPassWord(RedirectAttributes redirectAttributes, @ModelAttribute PasswordDTO passwordDTO){
        String msg = null;
        if (userService.updatePassword(passwordDTO))
            msg = "Thay đổi mật khẩu thành công";
        else msg = "Thay đổi mật khẩu thất bại";
        redirectAttributes.addAttribute("msg", msg);
        return "redirect:/user/personal-information";
    }
    @GetMapping("/user/order-history")
    public String getOrderHistoryPage(Model model){
        model.addAttribute("listOrder", orderService.getOrderByCurrentUser());
        return "order-history";
    }
}
