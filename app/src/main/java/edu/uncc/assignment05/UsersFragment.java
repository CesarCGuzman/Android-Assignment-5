package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.databinding.ListItemUserBinding;

public class UsersFragment extends Fragment{
    private static final String ARG_PARAM_USERS = "users";
    FragmentUsersBinding binding;
    ArrayList<User> mUsers = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    UserRecyclerViewAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }


    public static UsersFragment newInstance(ArrayList<User> users) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USERS, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsers = (ArrayList<User>)getArguments().getSerializable(ARG_PARAM_USERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users");
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UserRecyclerViewAdapter(mUsers);
        recyclerView.setAdapter(adapter);

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsers.clear();
                adapter.notifyDataSetChanged();
                mListener.clearAllUsers();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToNewUser();
            }
        });

        binding.imageViewSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToSort();
            }
        });

        binding.imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToFilter();
            }
        });
    }
    UserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UserFragmentListener)context;
    }

    public interface UserFragmentListener {
        void goToFilter();
        void goToSort();
        void clearAllUsers();
        void goToNewUser();
    }
}