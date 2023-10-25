package edu.uncc.assignment05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import edu.uncc.assignment05.databinding.FragmentSelectMoodBinding;
public class SelectMoodFragment extends Fragment {

    List<String> moods = Arrays.asList("Very Good", "Good", "Ok", "Sad", "Not Well");
    ListView listView;
    SelectMoodFragmentAdapter adapter;

    public SelectMoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectMoodBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectMoodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Mood");

        listView = binding.listView;
        adapter = new SelectMoodFragmentAdapter(getActivity(), R.layout.list_item_mood, moods);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Mood mood = getMood(position);
            mListener.sendSelectedMood(mood);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            mListener.cancelMoodSelection();
        });
    }

    MoodFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MoodFragmentListener) context;
    }

    interface MoodFragmentListener {
        void sendSelectedMood(Mood mood);
        void cancelMoodSelection();
    }

    public Mood getMood(int position) {
        if (position == 0) {
            return new Mood("Very Good", R.drawable.very_good);
        } else if (position == 1) {
            return new Mood("Good", R.drawable.good);
        } else if (position == 2) {
            return new Mood("Ok", R.drawable.ok);
        } else if (position == 3) {
            return new Mood("Sad", R.drawable.sad);
        } else {
            return new Mood("Not Well", R.drawable.not_well);
        }
    }
}