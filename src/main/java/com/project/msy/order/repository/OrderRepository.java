package com.project.msy.order.repository;

import com.project.msy.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    long count();
    long countByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    @Query(value = """
    SELECT
        SUM(total_price) AS total,
        SUM(CASE WHEN DATE(order_date) = CURDATE() THEN total_price ELSE 0 END) AS today,
        SUM(CASE WHEN DATE(order_date) = CURDATE() - INTERVAL 1 DAY THEN total_price ELSE 0 END) AS yesterday
    FROM orders
""", nativeQuery = true)
    Map<String, Object> getSalesSummary();
}