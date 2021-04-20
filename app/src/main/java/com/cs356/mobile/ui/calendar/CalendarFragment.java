package com.cs356.mobile.ui.calendar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.ui.event.ConfirmedEventDetailsFragment;
import com.cs356.mobile.ui.event.InProgressEventDetailsFragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    com.applandeo.materialcalendarview.CalendarView calendarView;
    LinearLayout eventDetailsBox;
    TextView activityText;
    TextView dateText;
    TextView timeText;
    TextView locationText;
    TextView eventStatusText;
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
        calendarView = view.findViewById(R.id.calendar_view);
        activityText = view.findViewById(R.id.activity_text);
        dateText = view.findViewById(R.id.date_text);
        timeText = view.findViewById(R.id.time_text);
        locationText = view.findViewById(R.id.location_text);
        moreDetailsButton = view.findViewById(R.id.more_details_button);
        eventStatusText = view.findViewById(R.id.event_status_text);

        LocalDate localDate = LocalDate.now();

        currentDay = localDate.getDayOfMonth();
        currentMonth = localDate.getMonthValue();
        currentYear = localDate.getYear();

        currentEvent = Data.getInstance().findEventByDate(currentMonth, currentDay, currentYear);
        setEventDetails();


        try {
            setUpCalendar();
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                currentEvent = Data.getInstance().findEventByEventDay(eventDay);
                setEventDetails();
            }
        });

        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();

                if (currentEvent.isInProgress()) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new InProgressEventDetailsFragment(currentEvent)).commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new ConfirmedEventDetailsFragment(currentEvent)).commit();
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

            if (currentEvent.isInProgress()) {
                eventStatusText.setText(R.string.in_progress_status_text);
            }
            else {
                eventStatusText.setText(R.string.confirmed_status_text);
            }
        }
        //valid event selected
        else {
            activityText.setText("No Current Events For This Day");
            //activityText.setPadding(0,20,0,0);
            eventStatusText.setText("");
            dateText.setText("");
            timeText.setText("");
            locationText.setText("");
            moreDetailsButton.setVisibility(View.INVISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUpCalendar() throws OutOfDateRangeException {
        calendarView.setDate(Calendar.getInstance());

        Calendar.Builder calendarBuilder = new Calendar.Builder();
        List<EventDay> eventDays = new ArrayList<>();

        for (Event confirmedEvent : Data.getInstance().getConfirmedEvents()) {
            Calendar calendar = calendarBuilder.setDate(confirmedEvent.getYear(), confirmedEvent.getMonth() - 1, confirmedEvent.getDay()).build();
            EventDay eventDay = new EventDay(calendar, R.drawable.circle_blue);
            eventDays.add(eventDay);
            confirmedEvent.setEventDay(eventDay);
        }

        for (Event inProgressEvent : Data.getInstance().getInProgressEvents()) {
            Calendar calendar = calendarBuilder.setDate(inProgressEvent.getYear(), inProgressEvent.getMonth() - 1, inProgressEvent.getDay()).build();
            EventDay eventDay = new EventDay(calendar, R.drawable.circle_red);
            eventDays.add(eventDay);

            inProgressEvent.setEventDay(eventDay);
        }

        calendarView.setEvents(eventDays);
    }
}