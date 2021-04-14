package com.cs356.mobile.ui.event;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.utils.InviteeListAdapter;
import com.cs356.mobile.utils.ListListener;
import com.cs356.mobile.utils.UninvitedListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends Fragment implements ListListener {
    private ListListener listener = this;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private ExpandableListView inviteesExpandableListView;
    private ExpandableListView uninvitedExpandableListView;
    private ExpandableListAdapter inviteesExpandableListAdapter;
    private ExpandableListAdapter uninvitedExpandableListAdapter;
    private Data data;
    private String inviteesListTitle;
    private String uninvitedListTitle;
    private List<String> invitees;
    private List<String> uninvited;

    private EditText eventTitleTextBox;
    private EditText monthTextBox;
    private EditText dayTextBox;
    private EditText yearTextBox;
    private EditText timeTextBox;
    private EditText locationTextBox;
    private Button addInviteesButton;
    private Button createEventButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        initializeData();

        // Adapter stuff
        inviteesExpandableListView = view.findViewById(R.id.expandable_invitees_list);
        uninvitedExpandableListView = view.findViewById(R.id.expandable_uninvited_list);

        inviteesExpandableListAdapter = new InviteeListAdapter(this.getContext(), inviteesListTitle, invitees, listener);
        uninvitedExpandableListAdapter = new UninvitedListAdapter(this.getContext(), uninvitedListTitle, uninvited, listener);
        inviteesExpandableListView.setAdapter(inviteesExpandableListAdapter);
        uninvitedExpandableListView.setAdapter(uninvitedExpandableListAdapter);

        //expand uninvited list view by default
        uninvitedExpandableListView.expandGroup(0);

        eventTitleTextBox = view.findViewById(R.id.event_title_text_box);
        monthTextBox = view.findViewById(R.id.month_text_box);
        dayTextBox = view.findViewById(R.id.day_text_box);
        yearTextBox = view.findViewById(R.id.year_text_box);
        timeTextBox = view.findViewById(R.id.time_text_box);
        locationTextBox = view.findViewById(R.id.location_text_box);
        addInviteesButton = view.findViewById(R.id.add_button);
        createEventButton = view.findViewById(R.id.create_event_button);

        // Listeners
        addInviteesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uninvitedExpandableListView.getVisibility() == View.INVISIBLE) {
                    uninvitedExpandableListView.setVisibility(View.VISIBLE);
                    uninvitedExpandableListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    uninvitedExpandableListView.expandGroup(0);
                    inviteesExpandableListView.expandGroup(0);
                    addInviteesButton.setText("Done");
                }
                else {
                    uninvitedExpandableListView.setVisibility(View.INVISIBLE);
                    uninvitedExpandableListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                    uninvitedExpandableListView.collapseGroup(0);
                    inviteesExpandableListView.collapseGroup(0);
                    addInviteesButton.setText(R.string.add_text);
                }
            }
        });
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventTitle = eventTitleTextBox.getText().toString();
                int month = Integer.valueOf(monthTextBox.getText().toString());
                int day = Integer.valueOf(dayTextBox.getText().toString());
                int year = Integer.valueOf(yearTextBox.getText().toString());
                String time = timeTextBox.getText().toString();
                String location = locationTextBox.getText().toString();
                Event newEvent = new Event(eventTitle, month, day, year, time, location, invitees, true);
                Data.getInstance().addInProgressEvent(newEvent);

                FragmentManager fragmentManager = getParentFragmentManager();

                if (newEvent.isInProgress()) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new InProgressEventDetailsFragment(newEvent)).commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new ConfirmedEventDetailsFragment(newEvent)).commit();
                }
                ((MainActivity) getActivity()).setTitleText("Event Details");
                Data.getInstance().setSelectedEvent(newEvent);
            }
        });



        return view;
    }

    private void initializeData() {
        data = Data.getInstance();
        inviteesListTitle = getContext().getString(R.string.invitee_title);
        uninvitedListTitle = "Uninvited Friends";
        invitees = new ArrayList<>();
        uninvited = data.getFriendsList();
    }

    @Override
    public void invitePerson(String person) {
        invitees.add(person);
        uninvited.remove(person);


        // Update List Adapter/Views
        inviteesExpandableListAdapter = new InviteeListAdapter(this.getContext(), inviteesListTitle, invitees, listener);
        uninvitedExpandableListAdapter = new UninvitedListAdapter(this.getContext(), uninvitedListTitle, uninvited, listener);
        inviteesExpandableListView.setAdapter(inviteesExpandableListAdapter);
        uninvitedExpandableListView.setAdapter(uninvitedExpandableListAdapter);

        inviteesExpandableListView.expandGroup(0);
        uninvitedExpandableListView.expandGroup(0);
    }

    @Override
    public void uninvitePerson(String person) {
        invitees.remove(person);
        uninvited.add(person);

        boolean uninvitedIsExpanded = uninvitedExpandableListView.isGroupExpanded(0);

        // Update List Adapter/Views
        inviteesExpandableListAdapter = new InviteeListAdapter(this.getContext(), inviteesListTitle, invitees, listener);
        uninvitedExpandableListAdapter = new UninvitedListAdapter(this.getContext(), uninvitedListTitle, uninvited, listener);
        inviteesExpandableListView.setAdapter(inviteesExpandableListAdapter);
        uninvitedExpandableListView.setAdapter(uninvitedExpandableListAdapter);

        inviteesExpandableListView.expandGroup(0);
        if (uninvitedIsExpanded) {
            uninvitedExpandableListView.expandGroup(0);
        }
    }
}