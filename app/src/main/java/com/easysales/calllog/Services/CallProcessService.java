package com.easysales.calllog.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.easysales.calllog.R;
import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.CallService;
import com.easysales.calllog.Entities.CallState;
import com.easysales.calllog.Entities.CallType;
import com.easysales.calllog.Media.MyMediaRecoder;
import com.easysales.calllog.Repository.Call.CallRepository;
import com.easysales.calllog.Repository.RepositoryFactory;
import com.easysales.calllog.Utils.IOHelper;

import java.util.Date;

public class CallProcessService extends Service {
    private MyMediaRecoder mediaRecoder;
    private CallRepository callRepository;

    private static String LOG_TAG = "CallProcessService";

    private Call currentCall;
    private Call secondLineCall;

    public CallProcessService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaRecoder = new MyMediaRecoder();
        callRepository = RepositoryFactory.GetRepository(this, RepositoryFactory.RepositoryNames.CALL_REPOSITORY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "Start service");

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(getString(R.string.callVoiceRecording))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(1, notification);
        String numberFrom = intent.getStringExtra("NumberFrom");
        String numberTo = intent.getStringExtra("NumberTo");
        CallState state = CallState.valueOf(intent.getIntExtra("State", 7));
        boolean isFirstLine = intent.getBooleanExtra("IsFirstLine", true);
        Log.d("PhoneCallReceiver", String.format("state: $1%s,from: $2%s, to:$3%s", state.name(), numberFrom, numberTo));

        if (state == CallState.IncomingReceived) {
            if(isFirstLine) {
                currentCall = new Call();
                mergeNumbers(currentCall, numberFrom, numberTo);
                currentCall.setBeginDate(new Date());
                currentCall.setType(CallType.Incoming);
            }
            else {
                secondLineCall = new Call();
                mergeNumbers(secondLineCall, numberFrom, numberTo);
                secondLineCall.setBeginDate(new Date());
                currentCall.setType(CallType.Incoming);
            }
        } else if (state == CallState.IncomingAnswered) {
            startProcess();
        } else if (state == CallState.IncomingEnded) {
            endProcess();
        } else if (state == CallState.OutgoingStarted) {
            currentCall = new Call();
            mergeNumbers(currentCall, numberFrom, numberTo);
            currentCall.setType(CallType.Outgoing);
            startProcess();
        } else if (state == CallState.OutgoindEnded) {
            endProcess();
        } else if (state == CallState.Missed) {
            if(isFirstLine) {
                currentCall.setType(CallType.Missed);
                currentCall.setBeginDate(new Date());
                endProcess();
            }
            else {
                if(secondLineCall != null) {
                    secondLineCall.setEndDate(new Date());
                    secondLineCall.setType(CallType.Missed);
                    callRepository.Add(secondLineCall);
                    secondLineCall = null;
                }
            }
        } else {
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Destroy service");
        mediaRecoder.StopRecord();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startProcess(){
        currentCall.setBeginDate(new Date());
        mediaRecoder.StartRecord(IOHelper.GetNewFileName(this, new Date()));
    }

    private void endProcess() {
        currentCall.setEndDate(new Date());
        String recordPath =  mediaRecoder.StopRecord();
        currentCall.setRecordPath(recordPath);
        currentCall.setName(CallService.getContactName(currentCall, getApplicationContext()));
        callRepository.Add(currentCall);
        currentCall = null;
        stopSelf();
    }

    private void mergeNumbers(Call call, String numberFrom, String numberTo) {
        if(numberFrom != null) call.setNumberFrom(numberFrom);
        if(numberTo != null) call.setNumberTo(numberTo);
    }
}
