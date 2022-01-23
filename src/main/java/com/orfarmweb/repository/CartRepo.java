package com.orfarmweb.repository;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.CartDTO;
import com.orfarmweb.modelutil.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    @Query(value = "select * from cart " +
            "left join user on cart.user_id = user.id " +
            "right join product on cart.product_id = product.id where cart.user_id=? and cart.is_delete = 0", nativeQuery = true)
    List<PaymentInformation> listByUserAndCartByIsDelete(int id);
    @Query(value = "select cart.id , product.name , user_id , quantity , sale_price , image " +
            "from cart left join user on user.id = cart.user_id left join product on cart.product_id = product.id " +
            "where user.email=:userEmail and is_delete = 0", nativeQuery = true)
    List<CartDTO> getCartByUser(String userEmail);
    Cart getCartByUserAndProduct(User user, Product product);
    List<Cart> getCartByUser(User user);
    Integer countCartByUser(User user);
    @Query(value = "select * from cart where cart.user_id = ?",nativeQuery = true)
    List<Cart> getCartByEmail(int email);

}
