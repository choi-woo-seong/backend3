package com.project.msy.payment.kakao;

import com.project.msy.cart.dto.CartItemResponse;
import com.project.msy.cart.service.CartItemService;
import com.project.msy.order.service.OrderService;
import com.project.msy.payment.kakao.dto.KakaoPayApproveResponse;
import com.project.msy.payment.kakao.dto.KakaoPayReadyResponse;
import com.project.msy.stats.service.StatsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/kakao")
@RequiredArgsConstructor
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;
    private final StatsService statsService;
    private final CartItemService cartItemService;

    // 결제 준비(ready) 메서드는 기존 코드 유지
    @PostMapping("/ready")
    public Map<String, String> ready(
            @RequestParam Long orderId,
            @RequestParam String itemName,
            @RequestParam int totalAmount,
            HttpSession session,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        KakaoPayReadyResponse resp = kakaoPayService.ready(orderId, userId, itemName, totalAmount);
        session.setAttribute("kakao_tid", resp.getTid());
        session.setAttribute("userId", userId);
        Map<String, String> result = new HashMap<>();
        result.put("redirectUrl", resp.getNextRedirectPcUrl());
        result.put("tid", resp.getTid());
        return result;
    }

    // 결제 승인(approve) 메서드 전체 교체
    @GetMapping("/approve")
    public ResponseEntity<Map<String, Object>> approve(
            @RequestParam("pg_token") String pgToken,
            @RequestParam("orderId") Long orderId,
            @RequestParam("tid") String tid,
            HttpSession session,
            Authentication authentication
    ) {
        System.out.println("[DEBUG] /approve 진입! orderId=" + orderId + ", pg_token=" + pgToken);
        //String tid    = (String) session.getAttribute("kakao_tid");
        String userId = authentication.getName();

        // 1) 카카오페이 승인
        KakaoPayApproveResponse approveResp =
                kakaoPayService.approve(tid, orderId, userId, pgToken);

        // 2) 장바구니 조회
        List<CartItemResponse> cartItems = cartItemService.getCartItemsForUser(userId);

        // 3) orders/stats 기록, 장바구니 비우기
        cartItems.forEach(item -> {

            Long productId   = item.getProductId();
            int qty          = item.getQuantity();
            int unitPrice    = item.getUnitPrice().intValue();
            int subTotal     = unitPrice * qty;

            // 주문 저장
            orderService.recordOrder(userId, productId, qty, subTotal, approveResp);
            // 매출 통계 저장
            statsService.recordSale(productId, qty);
        });
        cartItemService.clearCart(userId);

        // 4) 결과 반환
        Map<String, Object> response = new HashMap<>();
        response.put("message", "결제가 완료되었습니다.");
        response.put("approveInfo", approveResp);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "결제가 취소되었습니다.";
    }

    @GetMapping("/fail")
    public String fail() {
        return "결제에 실패했습니다.";
    }
}