package com.blesswater.rob.alertray;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    private Handler timerHandler = new Handler();
    private AlertRayTimer timerTask;

    private int startAlarmSec = 5;
    private int addAlarmSec = 2;
    private int maxAlarmSec = 30;

    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator;
        long vibratePattern[] = {0, 100, 200, 300, 400};
        int trigCnt = 0;
        int alarmSec;

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsBody += smsMessage.getMessageBody();
                }
            }

            if( (trigCnt = this.getAlarmLen( '!', smsBody )) > 0 ) {
                Log.d(TAG, "Sms with condition detected");
                vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate( vibratePattern, 0);
                Toast.makeText(context, "Alert Message:\nFrom: " + smsSender + "\nMessage: " + smsBody, Toast.LENGTH_LONG).show();
                this.timerTask = new AlertRayTimer( context );
                alarmSec = (this.startAlarmSec + (trigCnt - 1) * this.addAlarmSec);
                if( alarmSec > this.maxAlarmSec ) {
                    alarmSec = this.maxAlarmSec;
                }
                this.timerHandler.postDelayed( this.timerTask, alarmSec * 1000 );
            }
            Log.d(TAG, "Alert Message detected From " + smsSender + " Message: " + smsBody);
        }
    }

    private int getAlarmLen( char triggerChar, String body ) {
        int cnt;

        for( cnt = 0; (cnt < body.length()) && (body.charAt(cnt) == triggerChar); cnt++);

        return cnt;
    }


}
