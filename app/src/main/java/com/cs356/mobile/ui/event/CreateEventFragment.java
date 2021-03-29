package com.cs356.mobile.ui.event;

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

public class CreateEventFragment extends Fragment {

    private CreateEventViewModel mCreateEventViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mCreateEventViewModel =
                new ViewModelProvider(this).get(CreateEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_event, container, false);
        final TextView textView = root.findViewById(R.id.text_create_event);
        mCreateEventViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}