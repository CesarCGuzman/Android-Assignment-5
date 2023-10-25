package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.assignment05.databinding.FragmentAddUserBinding;

public class AddUserFragment extends Fragment {
    private static final String ARG_PARAM_USERS = "users";
    static String selectedAgeGroup;
    static Mood selectedMood;
    private ArrayList<User> mUsers;

    public void setSelectedAgeGroup(String selectedAgeGroup) {
        this.selectedAgeGroup = selectedAgeGroup;
    }

    public void setSelectedMood(Mood selectedMood){
        this.selectedMood = selectedMood;
    }

    public static void resetValues(){
        selectedAgeGroup = null;
        selectedMood = null;
    }

    public AddUserFragment() {
        // Required empty public constructor
    }

    public static AddUserFragment newInstance(ArrayList<User> users) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USERS, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsers = (ArrayList<User>) getArguments().getSerializable(ARG_PARAM_USERS);
        }
    }

    FragmentAddUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add User");

        if (selectedAgeGroup != null) {
            binding.textViewAgeGroup.setText(selectedAgeGroup);
        }

        if (selectedMood != null) {
            binding.textViewMood.setText(selectedMood.name);
            binding.imageViewMood.setImageResource(selectedMood.imageResourceId);
            binding.imageViewMood.setVisibility(View.VISIBLE);
        } else {
            binding.imageViewMood.setVisibility(View.INVISIBLE);
        }

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectAge();
            }
        });

        binding.buttonSelectMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectMood();
            }
        });


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextText.getText().toString();
                String ageGroup = binding.textViewAgeGroup.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Please input your name", Toast.LENGTH_SHORT).show();
                } else if (selectedAgeGroup == null) {
                    Toast.makeText(getContext(), "Please select an age group", Toast.LENGTH_SHORT).show();
                } else if (selectedMood == null) {
                    Toast.makeText(getContext(), "Please select a mood", Toast.LENGTH_SHORT).show();
                } else {
                    mUsers.add(new User(name, ageGroup, selectedMood));
                    mListener.sendUsers(mUsers);
                }
            }
        });
    }

    AddUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddUserFragmentListener) context;
    }

    interface AddUserFragmentListener {
        void sendUsers(ArrayList<User> users);
        void goToSelectAge();
        void goToSelectMood();
    }
}