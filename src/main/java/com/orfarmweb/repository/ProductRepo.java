package com.orfarmweb.repository;

import com.orfarmweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product left join category on product.cate_id = category.id where category.id = ?", nativeQuery = true)
    List<Product> findProductByCategoryId(int id);
    @Query(value = "select * from product left join category on product.cate_id = category.id " +
            "where product.sale_price between ?1 and ?2 and category.id = ?3", nativeQuery = true)
    List<Product> listFill(float a, float b, int id);
    @Query(value = "select count(product.id) from product left join category on product.cate_id = category.id where category.id = ?",nativeQuery = true)
    int getTotal(int id);
    @Query(value = "select * from product where id = ?", nativeQuery = true)
    Product getAllById(int id);
    @Query(value = "select * from product where is_hot= 1", nativeQuery = true)
    List<Product> getProductByHot();
    @Query(value = "select * from product where percent_discount <> 0", nativeQuery = true)
    List<Product> getSaleProduct();
    @Query(value = "select sale_price from product where product.id = ?", nativeQuery = true)
    Float getSalePrice(int id);
}
