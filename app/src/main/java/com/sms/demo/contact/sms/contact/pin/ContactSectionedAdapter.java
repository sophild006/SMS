package com.sms.demo.contact.sms.contact.pin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sms.demo.R;
import com.sms.demo.contact.sms.contact.ParentContactData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactSectionedAdapter extends SectionedBaseAdapter {


    public List<ParentContactData> parentContactDatas = new ArrayList<>();
    public List<String> contact = new ArrayList<>();

    public ContactSectionedAdapter(List<ParentContactData> parentContactDatas) {
        this.parentContactDatas = parentContactDatas;
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return parentContactDatas.size();
    }

    @Override
    public int getCountForSection(int section) {
        return parentContactDatas.get(section).getData().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
//        RelativeLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.list_item, parent,false);
        } else {
            convertView = (RelativeLayout) convertView;
        }
        ((TextView) convertView.findViewById(R.id.tv_name)).setText(parentContactDatas.get(section).getData().get(position).getName());
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(parentContactDatas.get(section).getLetter());
        return layout;
    }

}
