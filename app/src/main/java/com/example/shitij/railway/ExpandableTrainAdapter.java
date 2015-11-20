package com.example.shitij.railway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.shitij.railway.customviews.TextViewBoldFont;
import com.example.shitij.railway.customviews.TextViewRegularFont;
import com.example.shitij.railway.model.TrainDetails;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shitij on 19/11/15.
 */
public class ExpandableTrainAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<TrainDetails> trainDetails;

    public ExpandableTrainAdapter(Context context, List<TrainDetails> trainDetails){
        this.context = context;
        this.trainDetails = trainDetails;
    }

    @Override
    public int getGroupCount() {
        return trainDetails.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }
        groupViewHolder = (GroupViewHolder)convertView.getTag();
        groupViewHolder.trainNumber.setText(trainDetails.get(groupPosition).getNumber());
        groupViewHolder.trainName.setText(trainDetails.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }
        childViewHolder = (ChildViewHolder)convertView.getTag();
        childViewHolder.arrivalTime.setText(trainDetails.get(groupPosition).getDest_arrival_time());
        childViewHolder.departureTime.setText(trainDetails.get(groupPosition).getSrc_departure_time());
        childViewHolder.destinationName.setText(trainDetails.get(groupPosition).getTo().getName());
        childViewHolder.sourceName.setText(trainDetails.get(groupPosition).getFrom().getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    class ChildViewHolder{
        @Bind(R.id.departure_time) TextViewRegularFont departureTime;
        @Bind(R.id.arrival_time) TextViewRegularFont arrivalTime;
        @Bind(R.id.source_name) TextViewRegularFont sourceName;
        @Bind(R.id.destination_name) TextViewRegularFont destinationName;

        public ChildViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }

    class GroupViewHolder{
        @Bind(R.id.train_name) TextViewRegularFont trainName;
        @Bind(R.id.train_number) TextViewBoldFont trainNumber;

        public GroupViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }
}
