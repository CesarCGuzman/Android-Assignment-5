package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UsersFragment.UserFragmentListener,
                                                               UserRecyclerViewAdapter.UserRecyclerViewAdapterListener,
                                                               ProfileFragment.ProfileFragmentListener,
                                                               AddUserFragment.AddUserFragmentListener,
                                                               SelectAgeGroupFragment.AgeGroupListener,
                                                               SelectMoodFragment.MoodFragmentListener,
                                                               SortFragment.SortFragmentListener,
                                                               FilterNameRecyclerViewAdapter.FilterNameRecyclerViewAdapterListener,
                                                               FilterFragment.FilterFragmentListener,
                                                               FilterAgeGroupRecyclerViewAdapter.FilterAgeGroupListener,
                                                               FilterMoodRecyclerViewAdapter.FilterMoodListener {

    ArrayList<User> mUsers = new ArrayList<>();
    ArrayList<User> filteredUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsers.add(new User("Bob Smith", "18-24 years old", new Mood("Good", R.drawable.good)));
        mUsers.add(new User("Tom Green", "25-34 years old", new Mood("Very Good", R.drawable.very_good)));
        mUsers.add(new User("Bob Smith", "35-44 years old", new Mood("Ok", R.drawable.ok)));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rootView, new UsersFragment().newInstance(mUsers), "users-fragment")
                .commit();

    }



    /* ----- Users Fragment Functions ----- */

    @Override
    public void clearAllUsers() {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if (addUserFragment != null) {
            addUserFragment.resetValues();
        }
        mUsers.clear();
    }

    @Override
    public void deleteUser(User user) {
        mUsers.remove(user);
        filteredUsers.remove(user);
        // Send back the filtered users if we had a filter applied
        if (filteredUsers.size() > 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, UsersFragment.newInstance(filteredUsers), "users-fragment")
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, UsersFragment.newInstance(mUsers), "users-fragment")
                    .commit();
        }
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(mUsers), "users-fragment")
                .commit();*/
    }

    @Override
    public void goToProfile(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(user), "profile-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToFilter() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, FilterFragment.newInstance(mUsers), "filter-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, SortFragment.newInstance(mUsers), "sort-fragment")
                .addToBackStack(null)
                .commit();
    }

    /* ----- Add User Fragment ----- */

    @Override
    public void goToNewUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment().newInstance(mUsers), "add-user-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToUsers() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendUsers(ArrayList<User> users) {
        mUsers = users;
        AddUserFragment.resetValues();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(mUsers), "users-fragment")
                .commit();
    }

    /* ----- Select Age Fragment ----- */

    @Override
    public void goToSelectAge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectAgeGroupFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void sendSelectedAgeGroup(String ageGroup) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if (addUserFragment != null) {
            addUserFragment.setSelectedAgeGroup(ageGroup);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelAgeGroupSelection() {
        getSupportFragmentManager().popBackStack();
    }

    /* ----- Select Mood Fragment ----- */

    @Override
    public void goToSelectMood() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectMoodFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendSelectedMood(Mood mood) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if (addUserFragment != null) {
            addUserFragment.setSelectedMood(mood);
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void cancelMoodSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sortMoods(ArrayList<User> users) {
        mUsers = users;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(mUsers), "users-fragment")
                .commit();
    }

    @Override
    public void cancelSort() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void returnFilteredUsers(ArrayList<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(filteredUsers), "users-fragment")
                .commit();
    }

    @Override
    public void clearFilter() {
        filteredUsers.clear();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UsersFragment.newInstance(mUsers), "users-fragment")
                .commit();
    }
}