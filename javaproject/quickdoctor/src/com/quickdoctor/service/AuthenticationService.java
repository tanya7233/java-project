package com.quickdoctor.service;

import com.quickdoctor.dao.UserDAO;
import com.quickdoctor.dao.UserDAOImpl;
import com.quickdoctor.model.User;
import com.quickdoctor.model.UserRole;
import com.quickdoctor.util.PasswordUtil;
import com.quickdoctor.util.ValidationUtil;

import java.util.UUID;

/**
 * Service class for authentication and user management
 */
public class AuthenticationService {
    
    private final UserDAO userDAO;
    private User currentUser;
    
    public AuthenticationService() {
        this.userDAO = new UserDAOImpl();
        this.currentUser = null;
    }
    
    /**
     * Register a new user
     */
    public boolean register(String username, String password, String email, 
                           String phoneNumber, UserRole role) {
        // Validate inputs
        if (!ValidationUtil.isNotEmpty(username)) {
            System.out.println("Username cannot be empty");
            return false;
        }
        
        if (!ValidationUtil.isValidPassword(password)) {
            System.out.println("Password must be at least 6 characters");
            return false;
        }
        
        if (!ValidationUtil.isValidEmail(email)) {
            System.out.println("Invalid email format");
            return false;
        }
        
        if (!ValidationUtil.isValidPhone(phoneNumber)) {
            System.out.println("Phone number must be 10 digits");
            return false;
        }
        
        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            System.out.println("Username already exists");
            return false;
        }
        
        // Create and save user
        String userId = UUID.randomUUID().toString();
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(userId, username, hashedPassword, role, email, phoneNumber);
        userDAO.save(user);
        
        System.out.println("User registered successfully!");
        return true;
    }
    
    /**
     * Login a user
     */
    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);
        
        if (user == null) {
            System.out.println("User not found");
            return false;
        }
        
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            System.out.println("Invalid password");
            return false;
        }
        
        currentUser = user;
        System.out.println("Login successful! Welcome, " + username);
        return true;
    }
    
    /**
     * Logout current user
     */
    public void logout() {
        currentUser = null;
        System.out.println("Logged out successfully");
    }
    
    /**
     * Get current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Check if a user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Check if current user has a specific role
     */
    public boolean hasRole(UserRole role) {
        return currentUser != null && currentUser.getRole() == role;
    }
}
