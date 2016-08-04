package com.easysales.calllog.Utils;

import android.content.Context;
import android.os.Environment;

import com.easysales.calllog.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drmiller on 09.07.2016.
 */
public final class IOHelper {
    private static final String RECORD_FILE_EXTENSION = "3gpp";

    public static boolean FileExists(String path)
    {
        if(path == null || path == "") return false;
        return new File(path).exists();
    }


    public static String GetNewFileName(Context context, Date callDate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmss");
        String catalogName = context.getString(R.string.packageName);
        File catalog = new File(Environment.getExternalStorageDirectory(), catalogName);
        if(!catalog.exists())
        {
            catalog.mkdir();
        }
        return String.format("%1$s/%2$s/callRecord_%3$s.%4$s", Environment.getExternalStorageDirectory(), catalogName, dateFormat.format(callDate), RECORD_FILE_EXTENSION);
    }

}
