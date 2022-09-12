package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class expandlistadapter extends BaseExpandableListAdapter {
    List<String> heading;
    HashMap<String,List<String>> child;
    Context c; HashMap<String,List<String>> child2;

    public expandlistadapter(List<String> heading, HashMap<String, List<String>> child, HashMap<String, List<String>> child2, Context c) {
        this.heading = heading;
        this.child = child;

        this.child2 = child2;
        this.c = c;
    }

    @Override
    public int getGroupCount() {
        return heading.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(heading.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return heading.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(heading.get(groupPosition)).get(childPosition);

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
        String list= (String) this.getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater l= (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=l.inflate(R.layout.heading,null);

        }
        TextView t=convertView.findViewById(R.id.textView9);
        t.setText(list);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String list= (String) this.getChild(groupPosition, childPosition);
        if(convertView==null){
            LayoutInflater l= (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=l.inflate(R.layout.child,null);

        }
        TextView t=convertView.findViewById(R.id.textView8);
        t.setText(list);
        TextView tt=convertView.findViewById(R.id.textView7);

        tt.setText(child2.get(heading.get(groupPosition)).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
