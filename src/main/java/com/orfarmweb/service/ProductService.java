package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;

import java.util.List;
public interface ProductService {
    List<Product> listAllByCategoryId(int id);
    List<Product> listFill(float a, float b, int id);
    Product findById(int id);
    int getTotal(int id);
    List<Product> getListProductByHot();
    List<Product> getListSaleProduct();
    String getSalePriceById(int id);
    String getDiscountPriceById(int id);
    long getTotalPage(int id);
    List<Product> getByPage(long currentPage, int id);
    int getCategoryId(int id);
    List<Product> getProductFromCart(List<Cart> cartList);
}
