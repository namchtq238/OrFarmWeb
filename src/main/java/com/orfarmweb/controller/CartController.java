package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.*;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CartController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private FormatPrice format;
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
    public String getCart(Model model) {
        List<Cart> listCart = cartService.getAllCartByUser();
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPrice(listProductInCart);
        Float ship = 20000f;
        if(tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        model.addAttribute("tempPrice", format.formatPrice(tempPrice));
        model.addAttribute("ship", format.formatPrice(ship));
        model.addAttribute("totalPrice", format.formatPrice(totalPrice));
        model.addAttribute("format", format);
        model.addAttribute("listProductInCart", listProductInCart);
        CartDTO cartDTO = new CartDTO();
        model.addAttribute("listQuantity", cartDTO);
        return "ViewCart";
    }

    @PostMapping("/product/{id}")
    public String addProductToCart(@PathVariable("id") int id,
                                   @RequestParam("quantity") Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return "redirect:/login";
        Product product = productService.findById(id);
        cartService.saveItemToCart(product, quantity);
        return "redirect:/product/{id}";
    }
    @GetMapping("/cart/delete")
    public String deleteAllProduct(){
        cartService.deleteAllItemInCart();
        return "redirect:/cart";
    }
    @PostMapping("/cart/save")
    public String saveNewCart(Model model, @RequestParam("soluong") String[] list){
        List<Integer> soluong = new ArrayList<>();
        for(int i=0; i<list.length; i++){
            soluong.add(Integer.parseInt(list[i]));
        }
        List<Cart> listCart = cartService.getAllCartByUser();
        cartService.saveNewQuantity(listCart, soluong);
        return "redirect:/cart";
    }
    @GetMapping("/payment")
    public String payment(Model model){
        User user = userService.getCurrentUser();
        List<Cart> listCart = cartService.getAllCartByUser();
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPrice(listProductInCart);
        Float ship = 20000f;
        if(tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        model.addAttribute("tempPrice", format.formatPrice(tempPrice));
        model.addAttribute("ship", format.formatPrice(ship));
        model.addAttribute("totalPrice", format.formatPrice(totalPrice));
        model.addAttribute("format", format);

        model.addAttribute("productInCart", listProductInCart);
        model.addAttribute("userInformation", user);
        model.addAttribute("paymentInformation", new PaymentInformation());
        return "payment";
    }
    @PostMapping("/paymentProcess")
    public String paymentProcess(@ModelAttribute PaymentInformation paymentInformation){
        List<Cart> listCart = cartService.getAllCartByUser();
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPrice(listProductInCart);
        Float ship = 20000f;
        if(tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        String note = paymentInformation.getOrder().getNote();
        Orders orders = orderService.saveNewOrder(paymentInformation);
        Set<OrderDetail> orderDetailList = new HashSet<>();
        for (CartItem cart: listProductInCart) {
            OrderDetail orderDetail = orderDetailService.saveOrderDetail(
                    productService.findById(cart.getProductId()), orders,
                    cart.getTotalPrice(), cart.getQuantity());
            orderDetailList.add(orderDetail);
        }
        orderService.saveOrder(orders, totalPrice, paymentInformation.getOrder().getNote(), orderDetailList);
        cartService.deleteAllItemInCart();
        return "success-order";
    }
}
