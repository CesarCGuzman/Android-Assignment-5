package edu.uncc.assignment05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterMoodRecyclerViewAdapter extends RecyclerView.Adapter<FilterMoodRecyclerViewAdapter.FilterMoodViewHolder> {
    static ArrayList<User> users;
    private Set<Integer> displayedMoods = new HashSet<>();


    public FilterMoodRecyclerViewAdapter(ArrayList<User> data) {
        this.users = data;
    }

    @NonNull
    @Override
    public FilterMoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_filter, parent, false);
        FilterMoodViewHolder filterMoodViewHolder = new FilterMoodViewHolder(view);
        return filterMoodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterMoodViewHolder holder, int position) {
        // Get the User object at the current position
        User user = users.get(position);

        // Check if the mood exists in the Map, if it does, display it
        Map<Integer, Integer> moods = getMoods(users);
        int mood = user.getMood().getImageResourceId();

        // Display a mood only once if it was found in the Map and hasn't been displayed before
        if (moods.containsKey(mood) && !displayedMoods.contains(mood)) {
            holder.imageView.setImageResource(mood);
            holder.imageView.setTag(mood);
            displayedMoods.add(mood); // Add the mood to the set of displayed moods
        } else {
            // If the mood was not found in the Map, has a count of 0, or has been displayed before, hide the view
            holder.imageView.setVisibility(View.GONE);
            holder.itemView.setVisibility(View.GONE);
        }
    }


    public static Map<Integer, Integer> getMoods(ArrayList<User> users) {
        // Define moods
        int[] moods = {R.drawable.very_good, R.drawable.good, R.drawable.ok, R.drawable.sad, R.drawable.not_well};

        // Create a HashMap to store the moods and their counts
        Map<Integer, Integer> moodsFound = new HashMap<>();

        // Initialize counts to 0 for all moods
        for (int mood : moods) {
            moodsFound.put(mood, 0);
        }

        // Count the occurrences of moods in the ArrayList
        for (User user : users) {
            int mood = user.getMood().getImageResourceId();
            if (moodsFound.containsKey(mood)) {
                int count = moodsFound.get(mood);
                moodsFound.put(mood, count + 1);
            }
        }
        return moodsFound;
    }


    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class FilterMoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public FilterMoodViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mood = (int)imageView.getTag();
                    ArrayList<User> filteredUsers = filterUsersByMood(mood, users);
                    mListener.returnFilteredUsers(filteredUsers);
                }
            });
        }
    }

    public static ArrayList<User> filterUsersByMood(int moodResourceId, ArrayList<User> users) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getMood().getImageResourceId() == moodResourceId) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    static FilterMoodListener mListener;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mListener = (FilterMoodListener) recyclerView.getContext();
    }

    public interface FilterMoodListener {
        void returnFilteredUsers(ArrayList<User> filteredUsers);
    }


}
