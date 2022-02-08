package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.modelutil.ProductAdminDTO;

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
    Product getProductById(int id);
    long getTotalPage(int id);
    List<Product> getByPage(long currentPage, int id);
    int getCategoryId(int id);
    List<CartItem> getProductFromCart(List<Cart> cartList);
    Float getTempPrice(List<CartItem> itemList);
    boolean addProduct(Product product);
    boolean deleteProduct(int id);
    List<ProductAdminDTO> findAll();
}
