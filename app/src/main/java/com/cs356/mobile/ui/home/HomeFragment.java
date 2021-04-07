package com.cs356.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.ui.event.EventDetailsFragment;
import com.cs356.mobile.utils.EventListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements EventListAdapter.Listener{
    private EventListAdapter.Listener listener = this;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private Data data;
    private HashMap<String, List<Event>> expandableListData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initializeData();


        expandableListView = root.findViewById(R.id.expandable_event_lists);

        expandableListAdapter = new EventListAdapter(this.getContext(), expandableListData, listener);
        expandableListView.setAdapter(expandableListAdapter);

        return root;
    }

    private void initializeData() {
        data = Data.getInstance();
        expandableListData = new HashMap<>();

        // Load data from Data into the expandableListData
        expandableListData.put(getContext().getString(R.string.confirmed_events), data.getConfirmedEvents());
        expandableListData.put(getContext().getString(R.string.in_progress_events), data.getInProgressEvents());
    }

    @Override
    public void onEventClicked(Event event) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_holder, new EventDetailsFragment()).commit();
        ((MainActivity) getActivity()).setTitleText("Event Details");
        Data.getInstance().setSelectedEvent(event);
    }
}