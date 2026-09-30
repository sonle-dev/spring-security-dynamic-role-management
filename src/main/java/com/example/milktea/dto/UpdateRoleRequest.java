package com.example.milktea.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {

    @NotBlank(message = "Vai trò (role) không được để trống")
    private String role; // Ví dụ: "ROLE_STAFF", "ROLE_CUSTOMER", hoặc "STAFF"
}