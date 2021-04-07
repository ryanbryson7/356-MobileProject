//package com.cs356.mobile.ui.notifications;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cs356.mobile.R;
//import com.cs356.mobile.model.Data;
//import com.cs356.mobile.model.Notification;
//
//import java.util.List;
//
//public class NotificationsFragment extends Fragment {
//    private RecyclerView notificationRecyclerView;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
//
//
//        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
//
//        //initialize recyclerView
//        RecyclerAdapter adapter = new RecyclerAdapter(Data.getInstance().getNotifications());
//        notificationRecyclerView.setAdapter(adapter);
//
//        return view;
//    }
//
//    private class RecyclerAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
//        private List<Notification> notifications;
//
//        RecyclerAdapter(List<Notification> notifications) {
//            this.notifications = notifications;
//        }
//
//        @NonNull
//        @Override
//        public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = getLayoutInflater().inflate(R.layout.notification_item, parent, false);
//
//            return new SearchResultViewHolder(view, viewType);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
//            holder.bind(peopleResults.get(position));
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return notifications.size();
//        }
//    }
//
//    private class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private final ImageView icon;
//        private final TextView firstLineText;
//        private final TextView secondLineText;
//
//        private final int viewType;
//
//        SearchResultViewHolder(View view, int viewType) {
//            super(view);
//            this.viewType = viewType;
//
//            itemView.setOnClickListener(this);
//
//            if (viewType == PERSON_ITEM_VIEW_TYPE) {
//                icon = itemView.findViewById(R.id.gender_icon);
//                firstLineText = itemView.findViewById(R.id.person_name);
//                secondLineText = null;
//            } else {
//                icon = itemView.findViewById(R.id.marker_icon);
//                firstLineText = itemView.findViewById(R.id.event_details);
//                secondLineText = itemView.findViewById(R.id.person_name);
//            }
//        }
//
//        private void bind(Notification notification) {
////            this.person = person;
////
////            //male
////            if (person.getGender().equals("m")) {
////                Drawable genderIcon = new IconDrawable(SearchActivity.this,
////                        FontAwesomeIcons.fa_male).colorRes(R.color.skyBlue).sizeDp(30);
////                icon.setImageDrawable(genderIcon);
////            }
////
////            //female
////            else {
////                Drawable genderIcon = new IconDrawable(SearchActivity.this,
////                        FontAwesomeIcons.fa_female).colorRes(R.color.pink).sizeDp(30);
////                icon.setImageDrawable(genderIcon);
////            }
////            firstLineText.setText(person.getFirstName() + " " + person.getLastName());
//        }
//
//        @Override
//        public void onClick(View view) {
////            if (viewType == PERSON_ITEM_VIEW_TYPE) {
////                //start new person activity
////                Intent personActivityIntent = new Intent(SearchActivity.this,
////                        PersonActivity.class);
////                personActivityIntent.putExtra(PERSON_ID, person.getPersonID());
////                startActivity(personActivityIntent);
////            }
////            else {
////                //set new event Intent
////                Intent eventActivityIntent = new Intent(SearchActivity.this,
////                        EventActivity.class);
////                eventActivityIntent.putExtra(EVENT_ID, event.getEventID());
////                startActivity(eventActivityIntent);
////            }
//        }
//    }
//}