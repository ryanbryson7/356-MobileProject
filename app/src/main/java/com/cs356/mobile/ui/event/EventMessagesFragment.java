package com.cs356.mobile.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;

public class EventMessagesFragment extends Fragment {
    private Event event;
    private TextView eventTitle;
    private ImageView backButton;

    public EventMessagesFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_messages, container, false);

        eventTitle = view.findViewById(R.id.event_title);
        backButton = view.findViewById(R.id.back_button);

        eventTitle.setText(event.getTitle());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                if (event.isInProgress()) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new InProgressEventDetailsFragment(event)).commit();
                }

                else {
                    fragmentManager.beginTransaction().replace(R.id.fragment_holder, new ConfirmedEventDetailsFragment(event)).commit();
                }

                ((MainActivity) getActivity()).setTitleText("Event Details");
                Data.getInstance().setSelectedEvent(event);
            }
        });

        return view;
    }
}
