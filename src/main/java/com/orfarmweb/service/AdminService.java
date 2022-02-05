package com.orfarmweb.service;

import com.orfarmweb.entity.Product;

import java.util.List;

public interface AdminService {
    Integer countOrders();
    Integer countUserByRole();
    Float getRevenue();
    long getTotalPageProduct();
    List<Product> getProductByPage(long currentPage);
}
