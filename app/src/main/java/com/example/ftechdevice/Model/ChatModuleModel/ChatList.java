package com.example.ftechdevice.Model.ChatModuleModel;

public class ChatList {

    private final String chatKey;
    private final String fullName;
    private final String mobile;
    private final String lastMessage;
    private String iamgeUrl;
    private int unseenMessages;

    public ChatList(String chatKey, String fullName, String mobile, String lastMessage, int unseenMessages) {
        this.chatKey = chatKey;
        this.fullName = fullName;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
    }

    public ChatList(String chatKey, String fullName, String mobile, String lastMessage, String iamgeUrl, int unseenMessages) {
        this.chatKey = chatKey;
        this.fullName = fullName;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.iamgeUrl = iamgeUrl;
        this.unseenMessages = unseenMessages;
    }

    public String getChatKey() {
        return chatKey;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }
}
