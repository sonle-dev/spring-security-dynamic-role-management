package com.example.milktea.controller;

import com.example.milktea.dto.UpdateRoleRequest;
import com.example.milktea.dto.UserProfileResponse;
import com.example.milktea.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Lấy thông tin user đang đăng nhập (Tất cả user đã xác thực)
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(Authentication authentication) {
        UserProfileResponse profile = userService.getCurrentUserProfile(authentication.getName());
        return ResponseEntity.ok(profile);
    }

    // 2. ADMIN cập nhật quyền cho user khác
    @PutMapping("/{id}/role")
    public ResponseEntity<UserProfileResponse> updateUserRole(@PathVariable Long id,
                                                              @Valid @RequestBody UpdateRoleRequest request) {
        UserProfileResponse response = userService.updateUserRole(id, request);
        return ResponseEntity.ok(response);
    }
}