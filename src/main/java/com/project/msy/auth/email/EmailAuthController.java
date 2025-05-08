package com.project.msy.auth.email;

import com.project.msy.auth.dto.EmailRequest;
import com.project.msy.auth.dto.EmailVerifyRequest;
import com.project.msy.auth.dto.VerificationTokenResponse;
import com.project.msy.config.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/email")
public class EmailAuthController {

    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    // 인증 메일 요청 API
    @PostMapping("/send")
    public ResponseEntity<VerificationTokenResponse> sendCode(@RequestBody EmailRequest request) {
        String token = emailService.sendVerificationCode(request.getEmail()); // 이메일 발송 & 토큰 발급
        return ResponseEntity.ok(new VerificationTokenResponse(token)); // 프론트에 토큰 전달
    }

    // 인증 코드 검증 API
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody EmailVerifyRequest request) {
        try {
            Claims claims = jwtUtil.parseClaims(request.getToken()); // 토큰 파싱

            String email = claims.get("email", String.class);
            String code = claims.get("code", String.class);

            // 입력값과 토큰값 비교
            if (!email.equals(request.getEmail()) || !code.equals(request.getCode())) {
                return ResponseEntity.badRequest().body("인증 실패: 이메일 또는 코드가 일치하지 않습니다.");
            }

            return ResponseEntity.ok("인증 성공");

        } catch (Exception e) {
            return ResponseEntity.status(401).body("토큰이 유효하지 않거나 만료되었습니다.");
        }
    }
}
