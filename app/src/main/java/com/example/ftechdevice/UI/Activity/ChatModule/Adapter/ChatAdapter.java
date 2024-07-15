package com.example.ftechdevice.UI.Activity.ChatModule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.Model.ChatModuleModel.ChatList;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ChatModule.MessageActivity.MessageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatList> chatLists;
    private final Context context;

    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        ChatList list2 = chatLists.get(position);

        holder.name.setText(list2.getFullName());
        holder.lastMessage.setText(list2.getLastMessage());

        if (list2.getUnseenMessages() == 0) {
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getUnseenMessages() + "");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.theme_color_80));
        }

        Glide.with(context)
                .load(list2.getIamgeUrl())
                .placeholder(R.drawable.user_icon) // A default placeholder if image is not available
                .error(R.drawable.user_icon) // An error image if loading fails
                .into(holder.profileImage);



        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setting unseen messages as 0
                list2.setUnseenMessages(0);

                notifyItemChanged(holder.getAdapterPosition());

                // create intent to open Chat activity
                Intent intent = new Intent(context, MessageActivity.class);

                // Test

                // append data along with the intent
                intent.putExtra("mobile", list2.getMobile());
                intent.putExtra("full_name", list2.getFullName());
                intent.putExtra("chat_key", list2.getChatKey());

                // launch Chat activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMessages(List<ChatList> userChatList) {
        chatLists = userChatList;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final private TextView name;
        final private TextView lastMessage;
        final private TextView unseenMessages;
        final private LinearLayout rootLayout;
        final private CircleImageView profileImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseenMessages = itemView.findViewById(R.id.unseenMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            profileImage = itemView.findViewById(R.id.profilePic);
        }
    }
}
