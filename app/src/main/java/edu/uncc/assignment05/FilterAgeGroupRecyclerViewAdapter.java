package edu.uncc.assignment05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterAgeGroupRecyclerViewAdapter extends RecyclerView.Adapter<FilterAgeGroupRecyclerViewAdapter.FilterAgeGroupViewHolder> {
    static ArrayList<User> users;
    private Set<String> displayedAgeGroups = new HashSet<>();

    public FilterAgeGroupRecyclerViewAdapter(ArrayList<User> data) {
        this.users = data;
    }

    @NonNull
    @Override
    public FilterAgeGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text_filter, parent, false);
        FilterAgeGroupViewHolder filterAgeGroupViewHolder = new FilterAgeGroupViewHolder(view);
        return filterAgeGroupViewHolder;
    }

    // I also had GPT help me out here.
    // The main issue I was having was that it would display each Age Group multiple times.
    // GPT Suggested I try using a Set to keep track of which Age Groups have already been displayed, so that they aren't displayed again.
    @Override
    public void onBindViewHolder(@NonNull FilterAgeGroupViewHolder holder, int position) {
        // Get the User object at the current position
        User user = users.get(position);

        // Check if the ageGroup has 1 or more occurrences in the Map, if it does, display it
        Map<String, Integer> ageGroups = getAgeGroups(users);
        String ageGroup = user.getAgeGroup();
        int count = ageGroups.getOrDefault(ageGroup, 0);

        if(count > 0 && !displayedAgeGroups.contains(ageGroup)) {
            holder.textView.setText(ageGroup);
            holder.textView.setTag(ageGroup);
            displayedAgeGroups.add(ageGroup); // Add the ageGroup to the set of displayed ageGroups
        } else {
            // If the ageGroup was not found in the Map, has a count of 0, or has been displayed before, hide the view
            holder.textView.setVisibility(View.GONE);
            holder.itemView.setVisibility(View.GONE);
        }
    }

    public static Map<String, Integer> getAgeGroups(ArrayList<User> users) {
        // Define age groups
        String[] ageGroups = {"Under 12 years old", "12-17 years old", "18-24 years old", "25-34 years old", "35-44 years old", "45-54 years old", "55-64 years old", "65-74 years old", "75 years or older"};

        // Create a HashMap to store the counts of age groups
        Map<String, Integer> agesFound = new HashMap<>();

        // Initialize counts to 0 for all age groups
        for (String ageGroup : ageGroups) {
            agesFound.put(ageGroup, 0);
        }

        // Count the occurrences of age groups in the ArrayList
        for (User user : users) {
            String ageGroup = user.getAgeGroup();
            if (ageGroup != null && agesFound.containsKey(ageGroup)) {
                int count = agesFound.get(ageGroup);
                agesFound.put(ageGroup, count + 1);
            }
        }
        return agesFound;
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class FilterAgeGroupViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public FilterAgeGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ageGroup = textView.getText().toString();
                    ArrayList<User> filteredUsers = filterUsersByAgeGroup(ageGroup);
                    mListener.returnFilteredUsers(filteredUsers);
                }
            });
        }
    }

    public static ArrayList<User> filterUsersByAgeGroup(String ageGroup) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getAgeGroup().equals(ageGroup)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    static FilterAgeGroupListener mListener;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mListener = (FilterAgeGroupListener) recyclerView.getContext();
    }

    public interface FilterAgeGroupListener {
        void returnFilteredUsers(ArrayList<User> filteredUsers);
    }
}
