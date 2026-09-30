package com.example.milktea.service.impl;

import com.example.milktea.dto.UpdateRoleRequest;
import com.example.milktea.dto.UserProfileResponse;
import com.example.milktea.entity.User;
import com.example.milktea.repository.UserRepository;
import com.example.milktea.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileResponse getCurrentUserProfile(String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng!"));

        return mapToProfileResponse(user);
    }

    @Override
    public UserProfileResponse updateUserRole(Long userId, UpdateRoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với ID: " + userId));

        // Chuẩn hóa tên quyền có tiền tố ROLE_
        String normalizedRole = request.getRole().trim().toUpperCase();
        if (!normalizedRole.startsWith("ROLE_")) {
            normalizedRole = "ROLE_" + normalizedRole;
        }

        // Kiểm tra tính hợp lệ của Role
        if (!normalizedRole.equals("ROLE_ADMIN") &&
                !normalizedRole.equals("ROLE_STAFF") &&
                !normalizedRole.equals("ROLE_CUSTOMER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vai trò không hợp lệ! Chỉ chấp nhận ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER");
        }

        user.setRole(normalizedRole);
        User updatedUser = userRepository.save(user);

        return mapToProfileResponse(updatedUser);
    }

    private UserProfileResponse mapToProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}