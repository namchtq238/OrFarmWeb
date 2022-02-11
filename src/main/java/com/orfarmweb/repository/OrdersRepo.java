package com.orfarmweb.repository;

import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    @Query(value = "select count(*) from Orders", nativeQuery = true)
    Integer countOrders();
    @Query(value = "select sum(total_price) from Orders where status = 3", nativeQuery = true)
    Float getReveune();
    List<Orders> getAllByUser(User user);
    @Query(value = "select * from orders left join user on orders.user_id = user.id", nativeQuery = true)
    List<Orders> getOrderUser();
    @Query(value = "select * from orders left join user on orders.user_id = user.id where orders.create_at between :start and :end", nativeQuery = true)
    List<Orders> getOrderUserFillter(Date start, Date end);
}
