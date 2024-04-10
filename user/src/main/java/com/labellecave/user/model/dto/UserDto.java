package com.labellecave.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank
    private String lastname;

    @NotBlank
    private String firstname;

    private String address;

    @NotBlank
    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;

    @Schema(defaultValue = "false")
    private boolean admin;
}
