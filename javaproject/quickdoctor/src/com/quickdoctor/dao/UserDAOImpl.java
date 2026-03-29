package com.quickdoctor.dao;

import com.quickdoctor.model.User;
import com.quickdoctor.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class UserDAOImpl implements UserDAO {
    
    private static final String DATA_FILE = "data/users.dat";
    private List<User> users;
    
    public UserDAOImpl() {
        loadUsers();
    }
    
    private void loadUsers() {
        try {
            users = FileUtil.loadListFromFile(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }
    }
    
    private void saveUsers() {
        try {
            FileUtil.saveListToFile(users, DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    @Override
    public void save(User user) {
        users.add(user);
        saveUsers();
    }
    
    @Override
    public User findById(String userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
    
    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                saveUsers();
                return;
            }
        }
    }
    
    @Override
    public void delete(String userId) {
        users = users.stream()
                .filter(u -> !u.getUserId().equals(userId))
                .collect(Collectors.toList());
        saveUsers();
    }
    
    @Override
    public boolean usernameExists(String username) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }
}
