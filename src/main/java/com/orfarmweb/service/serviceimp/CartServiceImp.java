package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.repository.CartRepo;
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
    private final CartRepo cartRepo;
    private final UserService userService;

    public CartServiceImp(CartRepo cartRepo, UserService userService) {
        this.cartRepo = cartRepo;
        this.userService = userService;
    }

    @Override
    public boolean saveItemToCart(Product product, Integer quantity) {
        User user = userService.getCurrentUser();
        Optional<Cart> cart = Optional.ofNullable(cartRepo.getCartByUserAndProductAndIsDelete(user, product, false));
        System.err.println(user.getEmail());
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
        return cartRepo.getCartByUserAndIsDelete(userService.getCurrentUser(), false);
    }

    @Override
    public Integer countNumberOfItemInCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return 0;
        if(authentication.isAuthenticated()){
            return cartRepo.countCartByUserAndIsDelete(userService.getCurrentUser(), false);
        }
        return 0;
    }

    @Override
    public boolean deleteAllItemInCart() {
        List<Cart> cartList = getAllCartByUser();
        for (Cart cart: cartList
             ) {
            cart.setDelete(true);
            cartRepo.save(cart);
        }
        return true;
    }

    @Override
    public void saveNewQuantity(List<Cart> cartList, List<Integer> soluong) {
        for (int i = 0; i < cartList.size(); i++) {
            cartList.get(i).setQuantity(soluong.get(i));
            cartRepo.save(cartList.get(i));
        }
    }
}
