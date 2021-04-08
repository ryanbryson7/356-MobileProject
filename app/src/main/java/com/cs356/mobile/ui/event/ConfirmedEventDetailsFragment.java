package com.cs356.mobile.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.utils.InviteeListAdapter;

import java.util.List;

public class ConfirmedEventDetailsFragment extends Fragment {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private Data data;
    private Event event;
    private String expandableListTitle;
    private List<String> invitees;

    private TextView eventTitleTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventLocationTextView;

    public ConfirmedEventDetailsFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmed_event_details, container, false);
        initializeData();

        eventTitleTextView = view.findViewById(R.id.event_title_text_box);
        eventDateTextView = view.findViewById(R.id.event_date);
        eventTimeTextView = view.findViewById(R.id.event_time);
        eventLocationTextView = view.findViewById(R.id.event_location);

        eventTitleTextView.setText(event.getTitle());
        eventDateTextView.setText(event.getDateAsString());
        eventTimeTextView.setText(event.getTime());
        eventLocationTextView.setText(event.getLocation());

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