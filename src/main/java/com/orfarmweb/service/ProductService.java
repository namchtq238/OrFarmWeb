package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.CartItem;

import java.util.List;
public interface ProductService {
    List<Product> listAllByCategoryId(int id);
    long getTotalPageByFill(float start, float end, int id);
    List<Product> listFillByPage(float start, float end, long currentPage, int id);
    Product findById(int id);
    int getTotalByFill(float start, float end, int id);
    int getTotal(int id);
    List<Product> getListProductByHot();
    List<Product> getListSaleProduct();
    String getSalePriceById(int id);
    String getDiscountPriceById(int id);

    long getTotalPage(int id);
    List<Product> getByPage(long currentPage, int id);
    int getCategoryId(int id);
    List<CartItem> getProductFromCart(List<Cart> cartList);
    Float getTempPrice(List<CartItem> itemList);
}
