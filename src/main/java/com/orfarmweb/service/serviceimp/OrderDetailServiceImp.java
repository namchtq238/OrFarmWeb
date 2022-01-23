package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.repository.OrderDetailRepo;
import com.orfarmweb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Override
    public boolean saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepo.save(orderDetail);
        return true;
    }
}
