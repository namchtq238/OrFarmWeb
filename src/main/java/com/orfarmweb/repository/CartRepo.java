package com.orfarmweb.repository;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Cart getCartByUserAndProduct(User user, Product product);
    List<Cart> getCartByUser(User user);
    Integer countCartByUser(User user);
    void deleteCartByUser(User user);
}
