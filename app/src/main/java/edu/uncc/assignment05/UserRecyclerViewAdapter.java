package edu.uncc.assignment05;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {
    ArrayList<User> users;

    public UserRecyclerViewAdapter(ArrayList<User> data) {
        this.users = data;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewUserName.setText(user.name);
        holder.textViewUserAgeGroup.setText(user.ageGroup);
        if (user.getMood() != null) {
            holder.imageViewUserMood.setImageResource(user.mood.getImageResourceId());
        }
        holder.user = user;
        holder.userList = users;

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewUserAgeGroup;
        ImageView imageViewUserMood;
        ImageView imageViewDelete;
        View rootView;
        User user;
        ArrayList<User> userList;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserAgeGroup = itemView.findViewById(R.id.textViewUserAgeGroup);
            imageViewUserMood = itemView.findViewById(R.id.imageViewUserMood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.goToProfile(user);
                }
            });

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteUser(user);
                    // userList.remove(user);
                }
            });
        }
    }

    static UserRecyclerViewAdapterListener mListener;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mListener = (UserRecyclerViewAdapterListener) recyclerView.getContext();
    }

    public interface UserRecyclerViewAdapterListener {
        void deleteUser(User user);
        void goToProfile(User user);
    }
}
