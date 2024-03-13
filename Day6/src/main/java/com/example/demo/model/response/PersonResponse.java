package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
