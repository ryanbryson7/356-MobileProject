package com.cs356.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ExpandableListView expandableListView;
    private Data data;
    private HashMap<String, List<Event>> expandableListData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        initializeData();
        expandableListView = textView.findViewById(R.id.expandable_event_lists);

        return root;
    }

    private void initializeData() {
        data = Data.getInstance();
        expandableListData = new HashMap<>();

        // Load data from Data into the expandableListData
        expandableListData.put(getContext().getString(R.string.confirmed_events), data.getConfirmedEvents());
        expandableListData.put(getContext().getString(R.string.in_progress_events), data.getInProgressEvents());
    }
}