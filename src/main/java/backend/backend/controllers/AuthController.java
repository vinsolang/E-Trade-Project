package backend.backend.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.dto.AuthResponse;
import backend.backend.dto.ChangePasswordRequest;
import backend.backend.dto.RegisterRequest;
import backend.backend.models.User;
import backend.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://e-trade-shop.vercel.app/") // React frontend
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
    // NEW: Endpoint to update user profile
    // @PutMapping("/profile/{userId}")
    // public User updateProfile(@PathVariable Long userId, @RequestBody ProfileUpdateRequest request) throws Exception {
    //     // NOTE: Add token validation to ensure only the logged-in user or an Admin can change this.
    //     return authService.updateProfile(userId, request);
    // }
    // NEW: Endpoint to change password
    @PutMapping("/change-password/{userId}")
    public void changePassword(@PathVariable UUID userId, @RequestBody ChangePasswordRequest request) throws Exception {
        // NOTE: Add token validation
        authService.changePassword(userId, request);
    }
    //  Delete user by UUID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            authService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //  Update user by UUID
    @PutMapping("/users/profile/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        try {
            User user = authService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}