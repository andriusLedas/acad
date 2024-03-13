package com.example.demo.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record PersonRequest(@NotNull
                            @Size(min = 3, max = 20)
                            @Schema(description = "first name")
                            String firstName,

                            @NotNull
                            @Size(min = 3, max = 20)
                            @Schema(description = "last name")
                            String lastName,

                            @NotNull
                            @NotEmpty(message = "Email cannot be empty")
                            @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                            @Schema(description = "e-mail")
                            String email,

                            @NotNull
                            @NotBlank
                            @Schema(description = "phone")
                            String phone
                            ) {
}
