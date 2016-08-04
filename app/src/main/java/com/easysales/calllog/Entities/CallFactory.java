package com.easysales.calllog.Entities;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by drmiller on 06.07.2016.
 */
public class CallFactory implements IEntityFactory<Call> {

    public static class FieldNames
    {
        public static final String Key = "_id";
        public static final String ContactName = "Name";
        public static final String NumberFrom = "NumberFrom";
        public static final String NumberTo = "NumberTo";
        public static final String Type = "Type";
        public static final String BeginDate = "BeginDate";
        public static final String EndDate = "EndDate";
        public static final String RecordPath = "RecordPath";
        public static final String IsSynchronized = "IsSynchronized";
    }

    @Override
    public Call BuildEntity(Cursor cursor) {

        Call call = new Call(
                cursor.getInt(cursor.getColumnIndex(FieldNames.Key)),
                cursor.getString(cursor.getColumnIndex(FieldNames.ContactName)),
                cursor.getString(cursor.getColumnIndex(FieldNames.NumberFrom)),
                cursor.getString(cursor.getColumnIndex(FieldNames.NumberTo)),
                CallType.valueOf(cursor.getInt(cursor.getColumnIndex(FieldNames.Type))),
                new Date(cursor.getLong(cursor.getColumnIndex(FieldNames.BeginDate))),
                new Date(cursor.getLong(cursor.getColumnIndex(FieldNames.EndDate))),
                cursor.getString(cursor.getColumnIndex(FieldNames.RecordPath)),
                cursor.getInt(cursor.getColumnIndex(FieldNames.IsSynchronized)) != 0);

        return call;
    }
}
