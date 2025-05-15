package com.project.msy.auth.dto;

import com.project.msy.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String userId;
    private final String name;
    private final String email;
    private final String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId(); // KAKAO_1234 같은 OAuth ID
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
    }
}