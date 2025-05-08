package com.project.msy.auth.dto;

import lombok.Getter;
import lombok.Setter;

// 이메일 인증 확인 시 사용: 이메일 + 인증코드 + JWT 토큰
@Getter
@Setter
public class EmailVerifyRequest {
    private String email;
    private String code;
    private String token;
}
