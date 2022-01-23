package com.orfarmweb.repository;

import com.orfarmweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product left join category on product.cate_id = category.id where product.cate_id = ?", nativeQuery = true)
    List<Product> findProductByCategoryId(int id);
    @Query(value = "select * from product left join category on product.cate_id = category.id " +
            "where product.cate_id = :id and product.sale_price between :a and :b LIMIT :start, :offset", nativeQuery = true)
    List<Product> listFill(float a, float b, int id, @RequestParam long start, @RequestParam long offset);
    @Query(value = "select count(*) from product left join category on product.cate_id = category.id " +
            "where product.cate_id = :idCategory and product.sale_price between :a and :b",nativeQuery = true)
    List<Long> countByCategoryIdAndFill(float a, float b, int idCategory);
    @Query(value = "select count(product.id) from product left join category on product.cate_id = category.id where product.cate_id = ?",nativeQuery = true)
    int getTotal(int id);
    @Query(value = "select count(product.id) from product left join category on product.cate_id = category.id " +
            "where product.sale_price between :start and :end and product.cate_id = :id", nativeQuery = true)
    int getTotalProductByFill(float start, float end, int id);
    @Query(value = "select * from product where id = ?", nativeQuery = true)
    Product getAllById(int id);
    @Query(value = "select * from product where is_hot= 1", nativeQuery = true)
    List<Product> getProductByHot();
    @Query(value = "select * from product where percent_discount <> 0", nativeQuery = true)
    List<Product> getSaleProduct();
    @Query(value = "select sale_price from product where product.id = ?", nativeQuery = true)
    Float getSalePrice(int id);
    @Query(value = "select COUNT(*) from product left join category on product.cate_id = category.id where product.cate_id = ?", nativeQuery = true)
    List<Long> countByCategoryId(int id);
    @Query(value = "select * from product left join category on product.cate_id = category.id where product.cate_id =:id LIMIT :start, :limit ", nativeQuery = true)
    List<Product> findByPage(@RequestParam("start") long start, @RequestParam("limit") long limit, int id);
    @Query(value = "select cate_id from product where id = ?", nativeQuery = true)
    int findCateGoryIdByProdId(int id);
}
