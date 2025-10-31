package backend.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.dto.ChangePasswordRequest;
import backend.backend.dto.RegisterRequest;
import backend.backend.models.User;
import backend.backend.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(RegisterRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // hash this later!
                .role("customer")
                .build();

        // Do NOT set user.setId(...)
        // Let Hibernate auto-generate the UUID
        return userRepository.save(user);
    }

    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Invalid email or password"));
        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }
        return user;
    }

    // ADDED: Method to find all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // NEW: Update user profile details
    // public User updateProfile(Long userId, ProfileUpdateRequest request) throws Exception {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new Exception("User not found"));

    //     user.setUsername(request.getUsername());
    //     user.setEmail(request.getEmail());

    //     // You might want to add checks here (e.g., if new email already exists)

    //     return userRepository.save(user);
    // }

    // NEW: Change user password
    public void changePassword(UUID userId, ChangePasswordRequest request) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        // IMPORTANT: In a real app, you would use BCryptPasswordEncoder here
        if (!user.getPassword().equals(request.getCurrentPassword())) {
            throw new Exception("Invalid current password");
        }

        user.setPassword(request.getNewPassword()); // HASH THIS LATER!
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public User updateUser(UUID id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }
}
