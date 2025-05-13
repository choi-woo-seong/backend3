package com.project.msy.admin.dashboard.controller;

import com.project.msy.admin.dashboard.dto.*;
import com.project.msy.admin.dashboard.service.DashboardService;
import com.project.msy.order.repository.OrderRepository;
import com.project.msy.product.repository.ProductRepository;
import com.project.msy.stats.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService service;
    private final ProductRepository productRepository;
    private final StatsRepository statsRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/facility-count")
    public long getFacilityCount() {
        return service.getTotalFacilityCount();
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();

        // ✅ 상품 수 집계
        Map<String, Object> productStats = new HashMap<>();
        long totalProducts = productRepository.count();
        long inStock = productRepository.countByStockQuantityGreaterThan(0);
        long outOfStock = totalProducts - inStock;

        productStats.put("total", totalProducts);
        productStats.put("inStock", inStock);
        productStats.put("outOfStock", outOfStock);

        summary.put("products", productStats);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/user-daily-growth")
    public ResponseEntity<List<UserDailySignupDto>> getUserDailyGrowth() {
        return ResponseEntity.ok(service.getDailyUserGrowth());
    }

    @GetMapping("/sale-summary")
    public ResponseEntity<Map<String, Object>> getSaleSummary() {
        Map<String, Object> summary = orderRepository.getSalesSummary();
        return ResponseEntity.ok(summary);
    }
}