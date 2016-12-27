package com.easysales.calllog.Receivers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.easysales.calllog.Entities.CallState;
import com.easysales.calllog.Services.CallProcessService;
import com.easysales.calllog.Utils.SettingsHelper;

import java.util.Date;

public class MyPhoneCallReceiver extends PhoneCallReceiver {

    public static String TAG_LOG = "PhoneCallReceiver";

    public MyPhoneCallReceiver() {
    }

    @Override
    protected void onIncomigCallReceived(Context context, String number, Date startCallDate) {
        Log.d(TAG_LOG, "onIncomigCallReceived:" + number);
        startCallProcess(context, CallState.IncomingReceived, number, SettingsHelper.getMyPhoneNumber(), true);
    }

    @Override
    protected void onIncomigCallAnswered(Context context, String number, Date startCallDate) {
        Log.d(TAG_LOG, "onIncomigCallAnswered:" + number);
        startCallProcess(context, CallState.IncomingAnswered, number, SettingsHelper.getMyPhoneNumber(), true);
    }

    @Override
    protected void onIncomigCallEnded(Context context, String number, Date startCallDate, Date endCallDate) {
        Log.d(TAG_LOG, "onIncomigCallEnded:" + number);
        startCallProcess(context, CallState.IncomingEnded, number, SettingsHelper.getMyPhoneNumber(), true);
    }

    @Override
    protected void onIncomigSecondLineCallReceived(Context context, String number, Date startCallDate) {
        Log.d(TAG_LOG, "onIncomigSecondLineCallReceived:" + number);
        startCallProcess(context, CallState.IncomingReceived, number, SettingsHelper.getMyPhoneNumber(), false);
    }

    @Override
    protected void onIncomigSecondLineCallMissed(Context context, String number, Date date) {
        Log.d(TAG_LOG, "onIncomigSecondLineCallMissed:" + number);
        startCallProcess(context, CallState.Missed, number, SettingsHelper.getMyPhoneNumber(), false);
    }

    @Override
    protected void onOutgoingCallStarted(Context context, String number, Date startCallDate) {
        Log.d("PhoneCallReceiver", "onOutgoingCallStarted:" + number);
        startCallProcess(context, CallState.OutgoingStarted, SettingsHelper.getMyPhoneNumber(), number, true);
    }

    @Override
    protected void onOutgoingCallEnded(Context context, String number, Date startCallDate, Date endCallDate) {
        Log.d("PhoneCallReceiver", "onOutgoingCallEnded:" + number);
        startCallProcess(context, CallState.OutgoindEnded, SettingsHelper.getMyPhoneNumber(), number, true);
    }

    @Override
    protected void onMissedCall(Context context, String number, Date startCallDate) {
        Log.d("PhoneCallReceiver", "onMissedCall:" + number);
        startCallProcess(context, CallState.Missed, number, SettingsHelper.getMyPhoneNumber(), true);
    }

    private void startCallProcess(Context context, CallState state, String numberFrom, String numberTo, boolean isFirstLine) {
        if(SettingsHelper.getIsCallRecording()) {
            Intent intent = new Intent(context, CallProcessService.class);
            intent.putExtra("State", state.getValue());
            intent.putExtra("NumberFrom", numberFrom);
            intent.putExtra("NumberTo", numberTo);
            intent.putExtra("IsFirstLine", isFirstLine);
            context.startService(intent);
        }
    }
}
