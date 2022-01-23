package com.orfarmweb.service;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.PaymentInformation;

import java.util.Set;

public interface OrderService {
    Orders saveNewOrder(PaymentInformation information);
    boolean saveOrder(Orders orders, Float totalPrice, Set<OrderDetail> orderDetailList);
}
