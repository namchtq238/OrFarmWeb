package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getCartByUser(String email);
    boolean saveItemToCart(User user, Product product, Integer quantity);
    List<Cart> getAllCartByUser(String email);
    Integer countNumberOfItemInCart();
    boolean deleteAllItemInCart();
}
