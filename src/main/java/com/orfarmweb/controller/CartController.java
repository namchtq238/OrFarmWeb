package com.orfarmweb.controller;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import com.orfarmweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

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
        List<Cart> listCart = cartService.getAllCartByUser();
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPrice(listProductInCart);
        Float ship = 20000f;
        if(tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        model.addAttribute("tempPrice", tempPrice);
        model.addAttribute("ship", ship);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("listProductInCart", listProductInCart);
        CartDTO cartDTO = new CartDTO();
        model.addAttribute("listQuantity", cartDTO);
        return "ViewCart";
    }

    @PostMapping("/product/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model,
                                   @RequestParam("quantity") Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return "redirect:/login";
        Product product = productService.findById(id);
        cartService.saveItemToCart(product, quantity);
        return "redirect:/product/{id}";
    }
    @GetMapping("/payment")
    public String payment(){
        return "payment";
    }
    @GetMapping("/cart/delete")
    public String deleteAllProduct(Model model){
        cartService.deleteAllItemInCart();
        return "redirect:/cart";
    }
    @PostMapping("/cart/save")
    public String saveNewCart(Model model, @RequestParam("soluong") String[] list){
        for(int i=0; i<list.length; i++){
            System.out.println(Integer.parseInt(list[i]));
        }
        return "redirect:/cart";
    }
}
