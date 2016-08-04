package com.easysales.calllog.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by drmiller on 05.07.2016.
 */
public enum CallType {
    Incoming(1), Outgoing(2), Missed(3);

    private int value;
    private static Map map = new HashMap<>();

    private CallType(int value)
    {
        this.value = value;
    }

    static {
        for(CallType callType : CallType.values())
        {
            map.put(callType.value, callType);
        }
    }

    public static CallType valueOf(int callType)
    {
        if(map.containsKey(callType)) {
            return (CallType) map.get(callType);
        }
        else
            return (CallType) map.get(1);
    }

    public int getValue()
    {
        return value;
    }
}
