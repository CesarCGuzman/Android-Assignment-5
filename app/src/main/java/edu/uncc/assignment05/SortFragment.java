package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import edu.uncc.assignment05.databinding.FragmentSortBinding;

public class SortFragment extends Fragment {
    private static final String ARG_PARAM_USERS = "users";
    ArrayList<User> mUsers;


    FragmentSortBinding binding;


    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance(ArrayList<User> users) {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        args.putSerializable("users", users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getArguments() != null)) {
            mUsers = (ArrayList<User>) getArguments().getSerializable("users");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Sort");

        binding.imageViewNameDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.UserNameComparator(false));
                mListener.sortMoods(mUsers);
            }
        });

        binding.imageViewNameAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.UserNameComparator(true));
                mListener.sortMoods(mUsers);
            }
        });

        binding.imageViewAgeDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.AgeGroupComparator(false));
                mListener.sortMoods(mUsers);
            }
        });

        binding.imageViewAgeAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.AgeGroupComparator(true));
                mListener.sortMoods(mUsers);
            }
        });

        binding.imageViewFeelingDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.MoodNameComparator(false));
                mListener.sortMoods(mUsers);
            }
        });

        binding.imageViewFeelingAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mUsers, new User.MoodNameComparator(true));
                mListener.sortMoods(mUsers);
            }
        });



        binding.buttonCancel.setOnClickListener(v -> {
            mListener.cancelSort();
        });
    }

    SortFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SortFragmentListener) context;
    }

    interface SortFragmentListener {
        void sortMoods(ArrayList<User> users);
        void cancelSort();
    }
}