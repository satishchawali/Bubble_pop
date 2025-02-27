package controller;
import controller.AuthController;
import controller.ProfileController;
import model.User;

import java.util.Scanner;

public class AuthTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthController authController = new AuthController();
        ProfileController profileController = new ProfileController();

        while (true) {
            System.out.println("\n===== Bubble Pop Authentication Test =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Profile");
            System.out.println("4. Update Profile");
            System.out.println("5. Delete Profile");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Register
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean registered = authController.register(username, email, password);
                    if (registered) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Registration failed!");
                    }
                    break;

                case 2: // Login
                    System.out.print("Enter email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    User loggedInUser = authController.login(loginEmail, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case 3: // View Profile
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    User user = profileController.getUserProfile(userId);
                    if (user != null) {
                        System.out.println("Profile Details: " + user);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 4: // Update Profile
                    System.out.print("Enter user ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();

                    boolean updated = profileController.updateProfile(updateId, newUsername, newEmail, newPassword);
                    System.out.println(updated ? "Profile updated successfully!" : "Profile update failed.");
                    break;

                case 5: // Delete Profile
                    System.out.print("Enter user ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    boolean deleted = profileController.deleteProfile(deleteId);
                    System.out.println(deleted ? "User deleted successfully!" : "User deletion failed.");
                    break;

                case 6: // Exit
                    System.out.println("Exiting test...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
