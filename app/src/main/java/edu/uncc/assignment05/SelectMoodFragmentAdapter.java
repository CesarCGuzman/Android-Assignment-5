package edu.uncc.assignment05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SelectMoodFragmentAdapter extends ArrayAdapter<String> {

    public SelectMoodFragmentAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mood, parent, false);
        }

        String mood = getItem(position);

        ImageView imageViewMood = convertView.findViewById(R.id.imageViewMood);
        TextView textViewMood = convertView.findViewById(R.id.textViewMood);

        if(mood.equals("Very Good")){
            imageViewMood.setImageResource(R.drawable.very_good);
        } else if(mood.equals("Good")){
            imageViewMood.setImageResource(R.drawable.good);
        } else if(mood.equals("Ok")){
            imageViewMood.setImageResource(R.drawable.ok);
        } else if(mood.equals("Sad")){
            imageViewMood.setImageResource(R.drawable.sad);
        } else if(mood.equals("Not Well")){
            imageViewMood.setImageResource(R.drawable.not_well);
        }
        textViewMood.setText(mood);
        return convertView;
    }
}
