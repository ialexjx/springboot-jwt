package com.jwt_auth.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
