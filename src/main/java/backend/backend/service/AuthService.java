package backend.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //  Do NOT set user.setId(...)
        //  Let Hibernate auto-generate the UUID
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
}
