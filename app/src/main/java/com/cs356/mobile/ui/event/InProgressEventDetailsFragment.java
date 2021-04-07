package com.cs356.mobile.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.utils.InviteeListAdapter;

import java.util.HashMap;
import java.util.List;

public class InProgressEventDetailsFragment extends Fragment {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private Data data;
    private Event event;
    private String expandableListTitle;
    private List<String> invitees;

    public InProgressEventDetailsFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress_event_details, container, false);

        initializeData();

        expandableListView = view.findViewById(R.id.expandable_invitees_list);

        expandableListAdapter = new InviteeListAdapter(this.getContext(), expandableListTitle, invitees);
        expandableListView.setAdapter(expandableListAdapter);

        return view;
    }

    private void initializeData() {
        data = Data.getInstance();
        expandableListTitle = getContext().getString(R.string.invitee_title);
        invitees = event.getInvitees();
    }
}