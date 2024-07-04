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

        final String currentkeyFirebaseMobileLogin = getPhoneUserFromJWT();
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
        callGetUserList(databaseReference, currentkeyFirebaseMobileLogin, progressDialog);
    }

    private void callGetUserList(DatabaseReference databaseReference, String currentkeyFirebaseMobileLogin, MyProgressDialog progressDialog) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear old messages from the list
                userChatList.clear();
                // Loop through available users in the database
                for (DataSnapshot userData : snapshot.child("users").getChildren()) {
                    // Get user's keyFirebaseMobile number from firebase database
                    final String keyFirebaseMobile = userData.getKey();
                    // check next user in the list if keyFirebaseMobile is null
                    if (keyFirebaseMobile == null) {
                        continue;
                    }
                    firebaseUtil.getUserByPhoneNumber(currentkeyFirebaseMobileLogin, new FirebaseUtil.UserListener() {
                        @Override
                        public void onUserReceived(UserFireBaseModel user) {
                           if (user.roleid == 2 ) {
                               if (!keyFirebaseMobile.equals(currentkeyFirebaseMobileLogin)) {
                                   // Retrieve user's full name
                                   final Long getUserRoleid = userData.child("roleid").getValue(Long.class);
                                   if (getUserRoleid == 1 ) {
                                       final String getUserFullName = userData.child("name").getValue(String.class);
                                       // Other required variables
                                       String lastMessage = "";
                                       int unseenMessagesCount = 0;
                                       // Check if chat is available with the user
                                       String chatKey = checkChatExistence(snapshot, currentkeyFirebaseMobileLogin, keyFirebaseMobile);
                                       if (!chatKey.isEmpty()) {
                                           lastMessage = retrieveLastMessage(snapshot, chatKey);
                                           if (!lastMessage.isEmpty()) {
                                               unseenMessagesCount = countUnseenMessages(snapshot, chatKey, currentkeyFirebaseMobileLogin);
                                           }
                                       }
                                       loadData(chatKey, getUserFullName, keyFirebaseMobile, lastMessage, unseenMessagesCount);
                                   }
                               }
                           } else {
                               if (!keyFirebaseMobile.equals(currentkeyFirebaseMobileLogin)) {
                                   // Retrieve user's full name
                                       final String getUserFullName = userData.child("name").getValue(String.class);
                                       // Other required variables
                                       String lastMessage = "";
                                       int unseenMessagesCount = 0;
                                       // Check if chat is available with the user
                                       String chatKey = checkChatExistence(snapshot, currentkeyFirebaseMobileLogin, keyFirebaseMobile);
                                       if (!chatKey.isEmpty()) {
                                           lastMessage = retrieveLastMessage(snapshot, chatKey);
                                           if (!lastMessage.isEmpty()) {
                                               unseenMessagesCount = countUnseenMessages(snapshot, chatKey, currentkeyFirebaseMobileLogin);
                                           }
                                       }
                                       loadData(chatKey, getUserFullName, keyFirebaseMobile, lastMessage, unseenMessagesCount);
                                   }
                           }
                        }
                        @Override
                        public void onError(String error) {
                            Log.e("CheckResponeFireBase", "Error: " + error);
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
    private String checkChatExistence(DataSnapshot snapshot, String currentkeyFirebaseMobileLogin, String getkeyFirebaseMobile) {
        String chatKey = "";
        if (snapshot.child("chat").hasChild(currentkeyFirebaseMobileLogin + getkeyFirebaseMobile)) {
            chatKey = currentkeyFirebaseMobileLogin + getkeyFirebaseMobile;
        } else if (snapshot.child("chat").hasChild(getkeyFirebaseMobile + currentkeyFirebaseMobileLogin)) {
            chatKey = getkeyFirebaseMobile + currentkeyFirebaseMobileLogin;
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

                // Get the sender's keyFirebaseMobile number
                String userkeyFirebaseMobile = message.child("keyFirebaseMobile").getValue(String.class);

                // Get the message content
                String msg = message.child("msg").getValue(String.class);

                if (userkeyFirebaseMobile != null && msg != null) {

                    // Update the last message with the current message
                    lastMessage = msg;
                }
            }
        }

        // Return the last message from the chat

        return lastMessage;
    }

    // Count the number of unseen messages from the chat
    private int countUnseenMessages(DataSnapshot snapshot, String chatKey, String currentkeyFirebaseMobileLogin) {

        int unseenMessagesCount = 0;

        // Get the timestamp of last seen message by the user from memory
        long userLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(ChatActivity.this, chatKey));

        // Retrieve chat data for the specific chat key
        DataSnapshot chatData = snapshot.child("chat").child(chatKey);

        // Iterate through messages in the chat
        for (DataSnapshot message : chatData.getChildren()) {
            String msgTimestamp = message.getKey(); // Get the message timestamp
            String userkeyFirebaseMobile = message.child("keyFirebaseMobile").getValue(String.class); // Get the sender's keyFirebaseMobile number

            if (userkeyFirebaseMobile == null || msgTimestamp == null) {
                continue;
            }

            if (userkeyFirebaseMobile.equals(currentkeyFirebaseMobileLogin)) {
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
    private void loadData(String chatKey, String fullName, String keyFirebaseMobile, String lastMessage, int unseenMessagesCount) {
        if (!keyFirebaseMobileAlreadyExists(keyFirebaseMobile)) {
            ChatList chatList = new ChatList(chatKey, fullName, keyFirebaseMobile, lastMessage, unseenMessagesCount);
            userChatList.add(chatList);
            chatAdapter.updateMessages(userChatList);

        }
    }

    // Check if keyFirebaseMobile number already exists in the list
    private boolean keyFirebaseMobileAlreadyExists(String keyFirebaseMobile) {
        for (ChatList message : userChatList) {
            if (message.getMobile().equals(keyFirebaseMobile)) {
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