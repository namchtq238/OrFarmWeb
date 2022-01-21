package com.orfarmweb.service;

import com.orfarmweb.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> listAllByCategoryId(int id);
    List<Product> listFill(int a, int b, int id);
    Product findById(int id);
    int getTotal(int id);
}
