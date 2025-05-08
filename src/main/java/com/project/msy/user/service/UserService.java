package com.project.msy.user.service;

import com.project.msy.auth.dto.LoginRequest;
import com.project.msy.auth.dto.LoginResponse;
import com.project.msy.auth.dto.RegisterRequest;
import com.project.msy.user.entity.Role;
import com.project.msy.user.entity.User;
import com.project.msy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화

    /**
     * 전체 회원을 조회합니다.
     * @return 모든 User 엔티티 리스트
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ID로 회원을 조회합니다.
     * @param id 회원 PK
     * @return User 엔티티 (존재하지 않으면 예외 발생)
     */
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. id=" + id));
    }

    /**
     * ID로 회원을 삭제합니다.
     * @param id 회원 PK
     */
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 회원이 존재하지 않습니다. id=" + id);
        }
        userRepository.deleteById(id);
    }
    // ✅ userId 중복 검사
    public boolean isUserIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public void signup(RegisterRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}
