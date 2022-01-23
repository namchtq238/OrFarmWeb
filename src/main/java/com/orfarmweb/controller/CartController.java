package com.orfarmweb.controller;

<<<<<<< Updated upstream
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/cart")
    public String getCart(){
        return "ViewCart";
    }
=======
import com.orfarmweb.entity.*;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @ModelAttribute
    public void addCategoryToHeader(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
    }

    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader(Model model) {
        return cartService.countNumberOfItemInCart();
    }

    @GetMapping("/cart")
    public String getCart(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        List<Cart> listCart = cartService.getCartByEmail(userRepo.findUserByEmail(email).getId());
        List<Product> listProductInCart = productService.getProductFromCart(listCart);
        model.addAttribute("listCart", listCart);
        model.addAttribute("listProductInCart", listProductInCart);

        return "ViewCart";
    }

    @PostMapping("/product/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model,
                                   Authentication authentication, @RequestParam("quantity") Integer quantity) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepo.findUserByEmail(userDetails.getUsername());
        Product product = productService.findById(id);
        cartService.saveItemToCart(user, product, quantity);
        return "redirect:/product/{id}";
    }
    @GetMapping("/payment")
    public String payment(Model model){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userRepo.findUserByEmail(email);
            model.addAttribute("userInformation", user);
            model.addAttribute("paymentInformation", new PaymentInformation());
        return "payment";
    }
    @PostMapping("/paymentProcess")
    public String paymentProcess(@ModelAttribute PaymentInformation paymentInformation){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepo.findUserByEmail(email);
        List<Cart> listCart = cartService.getCartByEmail(user.getId());
        System.err.println(listCart);
        OrderDetail orderDetail = new OrderDetail();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Cart cart: listCart) {
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setPrice(cart.getProduct().getSalePrice());
            orderDetailList.add(orderDetail);
        }
        System.out.println(orderDetailList);
        System.out.println(paymentInformation.toString());
        return "index";
    }
>>>>>>> Stashed changes
}
