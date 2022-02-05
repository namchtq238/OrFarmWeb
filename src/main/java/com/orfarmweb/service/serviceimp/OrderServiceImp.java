package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class OrderServiceImp implements OrderService {
    private final OrdersRepo ordersRepo;

    public OrderServiceImp(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    @Override
    public Orders saveNewOrder(PaymentInformation information) {
        return ordersRepo.saveAndFlush(information.getOrder());
    }

    @Override
    public boolean saveOrder(Orders orders, Float totalPrice, String note, Set<OrderDetail> orderDetailList) {
        orders.setNote(note);
        orders.setTotalPrice(totalPrice);
        orders.setOrderDetails(orderDetailList);
        orders.setStatus(Status.APPROVED);
        orders.setCreateAt(new java.util.Date());
        ordersRepo.save(orders);
        return true;
    }



    @Override
    public boolean saveNoteToOrder(String note, int id) {
        ordersRepo.saveNoteToOrder(note,id);
        return false;
    }
}
