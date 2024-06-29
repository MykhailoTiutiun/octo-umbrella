package com.mykhailotiutiun.authservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String firstName;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String secondName;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String phoneNumber;
}
