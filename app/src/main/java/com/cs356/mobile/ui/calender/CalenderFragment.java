package com.cs356.mobile.ui.calender;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CalenderFragment extends Fragment {
    CalendarView calendarView;
    Event currentEvent;
    int currentDay;
    int currentMonth;
    int currentYear;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);


        calendarView = view.findViewById(R.id.calender_widget);
        LocalDate localDate = LocalDate.now();

        currentDay = localDate.getDayOfMonth();
        currentMonth = localDate.getMonthValue();
        currentYear = localDate.getYear();

        currentEvent = Data.getInstance().findEventByDate(currentMonth, currentDay, currentYear);
        if (currentEvent != null) {
            setEventDetails();
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += month;
                currentEvent = Data.getInstance().findEventByDate(month, dayOfMonth, year);

                if (currentEvent != null) {
                    setEventDetails();
                }
            }
        });
        return view;
    }

    private void setEventDetails() {

    }
}