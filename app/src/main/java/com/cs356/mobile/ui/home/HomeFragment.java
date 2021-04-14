package com.cs356.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cs356.mobile.MainActivity;
import com.cs356.mobile.R;
import com.cs356.mobile.model.Data;
import com.cs356.mobile.model.Event;
import com.cs356.mobile.ui.event.ConfirmedEventDetailsFragment;
import com.cs356.mobile.ui.event.InProgressEventDetailsFragment;
import com.cs356.mobile.utils.EventListAdapter;

import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements EventListAdapter.Listener{
    private EventListAdapter.Listener listener = this;

    private ExpandableListView inProgressExpandableListView;
    private ExpandableListAdapter inProgressExpandableListAdapter;
    private ExpandableListView confirmedExpandableListView;
    private ExpandableListAdapter confirmedExpandableListAdapter;
    private Data data;
    private HashMap<String, List<Event>> inProgressExpandableListData;
    private HashMap<String, List<Event>> confirmedExpandableListData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initializeData();

        inProgressExpandableListView = root.findViewById(R.id.in_progress_event_lists);
        inProgressExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });
        confirmedExpandableListView = root.findViewById(R.id.confirmed_event_list);
        confirmedExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        inProgressExpandableListAdapter = new EventListAdapter(this.getContext(), inProgressExpandableListData, listener);
        confirmedExpandableListAdapter = new EventListAdapter(this.getContext(), confirmedExpandableListData, listener);
        inProgressExpandableListView.setAdapter(inProgressExpandableListAdapter);
        confirmedExpandableListView.setAdapter(confirmedExpandableListAdapter);

        return root;
    }

    private void initializeData() {
        data = Data.getInstance();
        confirmedExpandableListData = new HashMap<>();
        inProgressExpandableListData = new HashMap<>();

        // Load data from Data into the expandableListData
        confirmedExpandableListData.put(getContext().getString(R.string.confirmed_events), data.getConfirmedEvents());
        inProgressExpandableListData.put(getContext().getString(R.string.in_progress_events), data.getInProgressEvents());
    }

    @Override
    public void onEventClicked(Event event) {
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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}