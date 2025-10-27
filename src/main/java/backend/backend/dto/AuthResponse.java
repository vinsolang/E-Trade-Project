package backend.backend.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private String username;
    private String email;
    private String role;
}