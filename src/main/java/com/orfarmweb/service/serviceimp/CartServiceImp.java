package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.repository.CartRepo;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService  {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserService userService;

    @Override
    public boolean saveItemToCart(Product product, Integer quantity) {
        User user = userService.getCurrentUser();
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

    @Override
    public List<Cart> getAllCartByUser() {
        return cartRepo.getCartByUser(userService.getCurrentUser());
    }

    @Override
    public Integer countNumberOfItemInCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return 0;
        if(authentication.isAuthenticated()){
            Integer number = cartRepo.countCartByUser(userService.getCurrentUser());
            return number;
        }
        return 0;
    }

    @Override
    public boolean deleteAllItemInCart() {
        cartRepo.deleteCartByUser(userService.getCurrentUser());
        return true;
    }
}
