package com.example.ftechdevice.UI.Activity.ChatModule.ChatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.ChatModuleModel.ChatList;
import com.example.ftechdevice.Model.UserFireBaseModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ChatModule.Adapter.ChatAdapter;
import com.example.ftechdevice.Until.FirebaseUtil;
import com.example.ftechdevice.Until.MemoryData;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    // List to store user's messages
    private final List<ChatList> userChatList = new ArrayList<>();

    // User messages adapter
    private ChatAdapter chatAdapter;

    private FirebaseUtil firebaseUtil = new FirebaseUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        final RecyclerView messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        final String currentMobileLogin = getPhoneUserFromJWT();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getString(R.string.database_url));

        // Configure RecyclerView
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter to RecyclerView
        chatAdapter = new ChatAdapter(userChatList, ChatActivity.this);
        messagesRecyclerView.setAdapter(chatAdapter);

        // Show progress dialog while fetching chats from the database
        final MyProgressDialog progressDialog = new MyProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Fetch user chats from Firebase Realtime Database
        callGetUserList(databaseReference, currentMobileLogin, progressDialog);
    }

    private void callGetUserList(DatabaseReference databaseReference, String currentMobileLogin, MyProgressDialog progressDialog) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear old messages from the list
                userChatList.clear();
                // Loop through available users in the database
                for (DataSnapshot userData : snapshot.child("users").getChildren()) {
                    final String mobile = userData.getKey();
                    if (mobile == null) {
                        continue;
                    }
                    firebaseUtil.getUserByPhoneNumber(currentMobileLogin, new FirebaseUtil.UserListener() {
                        @Override
                        public void onUserReceived(UserFireBaseModel user) {
                            if (user.roleid == 1) {
                                if (!mobile.equals(currentMobileLogin)) {
                                    final String getUserFullName = userData.child("name").getValue(String.class);
                                    String lastMessage = "";
                                    int unseenMessagesCount = 0;
                                    String chatKey = checkChatExistence(snapshot, currentMobileLogin,  mobile);
                                    if (!chatKey.isEmpty()) {
                                        lastMessage = retrieveLastMessage(snapshot, chatKey);
                                        if (!lastMessage.isEmpty()) {
                                            unseenMessagesCount = countUnseenMessages(snapshot, chatKey, currentMobileLogin);
                                        }
                                    }
                                    loadData(chatKey, getUserFullName, mobile, lastMessage, unseenMessagesCount);
                                }
                            } else {
                                String targetMobile = "+84356970686";
                                if (!mobile.equals(currentMobileLogin) && mobile.equals(targetMobile)) {
                                    final String getUserFullName = userData.child("name").getValue(String.class);
                                    String lastMessage = "";
                                    int unseenMessagesCount = 0;
                                    String chatKey = checkChatExistence(snapshot, currentMobileLogin, mobile);
                                    if (!chatKey.isEmpty()) {
                                        lastMessage = retrieveLastMessage(snapshot, chatKey);
                                        if (!lastMessage.isEmpty()) {
                                            unseenMessagesCount = countUnseenMessages(snapshot, chatKey, currentMobileLogin);
                                        }
                                    }
                                    loadData(chatKey, getUserFullName, mobile, lastMessage, unseenMessagesCount);
                                }
                            }
                        }
                        @Override
                        public void onError(String error) {

                        }
                    });


                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }


    // Check if a chat exists for users in the database
    private String checkChatExistence(DataSnapshot snapshot, String currentMobileLogin, String getMobile) {
        String chatKey = "";
        if (snapshot.child("chat").hasChild(currentMobileLogin + getMobile)) {
            chatKey = currentMobileLogin + getMobile;
        } else if (snapshot.child("chat").hasChild(getMobile + currentMobileLogin)) {
            chatKey = getMobile + currentMobileLogin;
        }

        return chatKey; // Return the chat key if it exists, otherwise an empty string
    }

    @NotNull
    private String retrieveLastMessage(DataSnapshot snapshot, String chatKey) {
        String lastMessage = "";

        // Get the specific chat data
        DataSnapshot chatData = snapshot.child("chat").child(chatKey);

        if (chatData.exists()) {

            // Loop through messages in the chat
            for (DataSnapshot message : chatData.getChildren()) {

                // Get the sender's mobile number
                String userMobile = message.child("mobile").getValue(String.class);

                // Get the message content
                String msg = message.child("msg").getValue(String.class);

                if (userMobile != null && msg != null) {

                    // Update the last message with the current message
                    lastMessage = msg;
                }
            }
        }

        // Return the last message from the chat

        return lastMessage;
    }

    // Count the number of unseen messages from the chat
    private int countUnseenMessages(DataSnapshot snapshot, String chatKey, String currentMobileLogin) {

        int unseenMessagesCount = 0;

        // Get the timestamp of last seen message by the user from memory
        long userLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(ChatActivity.this, chatKey));

        // Retrieve chat data for the specific chat key
        DataSnapshot chatData = snapshot.child("chat").child(chatKey);

        // Iterate through messages in the chat
        for (DataSnapshot message : chatData.getChildren()) {
            String msgTimestamp = message.getKey(); // Get the message timestamp
            String userMobile = message.child("mobile").getValue(String.class); // Get the sender's mobile number

            if (userMobile == null || msgTimestamp == null) {
                continue;
            }

            if (userMobile.equals(currentMobileLogin)) {
                continue;
            }

            // Check if the message is newer than the user's last seen message and sent by the other user
            if (Long.parseLong(msgTimestamp) > userLastSeenMessage) {
                unseenMessagesCount++; // Increment the count of unseen messages
            }
        }


        return unseenMessagesCount; // Return the count of unseen messages
    }

    // Load data into the message list
    private void loadData(String chatKey, String fullName, String mobile, String lastMessage, int unseenMessagesCount) {
        if (!mobileAlreadyExists(mobile)) {
            ChatList chatList = new ChatList(chatKey, fullName, mobile, lastMessage, unseenMessagesCount);
            userChatList.add(chatList);
            chatAdapter.updateMessages(userChatList);

        }
    }

    // Check if mobile number already exists in the list
    private boolean mobileAlreadyExists(String mobile) {
        for (ChatList message : userChatList) {
            if (message.getMobile().equals(mobile)) {
                return true;
            }
        }
        return false;
    }


    private String getPhoneUserFromJWT() {
        String phone;
        String accessToken = TokenManager.getAccessToken(ChatActivity.this);
        if(accessToken != null) {
            try {
                JSONObject decodedPayload = JWTDecoder.decodeJWT(accessToken);
                phone = decodedPayload.getString("phone");
                return phone;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(ChatActivity.this, "Phone is Null", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

}