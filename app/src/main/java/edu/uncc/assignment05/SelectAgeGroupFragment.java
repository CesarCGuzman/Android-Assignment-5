package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import edu.uncc.assignment05.databinding.FragmentSelectAgeGroupBinding;

public class SelectAgeGroupFragment extends Fragment {

    String[] ageGroups = {"Under 12 years old", "12-17 years old", "18-24 years old", "25-34 years old", "35-44 years old", "45-54 years old", "55-64 years old", "65-74 years old", "75 years or older"};
    ListView listView;
    ArrayAdapter<String> adapter;

    public SelectAgeGroupFragment() {
        // Required empty public constructor
    }

    FragmentSelectAgeGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectAgeGroupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Age Group");
        listView = binding.listView;

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ageGroups);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            mListener.sendSelectedAgeGroup(ageGroups[position]);
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelAgeGroupSelection();
            }
        });
    }

    AgeGroupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AgeGroupListener) context;
    }

    interface AgeGroupListener{
        void sendSelectedAgeGroup(String ageGroup);
        void cancelAgeGroupSelection();
    }
}