package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.assignment05.databinding.FragmentFilterBinding;
public class FilterFragment extends Fragment {
    private static final String ARG_PARAM_USERS = "users";
    ArrayList<User> mUsers = new ArrayList<>();
    FragmentFilterBinding binding;
    RecyclerView recyclerViewName, recyclerViewAgeGroup, recyclerViewMood;
    LinearLayoutManager layoutManagerName, layoutManagerAgeGroup, layoutManagerMood;
    FilterNameRecyclerViewAdapter adapterName;
    FilterAgeGroupRecyclerViewAdapter adapterAgeGroup;
    FilterMoodRecyclerViewAdapter adapterMood;

    public FilterFragment() {
        // Required empty public constructor
    }
    public static FilterFragment newInstance(ArrayList<User> users) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USERS, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
              mUsers = (ArrayList<User>)getArguments().getSerializable(ARG_PARAM_USERS);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Filter");

        // Name Filter RecyclerView
        recyclerViewName = binding.recyclerViewName;
        recyclerViewName.setHasFixedSize(true);

        layoutManagerName = new LinearLayoutManager(getContext(), layoutManagerName.HORIZONTAL, false);
        recyclerViewName.setLayoutManager(layoutManagerName);

        adapterName = new FilterNameRecyclerViewAdapter(mUsers);
        recyclerViewName.setAdapter(adapterName);


        // Age Group Filter RecyclerView
        recyclerViewAgeGroup = binding.recyclerViewAge;
        recyclerViewAgeGroup.setHasFixedSize(true);

        layoutManagerAgeGroup = new LinearLayoutManager(getContext(), layoutManagerAgeGroup.HORIZONTAL, false);
        recyclerViewAgeGroup.setLayoutManager(layoutManagerAgeGroup);

        adapterAgeGroup = new FilterAgeGroupRecyclerViewAdapter(mUsers);
        recyclerViewAgeGroup.setAdapter(adapterAgeGroup);

        // Mood Filter RecyclerView
        recyclerViewMood = binding.recyclerViewFeeling;
        recyclerViewMood.setHasFixedSize(true);

        layoutManagerMood = new LinearLayoutManager(getContext(), layoutManagerMood.HORIZONTAL, false);
        recyclerViewMood.setLayoutManager(layoutManagerMood);

        adapterMood = new FilterMoodRecyclerViewAdapter(mUsers);
        recyclerViewMood.setAdapter(adapterMood);


        binding.buttonClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearFilter();
            }
        });
    }

    FilterFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FilterFragmentListener) context;
    }

    interface FilterFragmentListener {
        void clearFilter();
    }
}