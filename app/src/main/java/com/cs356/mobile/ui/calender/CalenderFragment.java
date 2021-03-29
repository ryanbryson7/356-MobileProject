package com.cs356.mobile.ui.calender;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cs356.mobile.R;

public class CalenderFragment extends Fragment {

    private CalenderViewModel mCalenderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mCalenderViewModel =
                new ViewModelProvider(this).get(CalenderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calender, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        mCalenderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}