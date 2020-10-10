package com.example.messengerapp.Models;

public class Chat {

    private String message;
    private String sender;
    private String reciver;

    public Chat(String message, String sender, String reciver, String imageUrl) {
        this.message = message;
        this.sender = sender;
        this.reciver = reciver;
    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

}
