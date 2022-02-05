package com.orfarmweb.service.serviceimp;

import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private final OrdersRepo ordersRepo;
    private final UserRepo userRepo;

    public AdminServiceImp(OrdersRepo ordersRepo, UserRepo userRepo) {
        this.ordersRepo = ordersRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Integer countOrders() {
        return ordersRepo.countOrders();
    }
    @Override
    public Integer countUserByRole() {
        return userRepo.countUserByRole();
    }
}
