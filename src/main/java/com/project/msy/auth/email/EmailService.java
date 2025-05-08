package com.project.msy.auth.email;

import com.project.msy.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    // 인증 코드 전송 및 토큰 반환
    public String sendVerificationCode(String email) {
        String code = createCode(); // 6자리 숫자 생성

        // 이메일 메시지 구성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("모셔요 이메일 인증 코드입니다");
        message.setText("인증 코드: " + code);

        mailSender.send(message); // 메일 전송

        // 인증코드를 포함한 JWT 생성 (5분간 유효)
        return jwtUtil.generateEmailVerificationToken(email, code, 5 * 60 * 1000);
    }

    // 6자리 난수 생성 함수
    private String createCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
