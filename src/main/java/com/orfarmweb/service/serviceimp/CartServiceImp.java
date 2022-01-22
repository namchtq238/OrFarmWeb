package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.repository.CartRepo;
import com.orfarmweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService  {
    @Autowired
    private CartRepo cartRepo;

    @Override
    public List<CartDTO> getCartByUser(String email) {
        return cartRepo.getCartByUser(email);
    }

    @Override
    public boolean saveItemToCart(User user, Product product, Integer quantity) {
        Optional<Cart> cart = Optional.ofNullable(cartRepo.getCartByUserAndProduct(user, product));
        if(!cart.isPresent()){
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setQuantity(quantity);
            cartRepo.save(newCart);
            return true;
        }
        else {
            Cart currentCart = cart.get();
            int newQuantity = currentCart.getQuantity() + quantity;
            currentCart.setQuantity(newQuantity);
            cartRepo.save(currentCart);
            return true;
        }
    }
}
