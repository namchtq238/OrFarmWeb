package com.orfarmweb.service;

import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.PaymentInformation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    Orders saveNewOrder(PaymentInformation information);
    boolean saveOrder(Orders orders, Float totalPrice, String note,Set<OrderDetail> orderDetailList);
    List<Orders> getOrderByCurrentUser();
    Orders findById(int id);
    void updateStatus(int id, Orders orders);
}
