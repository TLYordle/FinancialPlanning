package com.group_2.FinancialPlanning.controller;

import com.group_2.FinancialPlanning.entity.User;
import com.group_2.FinancialPlanning.service.EmailService;
import com.group_2.FinancialPlanning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.Optional;

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

    @PostMapping
    public User createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            System.out.println(errors);
            throw new IllegalArgumentException(errors);
        }

        User userResult = userService.createUser(user);
        if (userResult != null) {
            emailService.sendAccount(userResult.getEmail(), userResult.getUserName(), userResult.getPassword());
        }
        return userResult;
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
            response.put("username", user.getUserName());
            response.put("role", user.getRole().toString());
            response.put("userID", user.getUserID().toString());
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
