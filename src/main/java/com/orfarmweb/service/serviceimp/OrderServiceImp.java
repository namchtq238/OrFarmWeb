package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.service.OrderService;
import com.orfarmweb.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImp implements OrderService {
    private final OrdersRepo ordersRepo;
    private final UserService userService;

    public OrderServiceImp(OrdersRepo ordersRepo, UserService userService) {
        this.ordersRepo = ordersRepo;
        this.userService = userService;
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

    @Override
    public List<Orders> getOrderByCurrentUser() {
        return ordersRepo.getAllByUser(userService.getCurrentUser());
    }
}
