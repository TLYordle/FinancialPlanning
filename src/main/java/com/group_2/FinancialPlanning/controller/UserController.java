package com.group_2.FinancialPlanning.controller;

import com.group_2.FinancialPlanning.entity.User;
import com.group_2.FinancialPlanning.service.EmailService;
import com.group_2.FinancialPlanning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Email already exists")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("message", "Email already exists"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Something went wrong"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra nếu email bị bỏ trống
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "This field is required");
        } else {
            // Kiểm tra định dạng email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!email.matches(emailRegex)) {
                errors.put("email", "Please enter a valid email address");
            }
        }

        // Kiểm tra nếu password bị bỏ trống
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "This field is required");
        }

        // Nếu có lỗi thì trả về luôn
        if (!errors.isEmpty()) {
            response.put("success", false);
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> userOptional = userService.getUserByEmail(email);

        // Kiểm tra thông tin đăng nhập
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            User user = userOptional.get();
            response.put("success", true);
            response.put("email", user.getEmail());
            response.put("role", user.getRole().toString());
            response.put("userID", user.getUser_id().toString());
            return ResponseEntity.ok(response);
        } else {
            errors.put("password", "Either email address or password is incorrect. Please try again");
            response.put("success", false);
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        if (userService.deleteUser(id)) {
            response.put("success", true);
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }
}
