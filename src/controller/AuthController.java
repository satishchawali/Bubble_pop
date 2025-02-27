package controller;

import database.UserDAO;
import model.User;

public class AuthController {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    // Register a new user
    public boolean register(String username, String email, String password) {
        User user = new User(0, username, email, password);
        return userDAO.createUser(user);
    }

    // Login user (simple validation)
    public User login(String email, String password) {
        for (User user : userDAO.getAllUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user; // Successful login
            }
        }
        return null; // Login failed
    }
}
