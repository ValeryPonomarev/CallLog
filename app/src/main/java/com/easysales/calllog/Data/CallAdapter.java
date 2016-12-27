package com.easysales.calllog.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.R;
import com.easysales.calllog.Utils.DataHelper;
import com.easysales.calllog.Utils.SettingsHelper;

import java.util.List;

/**
 * Created by drmiller on 07.07.2016.
 */
public class CallAdapter extends BaseAdapter {

    private Context context;
    private List<Call> callList;

    public CallAdapter(Context context, List<Call> callList) {
        this.context = context;
        this.callList = callList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = new View(context);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.call_grid_item, parent, false);
        }
        else
        {
            view = (View) convertView;
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.callGridItemImage);
        TextView titleView = (TextView)view.findViewById(R.id.callGridItemTextTitle);
        TextView phoneNumberView = (TextView)view.findViewById(R.id.callGridItemPhoneNumber);
        TextView descriptionView = (TextView)view.findViewById(R.id.callGridItemTextDescription);
        ImageView imageSyncView = (ImageView) view.findViewById(R.id.callGridItemSyncImage);

        Call call = getItem(position);

        String name = call.getName();
        String number = "";
        String description = "";

        switch (call.getType())
        {
            case Incoming:
                imageView.setImageResource(R.drawable.incoming);
                number = call.getNumberFrom();
                description = String.format("%1$s (%2$s)", DataHelper.ToDateTimeFormat(call.getBeginDate()), DataHelper.ToTimeFromat(call.getDuration() / 1000));
                break;
            case Missed:
                imageView.setImageResource(R.drawable.missed);
                number = call.getNumberFrom();
                description = String.format("%1$s", DataHelper.ToDateTimeFormat(call.getBeginDate()));
                break;
            case Outgoing:
                imageView.setImageResource(R.drawable.outgoind);
                number = call.getNumberTo();
                description = String.format("%1$s (%2$s)", DataHelper.ToDateTimeFormat(call.getBeginDate()), DataHelper.ToTimeFromat(call.getDuration() / 1000));
                break;
        }
        if(name == null || name.length() == 0) {
            name = number;
        }

        titleView.setText(name);
        phoneNumberView.setText(number);
        descriptionView.setText(description);

        if(SettingsHelper.getIsSynchronized()) {
            if (call.getIsSynchronized()) {
                imageSyncView.setImageResource(R.drawable.ok);
            } else {
                imageSyncView.setImageResource(R.drawable.cancel);
            }
        }

        return view;
    }

    @Override
    public int getCount() {
        return callList.size();
    }

    @Override
    public Call getItem(int position) {
        return callList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
