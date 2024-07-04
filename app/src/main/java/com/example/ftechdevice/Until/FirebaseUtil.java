package com.example.ftechdevice.Until;

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
}
