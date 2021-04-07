package com.cs356.mobile.ui.calendar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.ui.event.ConfirmedEventDetailsFragment;
import com.cs356.mobile.ui.event.InProgressEventDetailsFragment;

import java.time.LocalDate;

public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    TextView pageTitle;
    LinearLayout eventDetailsBox;
    TextView activityText;
    TextView dateText;
    TextView timeText;
    TextView locationText;
    Button moreDetailsButton;
    Event currentEvent;
    int currentDay;
    int currentMonth;
    int currentYear;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        eventDetailsBox = view.findViewById(R.id.event_details_box);
        calendarView = view.findViewById(R.id.calendar_widget);
        activityText = view.findViewById(R.id.activity_text);
        dateText = view.findViewById(R.id.date_text);
        timeText = view.findViewById(R.id.time_text);
        locationText = view.findViewById(R.id.location_text);
        moreDetailsButton = view.findViewById(R.id.more_details_button);
        //pageTitle = view.findViewById(R.id.page_title);

        LocalDate localDate = LocalDate.now();

        currentDay = localDate.getDayOfMonth();
        currentMonth = localDate.getMonthValue();
        currentYear = localDate.getYear();

        currentEvent = Data.getInstance().findEventByDate(currentMonth, currentDay, currentYear);
        setEventDetails();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                currentEvent = Data.getInstance().findEventByDate(month, dayOfMonth, year);
                setEventDetails();
            }
        });

        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();

                if (currentEvent.isInProgress()) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new InProgressEventDetailsFragment()).commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new ConfirmedEventDetailsFragment()).commit();
                }

                ((MainActivity) getActivity()).setTitleText("Event Details");
                Data.getInstance().setSelectedEvent(currentEvent);
            }
        });

        return view;
    }

    private void setEventDetails() {
        if (currentEvent != null) {
            activityText.setText("Activity: " + currentEvent.getTitle());
            activityText.setPadding(0,0,0,0);

            dateText.setText("Date: " + currentEvent.getMonth() + "/" + currentEvent.getDay() +"/" +
                    currentEvent.getYear());
            timeText.setText("Time: " + currentEvent.getTime());
            locationText.setText("Location: " + currentEvent.getLocation());
            moreDetailsButton.setVisibility(View.VISIBLE);
        }
        else {
            activityText.setText("No Current Events For This Day");
            activityText.setPadding(0,40,0,0);
            dateText.setText("");
            timeText.setText("");
            locationText.setText("");
            moreDetailsButton.setVisibility(View.INVISIBLE);
        }
    }
}