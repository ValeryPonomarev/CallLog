package com.easysales.calllog.Data;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.CallFactory;
import com.easysales.calllog.Entities.EntityFactoryBuilder;
import com.easysales.calllog.MyApplication;
import com.easysales.calllog.R;
import com.easysales.calllog.Utils.DataHelper;
import com.easysales.calllog.Utils.SettingsHelper;

import java.util.Calendar;
import java.util.List;

/**
 * Created by drmiller on 07.07.2016.
 */
public class CallAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    private static final int ViewTypeItem = 0;
    private static final int ViewTypeGroup = 1;

    public CallAdapter(Context context, Cursor calls) {
        super(context, calls);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return ViewTypeItem;
        if(isNewGroup(getCursor(), position)) {
            return ViewTypeGroup;
        }

        return ViewTypeItem;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
//        if(convertView == null){
//            view = new View(context);
//            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.call_grid_item, parent, false);
//        }
//        else
//        {
//            view = (View) convertView;
//        }
//
//        ImageView imageView = (ImageView)view.findViewById(R.id.callGridItemImage);
//        TextView titleView = (TextView)view.findViewById(R.id.callGridItemTextTitle);
//        TextView phoneNumberView = (TextView)view.findViewById(R.id.callGridItemPhoneNumber);
//        TextView descriptionView = (TextView)view.findViewById(R.id.callGridItemTextDescription);
//        ImageView imageSyncView = (ImageView) view.findViewById(R.id.callGridItemSyncImage);
//
//        Call call = getItem(position);
//        Call previousCall = null;
//        if(position > 0){
//            previousCall = getItem(position - 1);
//        }
//
//        String name = call.getName();
//        String number = "";
//        String description = "";
//
//        switch (call.getType())
//        {
//            case Incoming:
//                imageView.setImageResource(R.drawable.incoming);
//                number = call.getNumberFrom();
//                description = String.format("%1$s (%2$s)", DataHelper.ToDateTimeFormat(call.getBeginDate()), DataHelper.ToTimeFromat(call.getDuration() / 1000));
//                break;
//            case Missed:
//                imageView.setImageResource(R.drawable.missed);
//                number = call.getNumberFrom();
//                description = String.format("%1$s", DataHelper.ToDateTimeFormat(call.getBeginDate()));
//                break;
//            case Outgoing:
//                imageView.setImageResource(R.drawable.outgoind);
//                number = call.getNumberTo();
//                description = String.format("%1$s (%2$s)", DataHelper.ToDateTimeFormat(call.getBeginDate()), DataHelper.ToTimeFromat(call.getDuration() / 1000));
//                break;
//        }
//        if(name == null || name.length() == 0) {
//            name = number;
//        }
//
//        if(previousCall != null && previousCall.getBeginDate().before(call.getBeginDate())) {
//        }
//
//        titleView.setText(name);
//        phoneNumberView.setText(number);
//        descriptionView.setText(description);
//
//        if(SettingsHelper.getIsSynchronized()) {
//            if (call.getIsSynchronized()) {
//                imageSyncView.setImageResource(R.drawable.ok);
//            } else {
//                imageSyncView.setImageResource(R.drawable.cancel);
//            }
//        }
//
//        return view;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view;

        if(getItemViewType(cursor.getPosition())== ViewTypeGroup) {
            view = inflater.inflate(R.layout.call_grid_header_item, parent, false);
        }
        else {
            view = inflater.inflate(R.layout.call_grid_item, parent, false);
        }
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Call call = getCall(cursor, cursor.getPosition());
        ImageView imageView = (ImageView)view.findViewById(R.id.callGridItemImage);
        TextView titleView = (TextView)view.findViewById(R.id.callGridItemTextTitle);
        TextView phoneNumberView = (TextView)view.findViewById(R.id.callGridItemPhoneNumber);
        TextView descriptionView = (TextView)view.findViewById(R.id.callGridItemTextDescription);
        ImageView imageSyncView = (ImageView) view.findViewById(R.id.callGridItemSyncImage);
        TextView headerView = (TextView)view.findViewById(R.id.callGridHeaderItem);
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

        if(headerView != null) {
            headerView.setText(DataHelper.ToDateFormat(call.getBeginDate()));
        }
    }

    @Override
    public Object getItem(int position) {
        return getCall(getCursor(), position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isNewGroup(Cursor cursor, int position)
    {
        Call currentCall = getCall(cursor, position);
        Call previousCall = getCall(cursor, position - 1);

        if (DataHelper.IsDiferentDate(currentCall.getBeginDate(), previousCall.getBeginDate())) {
            return true;
        }

        return false;
    }

    private Call getCall(Cursor cursor, int position)
    {
        int currentPosition = cursor.getPosition();
        cursor.moveToPosition(position);
        Call result = (Call)EntityFactoryBuilder.GetEntityFactory("Call").BuildEntity(cursor);
        cursor.moveToPosition(currentPosition);
        return result;
    }
}
