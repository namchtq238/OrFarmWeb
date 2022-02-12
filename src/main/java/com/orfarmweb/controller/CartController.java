package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.OrderService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    private final CategoryService categoryService;
    private final CartService cartService;
    private final ProductService productService;
    private final FormatPrice format;
    private final OrderService orderService;

    public CartController(CategoryService categoryService, CartService cartService, ProductService productService, FormatPrice format, OrderService orderService) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.productService = productService;
        this.format = format;
        this.orderService = orderService;
    }

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

    @GetMapping("/cart/delete")
    public String deleteAllProduct(){
        cartService.deleteAllItemInCart();
        return "redirect:/cart";
    }
    @GetMapping("/cart/{id}")
    public String deleteProduct(@PathVariable("id") int productId){
        cartService.deleteAnItemInCart(productId);
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
    @GetMapping("/user/repurchase/{id}")
    public String getViewRepurchase(Model model, @PathVariable int id){
        cartService.saveItemToCartByOrder(orderService.findById(id));
        return "redirect:/cart";
    }

}
