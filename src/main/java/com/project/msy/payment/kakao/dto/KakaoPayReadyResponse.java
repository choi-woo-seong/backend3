package com.project.msy.payment.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoPayReadyResponse {
    private String tid;
    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;
    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl;
    @JsonProperty("created_at")
    private String createdAt;
}