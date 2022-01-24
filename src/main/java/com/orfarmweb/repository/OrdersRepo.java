package com.orfarmweb.repository;

import com.orfarmweb.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    @Query(value = "update orders set note = ?1 where id = ?2",nativeQuery = true)
    boolean saveNoteToOrder(String note, int id);
}
