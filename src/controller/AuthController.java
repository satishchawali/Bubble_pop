package controller;

import database.UserDAO;
import model.User;

public class AuthController {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String email, String password) {
        User user = new User(0, username, email, password);
        return userDAO.createUser(user);
    }

    public User login(String email, String password) {
        for (User user : userDAO.getAllUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
