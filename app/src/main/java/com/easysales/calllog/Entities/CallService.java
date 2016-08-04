package com.easysales.calllog.Entities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by drmiller on 26.07.2016.
 */
public final class CallService {
    public static Call mergeCall(Call call1, Call call2) {
        if(call1.getNumberFrom() == null || call1.getNumberFrom() == "") call1.setNumberFrom(call2.getNumberFrom());
        if(call1.getNumberTo() == null || call1.getNumberTo() == "") call1.setNumberTo(call2.getNumberTo());
        if(call1.getBeginDate() == null) call1.setBeginDate(call2.getBeginDate());
        if(call1.getEndDate().getTime() < call2.getEndDate().getTime()) call1.setEndDate(call2.getEndDate());
        return call1;
    }

    public static String getContactName(Call call, Context context)
    {
        String number = null;
        if(call.getType() == CallType.Outgoing){
            number = call.getNumberTo();
        } else {
            number = call.getNumberFrom();
        }
        String contactName = null;
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if(cursor == null) {
            return null;
        }
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        return contactName;
    }
}
