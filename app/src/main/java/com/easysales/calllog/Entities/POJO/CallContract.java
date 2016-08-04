package com.easysales.calllog.Entities.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by drmiller on 11.07.2016.
 */
public class CallContract extends ContractBase {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("NumberFrom")
    @Expose
    private String numberFrom;
    @SerializedName("NumberTo")
    @Expose
    private String numberTo;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("BeginDate")
    @Expose
    private Long beginDate;
    @SerializedName("EndDate")
    @Expose
    private Long endDate;
    @SerializedName("RecordPath")
    @Expose
    private String recordPath;
    @SerializedName("IsSynchronized")
    @Expose
    private Integer isSynchronized;

    private long duration;

    /*
    public CallContract()
    {
        name = "";
        numberFrom = "";
        numberTo = "";
        type = 1;
        beginDate = 0;
        endDate = 0;
        recordPath = "";
        duration = 0;
    }
    /**/

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public long getDuration() {
        return duration;
    }

    public Integer getIsSynchronized() {
        return isSynchronized;
    }

    public void setIsSynchronized(Integer isSynchronized) {
        this.isSynchronized = isSynchronized;
    }

    /*
    @Override
    public String toString() {
        return ContractConverter.ToEntity(this).toString();
    }
    /**/
}
