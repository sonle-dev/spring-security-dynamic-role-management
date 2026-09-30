package com.example.milktea.service;

import com.example.milktea.dto.UpdateRoleRequest;
import com.example.milktea.dto.UserProfileResponse;

public interface UserService {
    UserProfileResponse getCurrentUserProfile(String currentUserEmail);
    UserProfileResponse updateUserRole(Long userId, UpdateRoleRequest request);
}