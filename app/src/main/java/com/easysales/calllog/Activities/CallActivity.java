package com.easysales.calllog.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.CallType;
import com.easysales.calllog.R;
import com.easysales.calllog.Utils.DataHelper;
import com.easysales.calllog.Utils.IOHelper;

import java.io.File;
import java.io.IOException;

public class CallActivity extends Activity {

    private Call call;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        TextView textViewNumberFrom = (TextView) findViewById(R.id.textViewCallFrom);
        TextView textViewNumberTo = (TextView) findViewById(R.id.textViewCallTo);
        TextView textViewNumberState = (TextView) findViewById(R.id.textViewCallState);
        TextView textViewNumberDuration = (TextView) findViewById(R.id.textViewCallDuration);
        TextView textViewNumberBegin = (TextView) findViewById(R.id.textViewCallBegin);
        TextView textViewNumberEnd = (TextView) findViewById(R.id.textViewCallEnd);
        TextView textViewNumberRecordFile = (TextView) findViewById(R.id.textViewCallRecordFile);
        Button buttonPlayFileRecord = (Button) findViewById(R.id.btnCallPlayFileRecord);
        Button buttonStopFileRecord = (Button) findViewById(R.id.btnCallStopFileRecord);
        Button buttonOpenFileRecord = (Button) findViewById(R.id.btnCallOpenFileRecord);
        Button buttonCallInvoke = (Button) findViewById(R.id.btnCallInvoke);

        call = (Call) getIntent().getParcelableExtra("Call");
        if (call != null) {
            textViewNumberFrom.setText(getString(R.string.From) + call.getNumberFrom());
            textViewNumberTo.setText(getString(R.string.To) + call.getNumberTo());
            if (call.getType() == CallType.Incoming) {
                textViewNumberState.setText(getString(R.string.Incoming));
            } else if (call.getType() == CallType.Outgoing) {
                textViewNumberState.setText(getString(R.string.Outgoing));
            } else if (call.getType() == CallType.Missed) {
                textViewNumberState.setText(getString(R.string.Missing));
            }

            textViewNumberDuration.setText(getString(R.string.Duration) + DataHelper.ToTimeFromat(call.getDuration() / 1000));
            textViewNumberBegin.setText(getString(R.string.BeginCall) + DataHelper.ToDateTimeFormat(call.getBeginDate()));
            textViewNumberEnd.setText(getString(R.string.EndCall) + DataHelper.ToDateTimeFormat(call.getEndDate()));
            textViewNumberRecordFile.setText(getString(R.string.CallRecordFile) + call.getRecordPath());
        }

        buttonPlayFileRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayCallRecord();
            }
        });

        buttonStopFileRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopCallRecord();
            }
        });

        buttonOpenFileRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCallRecord();
            }
        });

        buttonCallInvoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallInvoke();
            }
        });
    }

    private void PlayCallRecord() {
        Log.d("MadiaPlayer", "Play file:" + call.getRecordPath());
        if (!IOHelper.FileExists(call.getRecordPath())) return;

        MPRelease();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(call.getRecordPath());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void MPRelease() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void StopCallRecord() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            MPRelease();
        }
    }

    private void OpenCallRecord() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(call.getRecordPath());
        if (!file.exists()) return;
        intent.setDataAndType(Uri.fromFile(file), "audio/*");
        startActivity(intent);
    }

    private void CallInvoke() {
        String number = "";
        if (call.getType() == CallType.Incoming)
            number = call.getNumberFrom();
        else if (call.getType() == CallType.Missed)
            number = call.getNumberFrom();
        else if (call.getType() == CallType.Outgoing)
            number = call.getNumberTo();

        String uri = "tel:" + number;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startActivity(intent);
    }

}
