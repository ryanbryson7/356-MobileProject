package com.cs356.mobile.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Notification;
import com.cs356.mobile.ui.event.ConfirmedEventDetailsFragment;
import com.cs356.mobile.ui.event.EventMessagesFragment;
import com.cs356.mobile.ui.event.InProgressEventDetailsFragment;


import java.util.List;

public class NotificationsFragment extends Fragment {
    private RecyclerView notificationRecyclerView;
    private static final int NOTIFICATION_ITEM_VIEW_TYPE = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);


        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initialize recyclerView
        RecyclerAdapter adapter = new RecyclerAdapter(Data.getInstance().getNotifications());
        notificationRecyclerView.setAdapter(adapter);

        return view;
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
        private List<Notification> notifications;

        RecyclerAdapter(List<Notification> notifications) {
            this.notifications = notifications;
        }

        @Override
        public int getItemViewType(int position) {
            return NOTIFICATION_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.notification_item, parent, false);

            return new SearchResultViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
            holder.bind(notifications.get(position));
        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }

    private class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView notificationType;
        private final TextView notificationTitle;
        private final TextView notificationBody;

        private final int viewType;
        private Notification notification;

        SearchResultViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            notificationType = view.findViewById(R.id.notification_type);
            notificationTitle = view.findViewById(R.id.notification_title);
            notificationBody = view.findViewById(R.id.notification_body);
        }

        private void bind(Notification notification) {
            this.notification = notification;
            notificationType.setText( "(" +notification.getNotificationType() + ")");
            notificationTitle.setText(notification.getNotificationTitle());
            notificationBody.setText(notification.getNotificationBody());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getParentFragmentManager();
            Fragment fragmentDestination = null;

            if (notification.getNotificationType().equals("Event Reminder")) {
                fragmentDestination = new ConfirmedEventDetailsFragment(notification.getAssociatedEvent());
                ((MainActivity) getActivity()).setTitleText("Event Details");
                Data.getInstance().setSelectedEvent(notification.getAssociatedEvent());
            }

            else if (notification.getNotificationType().equals("New Message")) {
                fragmentDestination = new EventMessagesFragment(notification.getAssociatedEvent());
                ((MainActivity) getActivity()).setTitleText("Event Chat");
                Data.getInstance().setSelectedEvent(notification.getAssociatedEvent());
            }
            Data.getInstance().removeNotification(notification);
            ((MainActivity) getActivity()).setNotificationStatus();
            fragmentManager.beginTransaction().replace(R.id.fragment_holder, fragmentDestination).commit();
        }
    }
}