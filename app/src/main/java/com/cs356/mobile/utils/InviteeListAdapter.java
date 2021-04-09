package com.cs356.mobile.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs356.mobile.R;

import java.util.List;

public class InviteeListAdapter extends BaseExpandableListAdapter {
    private ListListener listener;
    private Context context;
    private String expandableListTitle;
    private List<String> invitees;

    public InviteeListAdapter(Context context, String expandableListTitle, List<String> invitees, ListListener listener) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.invitees = invitees;
        this.listener = listener;
    }


    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return invitees.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return invitees;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return invitees.get(childPosition);
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
        TextView inviteeLabelView = convertView.findViewById(R.id.list_header);

        inviteeLabelView.setText(expandableListTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.invitee_list_item, null);
        }

        if (listener == null) {
            Button uninviteButton = convertView.findViewById(R.id.invite_button);
            uninviteButton.setVisibility(View.INVISIBLE);
            uninviteButton.setMinimumWidth(0);
            uninviteButton.setMinHeight(0);
            uninviteButton.setWidth(0);
            uninviteButton.setHeight(0);

            TextView inviteeNameView = convertView.findViewById(R.id.invitee_name);

            String invitee = invitees.get(childPosition);
            inviteeNameView.setText(invitee);
            return convertView;
        }

        Button uninviteButton = convertView.findViewById(R.id.invite_button);
        uninviteButton.setText("Un-invite");
        TextView inviteeNameView = convertView.findViewById(R.id.invitee_name);

        String invitee = invitees.get(childPosition);
        inviteeNameView.setText(invitee);

        uninviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.uninvitePerson(invitee);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
