package com.example.ftechdevice.Until;

import com.example.ftechdevice.Model.UserFireBaseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtil {
    private DatabaseReference mDatabase;

    public FirebaseUtil() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public void updateFCMToken(String phoneNumber, String newFCMToken) {
        mDatabase.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDatabase.child(phoneNumber).child("FCMToken").setValue(newFCMToken)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    System.out.println("FCMToken updated successfully.");
                                } else {
                                    System.err.println("Failed to update FCMToken.");
                                }
                            });
                } else {
                    System.err.println("User not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
            }
        });
    }

    public interface FCMTokenListener {
        void onTokenReceived(String token);
        void onError(String error);
    }

    public void getFCMToken(String phoneNumber, FCMTokenListener listener) {
        mDatabase.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String token = dataSnapshot.child("FCMToken").getValue(String.class);
                    if (token != null) {
                        listener.onTokenReceived(token);
                    } else {
                        listener.onError("FCMToken not found for user.");
                    }
                } else {
                    listener.onError("User not found.");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Error: " + databaseError.getMessage());
            }
        });
    }



    public interface UserListener {
        void onUserReceived(UserFireBaseModel user);
        void onError(String error);
    }

    public void getUserByPhoneNumber(String phoneNumber, UserListener listener) {
        mDatabase.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserFireBaseModel user = dataSnapshot.getValue(UserFireBaseModel.class);
                    listener.onUserReceived(user);
                } else {
                    listener.onError("User not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Error: " + databaseError.getMessage());
            }
        });
    }
}
