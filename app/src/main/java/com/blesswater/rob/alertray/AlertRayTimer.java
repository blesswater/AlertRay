package com.blesswater.rob.alertray;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;


public class AlertRayTimer implements Runnable {
    private static final String TAG = "SmsBroadcastReceiver";
    private Context context;

    public AlertRayTimer( Context context ){
        this.context = context;
    }

        @Override
    public void run() {
        Vibrator vibrator;
        Log.d(TAG, "TIMER PROCESSED");
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
    }
}
