package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public List<Product> listAllByCategoryId(int id) {
        return productRepo.findProductByCategoryId(id);
    }

    @Override
    public List<Product> listFill(int a, int b, int id) {
        List<Product> list = productRepo.listFill(a, b, id);
        return list;
    }

    @Override
    public Product findById(int id) {
        return productRepo.getAllById(id);
    }

    @Override
    public int getTotal(int id) {
        return productRepo.getTotal(id);
    }
}
