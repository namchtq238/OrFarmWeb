package com.orfarmweb.repository;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.modelutil.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
    @Query(value = "select *" +
            "from order_detail as od left join orders as o " +
            "on od.order_id = o.id left join product as p " +
            "on od.product_id = p.id order by create_at desc",nativeQuery = true)
    List<OrderDetail> getTopOrder();
}
