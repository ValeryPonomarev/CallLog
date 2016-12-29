package com.easysales.calllog.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by drmiller on 06.07.2016.
 */
public final class DataHelper {
    public static String ToDateTimeFormat(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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

    public static String ToDateFormat(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    /**
    * Возвращает true если даты отличаются друг от друга (не учитывая время)
    */
    public static boolean IsDiferentDate(Date date1, Date date2)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int daysInYear1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar = Calendar.getInstance();
        calendar.setTime(date2);
        int daysInYear2 = calendar.get(Calendar.DAY_OF_YEAR);

        if(daysInYear1 != daysInYear2 || date1.getYear() != date2.getYear()){
            return true;
        }

        return false;
    }
}
