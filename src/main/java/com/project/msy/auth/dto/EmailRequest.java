package com.project.msy.auth.dto;

import lombok.Getter;
import lombok.Setter;

// 이메일 인증 요청 시 사용: 이메일 주소만 포함
@Getter
@Setter
public class EmailRequest {
    private String email;
}
