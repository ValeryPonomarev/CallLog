package com.easysales.calllog.Media;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by drmiller on 07.07.2016.
 */
public final class MyMediaRecoder {

    private static String TAG_LOG = "MyMediaRecorder";

    private MediaRecorder callRecorder;
    private String pathOutputFile = "";

    public MyMediaRecoder()
    {
    }

    private void SetupRecorder(String recordFilePath)
    {
        releaseRecorder();
        callRecorder = new MediaRecorder();
        callRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        callRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        callRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        File recordFile = new File(recordFilePath);
        if(recordFile.exists())
        {
            recordFile.delete();
        }

        Log.d(TAG_LOG, "File output: " + recordFilePath);
        callRecorder.setOutputFile(recordFilePath);
        callRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                Log.d(TAG_LOG, String.format("Error in MediaRecorder: what(%1$d) extra(%2$d)", what, extra));
                releaseRecorder();
            }
        });
        pathOutputFile = recordFilePath;
    }


    public void StartRecord(String recordFilePath)
    {
        Log.d(TAG_LOG, "Start record");
        SetupRecorder(recordFilePath);
        try {
            callRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            callRecorder.start();
        } catch(IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public String StopRecord()
    {
        Log.d(TAG_LOG, "Stop record");
        if(callRecorder != null) {
            callRecorder.stop();
            releaseRecorder();
        }
        return pathOutputFile;
    }

    public void Pause()
    {
        Log.d(TAG_LOG, "Pause");
        try {
            if(callRecorder != null) {
                callRecorder.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Resume()
    {
        Log.d(TAG_LOG, "Resume");
        try {
            if(callRecorder != null) {
                callRecorder.start();
            }
        } catch(IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void releaseRecorder() {
        Log.d(TAG_LOG, "Release");
        if (callRecorder != null) {
            callRecorder.release();
            callRecorder = null;
        }
    }
}
