package controller;

import database.UserDAO;
import model.User;

public class ProfileController {
    private UserDAO userDAO;

    public ProfileController() {
        this.userDAO = new UserDAO();
    }

    public User getUserProfile(int id) {
        return userDAO.getUserById(id);
    }

    public boolean updateProfile(int id, String username, String email, String password) {
        User user = new User(id, username, email, password);
        return userDAO.updateUser(user);
    }

    public boolean deleteProfile(int id) {
        return userDAO.deleteUser(id);
    }
}
