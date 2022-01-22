package com.orfarmweb.controller;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductService productService;
    @ModelAttribute
    public void addCategoryToHeader(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
    }
    @GetMapping("/cart")
    public String getCart(Model model, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        List<CartDTO> listCart = cartService.getCartByUser(email);
        model.addAttribute("listCart", listCart);
        return "ViewCart";
    }
    @PostMapping("/product/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model,
                                   Authentication authentication, @RequestParam("quantity") Integer quantity){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepo.findUserByEmail(userDetails.getUsername());
        Product product = productService.findById(id);
        cartService.saveItemToCart(user, product, quantity);
        return "redirect:/product/{id}";
    }
}
