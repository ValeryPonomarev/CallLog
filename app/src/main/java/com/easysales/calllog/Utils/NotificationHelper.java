package com.easysales.calllog.Utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.easysales.calllog.R;

import java.util.Random;

/**
 * Created by drmiller on 05.07.2016.
 */
public final class NotificationHelper {
    public static void ShowNotification(Context context, int notify_id, String title, String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);
        NotificationManager notifyMgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(notify_id, builder.build());
    }

    public static void ShowNotification(Context context, String title, String text)
    {
        int min = 800;
        int max = 900;

        Random rnd = new Random();
        int notify_id = rnd.nextInt(max - min + 1) + min;
        ShowNotification(context, notify_id, title, text);
    }
}
