package com.project.msy.order.repository;

import com.project.msy.order.dto.OrderSummaryDto;
import com.project.msy.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT new com.project.msy.order.dto.OrderSummaryDto(
        o.id, u.name, p.name, o.quantity, o.totalPrice, o.orderDate
    )
    FROM Order o
    JOIN o.user u
    JOIN o.product p
    ORDER BY o.orderDate DESC
""")
    List<OrderSummaryDto> findOrderSummaries();

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    BigDecimal findTotalSales();

    @Query("SELECT COUNT(o) FROM Order o")
    Long countOrders();
}