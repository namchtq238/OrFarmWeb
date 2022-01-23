package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrdersRepo ordersRepo;

    @Override
    public Orders saveNewOrder(PaymentInformation information) {
        return ordersRepo.saveAndFlush(information.getOrder());
    }

    @Override
    public boolean saveOrder(Orders orders, Float totalPrice, Set<OrderDetail> orderDetailList) {
        orders.setTotalPrice(totalPrice);
        orders.setOrderDetails(orderDetailList);
        ordersRepo.save(orders);
        return true;
    }
}
