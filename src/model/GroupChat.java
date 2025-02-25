package model;

import java.util.List;

public class GroupChat {
    private int id;
    private String groupName;
    private List<Integer> memberIds; // List of user IDs

    public GroupChat(int id, String groupName, List<Integer> memberIds) {
        this.id = id;
        this.groupName = groupName;
        this.memberIds = memberIds;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public List<Integer> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds; }
}
