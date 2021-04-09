package com.cs356.mobile.ui.event;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
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
    private Button createEventButton;
    private ExpandableListView expandableListInvitees;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        eventTitleTextBox = view.findViewById(R.id.event_title_text_box);
        monthTextBox = view.findViewById(R.id.month_text_box);
        dayTextBox = view.findViewById(R.id.day_text_box);
        yearTextBox = view.findViewById(R.id.year_text_box);
        timeTextBox = view.findViewById(R.id.time_text_box);
        locationTextBox = view.findViewById(R.id.location_text_box);
        createEventButton = view.findViewById(R.id.create_event_button);
        expandableListInvitees = view.findViewById(R.id.expandable_invitees_list);

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

        initializeData();

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

    }

    @Override
    public void uninvitePerson(String person) {

    }
}