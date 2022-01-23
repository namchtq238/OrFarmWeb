package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.OrderDetailRepo;
import com.orfarmweb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Override
    public OrderDetail saveOrderDetail(
            Product product, Orders orders, Float price, Integer quantity) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrders(orders);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(price);
        return orderDetailRepo.saveAndFlush(orderDetail);
    }
}
