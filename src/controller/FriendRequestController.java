package controller;

import database.FriendRequestDAO;
import database.UserDAO;
import model.FriendRequest;
import model.User;

import java.util.List;

public class FriendRequestController {
    private FriendRequestDAO friendRequestDAO;
    private UserDAO userDAO;

    public FriendRequestController() {
        this.friendRequestDAO = new FriendRequestDAO();
        this.userDAO = new UserDAO();
    }

    public boolean sendFriendRequest(int senderId, int receiverId) {
        if (friendRequestDAO.requestExists(senderId, receiverId)) {
            return false;
        }
        FriendRequest request = new FriendRequest(0, senderId, receiverId, "PENDING", null);
        return friendRequestDAO.createFriendRequest(request);
    }

    public boolean acceptFriendRequest(int requestId) {
        return friendRequestDAO.updateFriendRequestStatus(requestId, "ACCEPTED");
    }

    public boolean rejectFriendRequest(int requestId) {
        return friendRequestDAO.updateFriendRequestStatus(requestId, "REJECTED");
    }

    public List<User> getAllUsersExcept(int userId) {
        return userDAO.getAllUsersExcept(userId);
    }

    public List<FriendRequest> getPendingRequests(int userId) {
        return friendRequestDAO.getPendingRequestsForUser(userId);
    }
}
