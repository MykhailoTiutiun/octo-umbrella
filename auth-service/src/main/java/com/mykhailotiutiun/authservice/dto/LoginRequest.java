package com.mykhailotiutiun.authservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class LoginRequest {
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
    private String password;
}
