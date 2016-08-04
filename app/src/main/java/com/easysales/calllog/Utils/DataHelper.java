package com.easysales.calllog.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drmiller on 06.07.2016.
 */
public final class DataHelper {
    public static String ToDateTimeFormat(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
        return dateFormat.format(date);
    }

    public static String ToTimeFromat(long secondValue)
    {
        String result = "";
        int hours, minutes, seconds;

        hours = (int) secondValue / 3600;
        int remainder = (int) secondValue - hours * 3600;
        minutes = remainder / 60;
        remainder = remainder - minutes * 60;
        seconds = remainder;

        if(hours > 0) {
            return String.format("%1$d ч %2$d мин %3$d сек", hours, minutes, seconds);
        }
        else if(minutes > 0){
            return String.format("%1$d мин %2$d сек", minutes, seconds);
        }
        else if(seconds > 0){
            return String.format("%1$d сек", seconds);
        }
        else return "0 сек";
    }
}
