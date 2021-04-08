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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
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

    private EditText eventTitleTextView;
    private EditText eventDayTextView;
    private EditText eventMonthTextView;
    private EditText eventYearTextView;
    private EditText eventTimeTextView;
    private EditText eventLocationTextView;
    private Button updateEventButton;
    private Button messagesButton;

    public InProgressEventDetailsFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress_event_details, container, false);

        initializeData();

        // Hook up EditTexts and update them with event info
        eventTitleTextView = view.findViewById(R.id.event_title_text_box);
        eventDayTextView = view.findViewById(R.id.day_text_box);
        eventMonthTextView = view.findViewById(R.id.month_text_box);
        eventYearTextView = view.findViewById(R.id.year_text_box);
        eventTimeTextView = view.findViewById(R.id.time_text_box);
        eventLocationTextView = view.findViewById(R.id.location_text_box);
        updateEventButton = view.findViewById(R.id.update_event_button);
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
        updateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(eventTitleTextView.getText().toString());
                event.setDay(Integer.parseInt(eventDayTextView.getText().toString()));
                event.setMonth(Integer.parseInt(eventMonthTextView.getText().toString()));
                event.setYear(Integer.parseInt(eventYearTextView.getText().toString()));
                event.setTime(eventTimeTextView.getText().toString());
                event.setLocation(eventLocationTextView.getText().toString());
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

        // Adapter stuff
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