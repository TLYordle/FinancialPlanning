package com.group_2.FinancialPlanning.service;

import com.group_2.FinancialPlanning.entity.User;
import com.group_2.FinancialPlanning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Random;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setPassword(createPassword());
        return userRepository.save(user);
    }

    private String createPassword(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < 10; i++) {
            // Sinh số ngẫu nhiên từ 0 đến 9
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
        
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setBirthday(updatedUser.getBirthday());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setDepartment(updatedUser.getDepartment());
            existingUser.setPosition(updatedUser.getPosition());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setIsActive(updatedUser.getIsActive());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + id);
        }
    }
}
