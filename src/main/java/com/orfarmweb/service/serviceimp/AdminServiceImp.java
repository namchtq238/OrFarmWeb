package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements AdminService {

    private final OrdersRepo ordersRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    public AdminServiceImp(OrdersRepo ordersRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.ordersRepo = ordersRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }
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
