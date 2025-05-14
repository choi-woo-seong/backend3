package com.project.msy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendVerificationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject("[모셔요] 이메일 인증 코드 안내");
        message.setText("다음 인증 코드를 입력해 주세요: " + code);
        System.out.println("[메일 발송] " + to + "로 인증 코드 전송 중... 코드: " + code);
        mailSender.send(message);
    }
}
