package com.quickdoctor.dao;
import com.quickdoctor.model.User;
import java.util.List;
public interface UserDAO {
    void save(User user);
    User findById(String userId);
    User findByUsername(String username);
    List<User> findAll();
    void update(User user);
    void delete(String userId);
    boolean usernameExists(String username);
}
