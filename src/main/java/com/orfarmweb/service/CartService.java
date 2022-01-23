package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;

import java.util.List;

public interface CartService {
    boolean saveItemToCart(Product product, Integer quantity);
    List<Cart> getAllCartByUser();
    Integer countNumberOfItemInCart();
    boolean deleteAllItemInCart();
}
