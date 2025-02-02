package com.example.ftechdevice.UI.Activity.ChatModule.ChatActivity;

import android.annotation.SuppressLint;
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
    private final List<ChatList> userMessagesList = new ArrayList<>();

    // User messages adapter
    private ChatAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final RecyclerView messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        // Get logged in user's mobile number from memory
        final String mobileNumber = getPhoneUserFromJWT();
        Log.d("CurrentMobile", mobileNumber);

        // Obtain the reference to the Firebase database using the defined URL
        // Reference to the Firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getString(R.string.database_url));

        // Configure RecyclerView
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter to RecyclerView
        messagesAdapter = new ChatAdapter(userMessagesList, ChatActivity.this);
        messagesRecyclerView.setAdapter(messagesAdapter);

        // Show progress dialog while fetching chats from the database
        final MyProgressDialog progressDialog = new MyProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Fetch user chats from Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Clear old messages from the list
                userMessagesList.clear();

                // Loop through available users in the database
                for (DataSnapshot userData : snapshot.child("users").getChildren()) {

                    // Get user's mobile number from firebase database
                    final String mobile = userData.getKey();

                    // check next user in the list if mobile is null
                    if (mobile == null) {
                        continue;
                    }
                    String getLoginMobile = getPhoneUserFromJWT();



                    // Don't fetch details of the logged-in user
                    if (getLoginMobile.equals("356970686") || !mobile.equals(mobileNumber) && mobile.equals("356970686")) {
                        if (!mobile.equals(mobileNumber)) {
                            // Retrieve user's full name
                            final String getUserFullName = userData.child("name").getValue(String.class);
                            final String getImageUrl = userData.child("image").getValue(String.class);

                            // Other required variables
                            String lastMessage = "";
                            int unseenMessagesCount = 0;

                            // Check if chat is available with the user
                            String chatKey = checkChatExistence(snapshot, mobileNumber, mobile);

                            if (!chatKey.isEmpty()) {
                                // Getting last message in the chat
                                lastMessage = retrieveLastMessage(snapshot, chatKey);

                                if (!lastMessage.isEmpty()) {
                                    unseenMessagesCount = countUnseenMessages(snapshot, chatKey, mobileNumber);
                                }
                            }
                            // Load chat/messages in the list
                            loadData(chatKey, getUserFullName, mobile, lastMessage, unseenMessagesCount, getImageUrl);
                        }
                    }


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
                // Handle database error if any
            }
        });
    }

    // Check if a chat exists for users in the database
    private String checkChatExistence(DataSnapshot snapshot, String mobileNumber, String getMobile) {
        String chatKey = "";

        // Check if the chat exists based on different possible chat keys
        if (snapshot.child("chat").hasChild(mobileNumber + getMobile)) {
            // Chat key for the first user's chat with the second user
            chatKey = mobileNumber + getMobile;
        } else if (snapshot.child("chat").hasChild(getMobile + mobileNumber)) {
            // Chat key for the second user's chat with the first user
            chatKey = getMobile + mobileNumber;
        }

        return chatKey; // Return the chat key if it exists, otherwise an empty string
    }

    // Retrieve the last message from the chat
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
    private int countUnseenMessages(DataSnapshot snapshot, String chatKey, String mobileNumber) {

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

            if (userMobile.equals(mobileNumber)) {
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
    private void loadData(String chatKey, String fullName, String mobile, String lastMessage, int unseenMessagesCount, String imageUrl) {
        if (!mobileAlreadyExists(mobile)) {
            ChatList messagesList = new ChatList(chatKey, fullName, mobile, lastMessage,imageUrl, unseenMessagesCount);
            userMessagesList.add(messagesList);
            messagesAdapter.updateMessages(userMessagesList);
        }
    }

    // Check if mobile number already exists in the list
    private boolean mobileAlreadyExists(String mobile) {
        for (ChatList message : userMessagesList) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}