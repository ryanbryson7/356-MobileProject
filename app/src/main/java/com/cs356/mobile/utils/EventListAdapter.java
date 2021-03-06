package com.cs356.mobile.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cs356.mobile.R;
import com.cs356.mobile.model.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListAdapter extends BaseExpandableListAdapter {
    private Listener listener;
    private Context context;
    private HashMap<String, List<Event>> expandableListData;

    private int marginSize = 0;

    public EventListAdapter(Context context, HashMap<String, List<Event>> expandableListData, Listener listener) {
        this.listener = listener;
        this.context = context;
        this.expandableListData = expandableListData;
    }

    @Override
    public int getGroupCount() {
        return expandableListData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getEventListByIndex(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getEventListByIndex(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getEventListByIndex(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_header, null);
        }
        // TODO: Only problem with this is the margin is blue
//        if (isExpanded)
//            convertView.setPadding(0, 0, 0, 0);
//        else
//            convertView.setPadding(0, 0, 0, marginSize);

        TextView eventLabelView = convertView.findViewById(R.id.list_header);

        int count = 0;
        String group = "";
        for (Map.Entry<String, List<Event>> entry : expandableListData.entrySet()) {
            group = entry.getKey();
            if (count == groupPosition) {
                break;
            }
        }

        eventLabelView.setText(group);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list_item, null);
        }

        if (childPosition == getEventListByIndex(groupPosition).size() - 1) {
            convertView.setPadding(0, 0, 0, marginSize);
        } else
            convertView.setPadding(0, 0, 0, 20);

        TextView eventTitleView = convertView.findViewById(R.id.event_list_title);
        TextView eventStatusView = convertView.findViewById(R.id.event_list_status);
        TextView eventDateView = convertView.findViewById(R.id.event_list_date);
        TextView eventTimeView = convertView.findViewById(R.id.event_list_time);
        TextView eventLocationView = convertView.findViewById(R.id.event_list_location);

        List<Event> eventList = getEventListByIndex(groupPosition);
        Event event = eventList.get(childPosition);

        eventTitleView.setText(event.getTitle());
        eventDateView.setText(event.getDateAsString());
        eventTimeView.setText(event.getTime());
        eventLocationView.setText(event.getLocation());

        if (event.isInProgress()) {
            eventStatusView.setText("In Progress");
        }
        else {
            eventStatusView.setText("Confirmed");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventClicked(event);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    private List<Event> getEventListByIndex(int index) {
        int count = 0;
        for (Map.Entry<String, List<Event>> entry : expandableListData.entrySet()) {
            if (count == index) {
                return entry.getValue();
            }
            ++count;
        }

        // If this runs, no bueno
        return null;
    }

    public interface Listener {
        void onEventClicked(Event event);
    }
}
