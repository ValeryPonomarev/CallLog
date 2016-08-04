package com.easysales.calllog.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by drmiller on 26.07.2016.
 */
public enum CallState {
    IncomingReceived(1), IncomingAnswered(2), IncomingEnded(3), OutgoingStarted(4), OutgoindEnded(5), Missed(6), Unknown(7);

    private int value;
    private static Map map = new HashMap<>();

    private CallState(int value)
    {
        this.value = value;
    }

    static {
        for(CallState callState : CallState.values())
        {
            map.put(callState.value, callState);
        }
    }

    public static CallState valueOf(int callState)
    {
        if(map.containsKey(callState)) {
            return (CallState) map.get(callState);
        }
        else
            return (CallState) map.get(1);
    }

    public int getValue()
    {
        return value;
    }
}
