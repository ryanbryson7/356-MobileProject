package com.cs356.mobile.ui.event;

import android.content.Context;
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
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.ui.home.HomeFragment;
import com.cs356.mobile.utils.InviteeListAdapter;
import com.cs356.mobile.utils.ListListener;
import com.cs356.mobile.utils.UninvitedListAdapter;

import java.util.List;

public class InProgressEventDetailsFragment extends Fragment implements ListListener {
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
    private Event event;
    private String inviteesListTitle;
    private String uninvitedListTitle;
    private List<String> invitees;
    private List<String> uninvited;

    private EditText eventTitleTextView;
    private EditText eventDayTextView;
    private EditText eventMonthTextView;
    private EditText eventYearTextView;
    private EditText eventTimeTextView;
    private EditText eventLocationTextView;
    private Button addInviteesButton;
    private Button updateEventButton;
    private Button confirmEventButton;
    private Button messagesButton;

    public InProgressEventDetailsFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress_event_details, container, false);

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

        // Hook up EditTexts and update them with event info
        eventTitleTextView = view.findViewById(R.id.event_title_text_box);
        eventDayTextView = view.findViewById(R.id.day_text_box);
        eventMonthTextView = view.findViewById(R.id.month_text_box);
        eventYearTextView = view.findViewById(R.id.year_text_box);
        eventTimeTextView = view.findViewById(R.id.time_text_box);
        eventLocationTextView = view.findViewById(R.id.location_text_box);
        addInviteesButton = view.findViewById(R.id.add_button);
        updateEventButton = view.findViewById(R.id.update_event_button);
        confirmEventButton = view.findViewById(R.id.confirm_event_button);
        messagesButton = view.findViewById(R.id.messages_button);


        eventTitleTextView.setText(event.getTitle());
        eventDayTextView.setText(String.valueOf(event.getDay()));
        eventMonthTextView.setText(String.valueOf(event.getMonth()));
        eventYearTextView.setText(String.valueOf(event.getYear()));
        eventTimeTextView.setText(event.getTime());
        eventLocationTextView.setText(event.getLocation());

        // Listeners
        eventTitleTextView.addTextChangedListener(textWatcher);
        eventDayTextView.addTextChangedListener(textWatcher);
        eventMonthTextView.addTextChangedListener(textWatcher);
        eventYearTextView.addTextChangedListener(textWatcher);
        eventTimeTextView.addTextChangedListener(textWatcher);
        eventLocationTextView.addTextChangedListener(textWatcher);
        addInviteesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uninvitedExpandableListView.getVisibility() == View.INVISIBLE) {
                    uninvitedExpandableListView.setVisibility(View.VISIBLE);
                    uninvitedExpandableListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    addInviteesButton.setText("Done");
                }
                else {
                    uninvitedExpandableListView.setVisibility(View.INVISIBLE);
                    uninvitedExpandableListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                    addInviteesButton.setText(R.string.add_text);
                }
            }
        });
        updateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(eventTitleTextView.getText().toString());
                event.setDay(Integer.parseInt(eventDayTextView.getText().toString()));
                event.setMonth(Integer.parseInt(eventMonthTextView.getText().toString()));
                event.setYear(Integer.parseInt(eventYearTextView.getText().toString()));
                event.setTime(eventTimeTextView.getText().toString());
                event.setLocation(eventLocationTextView.getText().toString());
                event.setInvitees(invitees);
                Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });
        confirmEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Data.getInstance().confirmEvent(event);

                fragmentManager.beginTransaction().replace(R.id.fragment_holder, new HomeFragment()).commit();
            }
        });
        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Fragment destinationFragment = null;
                if (event.getTitle().equals("Ski at Sundance Resort")) {
                    destinationFragment = new EventMessagesFragment(event);
                }
                else {
                    destinationFragment = new EmptyEventMessagesFragment(event);
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_holder,
                        destinationFragment).commit();
                ((MainActivity) getActivity()).setTitleText("Event Chat");
                Data.getInstance().setSelectedEvent(event);
            }
        });



        return view;
    }

    private void initializeData() {
        data = Data.getInstance();
        inviteesListTitle = getContext().getString(R.string.invitee_title);
        uninvitedListTitle = "Uninvited Friends";
        invitees = event.getInvitees();
        uninvited = event.getUninvitedFriends(data.getFriendsList());
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