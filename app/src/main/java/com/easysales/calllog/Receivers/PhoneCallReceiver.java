package com.easysales.calllog.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.easysales.calllog.Entities.CallType;

import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static CallType callType;
    private static String currentNumber;
    private static String firstNumber;
    private static String secondNumber;
    private static String LOG_TAG = "PhoneCallReceiver";


    private static class StringConstants
    {
        private static final String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    }

    public PhoneCallReceiver() {
    }

    protected abstract void onIncomigCallReceived(Context context, String number, Date startCallDate);
    protected abstract void onIncomigCallAnswered(Context context, String number, Date startCallDate);
    protected abstract void onIncomigCallEnded(Context context, String number, Date startCallDate, Date endCallDate);
    protected abstract void onIncomigSecondLineCallReceived(Context context, String number, Date startCallDate);
    protected abstract void onIncomigSecondLineCallMissed(Context context, String secondNumber, Date date);

    protected abstract void onOutgoingCallStarted(Context context, String number, Date startCallDate);
    protected abstract void onOutgoingCallEnded(Context context, String number, Date startCallDate, Date endCallDate);

    protected abstract void onMissedCall(Context context, String number, Date startCallDate);

    @Override
    public void onReceive(Context context, Intent intent) {

        /**/
        //Log.d("PhoneCallReceiver", "OnReceive");
        Log.d(LOG_TAG, "Action: " + intent.getAction());
        Log.d(LOG_TAG, "Phone state: " +  intent.getExtras().getString(TelephonyManager.EXTRA_STATE));
        Log.d(LOG_TAG, "Phone number 1: " + intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
        Log.d(LOG_TAG, "Phone number 2: " + intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
        /**/
        setCurrentNumber("unknown");
        if(intent.getAction().equals(StringConstants.ACTION_OUTGOING_CALL))
        {
            String num = intent.getExtras().getString(Intent.EXTRA_PHONE_NUMBER);
            setCurrentNumber(num);
            onCallStateChanged(context, -1, currentNumber);
        }
        else
        {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            setCurrentNumber(intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER));

            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, state, currentNumber);
        }
    }

    public void onCallStateChanged(Context context, int state, String number){
        if(lastState == state){
            return;
        }

        switch (state)
        {
            case TelephonyManager.CALL_STATE_RINGING:
                if(lastState == TelephonyManager.CALL_STATE_OFFHOOK) {
                    secondNumber = number;
                    callType = CallType.Incoming;
                    callStartTime = new Date();
                    onIncomigSecondLineCallReceived(context, secondNumber, callStartTime);
                }
                else {
                    firstNumber = number;
                    callType = CallType.Incoming;
                    callStartTime = new Date();
                    onIncomigCallReceived(context, firstNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if(lastState != TelephonyManager.CALL_STATE_RINGING)
                {
                    callType = CallType.Outgoing;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, firstNumber, callStartTime);
                }
                else
                {
                    if(secondNumber != null && secondNumber.equals(number)) {
                        onIncomigSecondLineCallMissed(context, secondNumber, new Date());
                        secondNumber = null;
                    }
                    else {
                        callType = CallType.Incoming;
                        callStartTime = new Date();
                        onIncomigCallAnswered(context, firstNumber, callStartTime);
                    }
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //End call
                if(lastState == TelephonyManager.CALL_STATE_RINGING) {
                    onMissedCall(context, number, callStartTime);
                }
                else if(callType == CallType.Incoming) {
                    onIncomigCallEnded(context, firstNumber, callStartTime, new Date());
                    firstNumber = null;
                }
                else {
                    onOutgoingCallEnded(context, firstNumber, callStartTime, new Date());
                    firstNumber = null;
                }
                break;
            case -1://New outgoing call
                firstNumber = number;
                break;
        }
        lastState = state;
    }

    private void setCurrentNumber(String newNumber) {
        if(newNumber != null && newNumber != "") {
            currentNumber = newNumber;
        }
    }
}
