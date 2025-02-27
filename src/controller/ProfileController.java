package controller;

import database.UserDAO;
import model.User;

public class ProfileController {
    private UserDAO userDAO;

    public ProfileController() {
        this.userDAO = new UserDAO();
    }

    // Get user profile by ID
    public User getUserProfile(int id) {
        return userDAO.getUserById(id);
    }

    // Update user profile
    public boolean updateProfile(int id, String username, String email, String password) {
        User user = new User(id, username, email, password);
        return userDAO.updateUser(user);
    }

    // Delete user account
    public boolean deleteProfile(int id) {
        return userDAO.deleteUser(id);
    }
}
