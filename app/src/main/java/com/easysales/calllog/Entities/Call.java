package com.easysales.calllog.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drmiller on 05.07.2016.
 */
public class Call extends Entity {
    private String name;
    private String numberFrom;
    private String numberTo;
    private CallType type;
    private Date beginDate;
    private Date endDate;
    private String recordPath;
    private boolean isSynchronized;
    private long duration;

    public Call() {
        this(null, null, null, null, CallType.Incoming, new Date(), new Date(), null, false);
    }

    public Call(Object key, String name, String numberFrom, String numberTo, CallType type, Date beginDate, String recordPath, boolean isSynchronized) {
        this(key, name, numberFrom, numberTo, type, beginDate, beginDate, recordPath, isSynchronized);
    }

    public Call(Object key, String name, String numberFrom, String numberTo, CallType type, Date beginDate, Date endDate, String recordPath, boolean isSynchronized)
    {
        super(key);
        this.name = name;
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
        this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.recordPath = recordPath;
        this.isSynchronized = isSynchronized;
        this.duration = endDate.getTime()-beginDate.getTime();
    }

    public Call(Parcel parcel)
    {
        super(parcel.readInt());
        String[] stringData = new String[4];
        long[] longData = new long[2];
        int[] intData = new int[2];

        parcel.readStringArray(stringData);
        parcel.readLongArray(longData);
        parcel.readIntArray(intData);

        name = stringData[0];
        numberFrom = stringData[1];
        numberTo = stringData[2];
        type = CallType.valueOf(intData[1]);
        beginDate = new Date(longData[0]);
        endDate = new Date(longData[1]);
        recordPath = stringData[3];
        this.isSynchronized = parcel.readByte() != 0;
        this.duration = endDate.getTime()-beginDate.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberFrom() {
        return numberFrom;
    }

    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom;
    }

    public String getNumberTo() {
        return numberTo;
    }

    public void setNumberTo(String numberTo) {
        this.numberTo = numberTo;
    }

    public CallType getType() {
        return type;
    }

    public void setType(CallType type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public boolean getIsSynchronized() {
        return isSynchronized;
    }

    public void setIsSynchronized(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy mm:ss");
        if(type == CallType.Incoming)
            return String.format("%1$s: %2$s call from %3$s [%4$s] (%5$s sec)", dateFormat.format(beginDate), type, name, numberFrom, (duration/1000));
        else if(type == CallType.Outgoing)
            return String.format("%1$s: %2$s call to %3$s [%4$s] (%5$s sec)", dateFormat.format(beginDate), type, name, numberTo, (duration/1000));
        else
            return String.format("%1$s: %2$s call from %3$s [%4$s]", dateFormat.format(beginDate), type, name, numberFrom);
    }

    public static final Parcelable.Creator<Call> CREATOR = new Parcelable.Creator<Call>() {
        public Call createFromParcel(Parcel in) {
            return new Call(in);
        }

        public Call[] newArray(int size) {
            return new Call[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Object key = getKey();
        int intKey = 0;
        if(key != null) intKey = (int)key;
        dest.writeInt(intKey);
        dest.writeStringArray(new String[]{name, numberFrom, numberTo, recordPath});
        dest.writeLongArray(new long[]{beginDate.getTime(), endDate.getTime()});
        dest.writeIntArray(new int[] { intKey, type.getValue() });
        dest.writeByte((byte)(isSynchronized ? 1 : 0));
    }

}
