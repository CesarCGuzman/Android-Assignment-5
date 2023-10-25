package edu.uncc.assignment05;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterNameRecyclerViewAdapter extends RecyclerView.Adapter<FilterNameRecyclerViewAdapter.FilterNameViewHolder>  {

    static ArrayList<User> users;
    List<Character> letters;

    public FilterNameRecyclerViewAdapter(ArrayList<User> data) {
        this.users = data;
    }

    @NonNull
    @Override
    public FilterNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text_filter, parent, false);
        FilterNameViewHolder filterNameViewHolder = new FilterNameViewHolder(view);
        return filterNameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterNameViewHolder holder, int position) {
        letters = getLetters(users);
        if (position >= 0 && position < letters.size()) {
            Character letter = letters.get(position);
            holder.textView.setText(letter.toString());
        } else {
            holder.rootView.setVisibility(View.INVISIBLE);
        }
    }

    public List<Character> getLetters(ArrayList<User> users) {
        Set<Character> letters = new HashSet<>();
        for (User user : users) {
            String name = user.getName();
            if(name != null && Character.isLetter(name.charAt(0)) && !letters.contains(Character.toUpperCase(name.charAt(0)))) {
                letters.add(Character.toUpperCase(name.charAt(0)));
            }
        }
        List<Character> lettersList = new ArrayList<>(letters);
        return lettersList;
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class FilterNameViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View rootView;
        public FilterNameViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            textView = itemView.findViewById(R.id.textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    char letter = textView.getText().charAt(0);
                    ArrayList<User> filteredUsers = filterUsersByName(letter);
                    mListener.returnFilteredUsers(filteredUsers);

                }
            });
        }
    }

    public static ArrayList<User> filterUsersByName(char letter) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getName().charAt(0) == letter) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    static FilterNameRecyclerViewAdapterListener mListener;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mListener = (FilterNameRecyclerViewAdapterListener) recyclerView.getContext();
    }

    public interface FilterNameRecyclerViewAdapterListener {
        void returnFilteredUsers(ArrayList<User> filteredUsers);
    }
}


