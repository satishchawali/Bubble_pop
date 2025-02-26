package model;

import java.util.Date;

public class FriendRequest {

    private int id;
    private int senderId;
    private int receiverId;
    private String status;
    private Date requestDate;
    
    public FriendRequest(){
    }

    public FriendRequest(int id,int senderId,int receiverId,String status,Date requestDate){
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.requestDate = requestDate;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getSenderId(){
        return senderId;
    }

    public void setSenderId(int senderId){
        this.senderId = senderId;
    }

    public int getReceiverId(){
        return receiverId;
    }

    public void setReceiverId(int receiverId){
        this.receiverId = receiverId;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Date getRequestDate(){
        return requestDate;
    }

    public void setRequestDate(Date requestDate){
        this.requestDate = requestDate;
    }

    @Override
    public String toString(){
        return "FriendRequest{ " +
            "id = " + id +
            ", senderID = " + senderId +
            ", receiverID = " + receiverId +
            ", status = " + status +
            ", requestDate = " + requestDate +
            "}";
    }
}
