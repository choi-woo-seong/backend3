package com.project.msy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 클라이언트에게 반환할 JWT 토큰
@Getter
@AllArgsConstructor
public class VerificationTokenResponse {
    private String token;
}
