package backend.backend.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.backend.dto.AuthResponse;
import backend.backend.dto.RegisterRequest;
import backend.backend.models.User;
import backend.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/") // React frontend
public class AuthController {

    @Autowired
    private AuthService authService;

    // Get All Users
     // ADDED: New GET mapping to retrieve all users
    @GetMapping("/users")
    public List<User> findAll() {
        // NOTE: In a real app, you would secure this endpoint and only allow ADMIN roles.
        return authService.findAllUsers();
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) throws Exception {
        User user = authService.register(request);
        AuthResponse response = new AuthResponse();
        response.setMessage("Registration successful");
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    // AuthController.java - Corrected login method
    @PostMapping("/login")
    public AuthResponse login(@RequestBody RegisterRequest request) throws Exception {
        //  Error: authService.login(request.getEmail(),request.getUsername(), request.getPassword());
        
        //  Solution: Only pass email and password to the service method
        User user = authService.login(request.getEmail(), request.getPassword()); 
        
        AuthResponse response = new AuthResponse();
        response.setMessage("Login successful");
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}