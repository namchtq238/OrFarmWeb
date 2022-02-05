package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    private final long pageSize = 7;
    @Override
    public Integer countOrders() {
        return ordersRepo.countOrders();
    }
    @Override
    public Integer countUserByRole() {
        return userRepo.countUserByRole();
    }

    @Override
    public Float getRevenue() {
        return ordersRepo.getReveune();
    }

    @Override
    public long getTotalPageProduct() {
        return (productRepo.countProduct().get(0) % pageSize == 0) ? productRepo.countProduct().get(0) / pageSize
                : (productRepo.countProduct().get(0) / pageSize) + 1;
    }
    @Override
    public List<Product> getProductByPage(long currentPage) {
        return productRepo.findByPage((currentPage - 1) * pageSize, pageSize);
    }
}
