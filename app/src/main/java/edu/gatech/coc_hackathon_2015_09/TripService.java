package edu.gatech.coc_hackathon_2015_09;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Clayton on 8/29/2015.
 */
public class TripService extends IntentService {

    boolean mCurrentlyDriving;

    private static final String TAG = "TripService";

    public TripService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (mCurrentlyDriving) {
            //Api requests for drive
        }
    }
}
