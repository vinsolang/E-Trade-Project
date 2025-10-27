package backend.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role; // customer or admin
}