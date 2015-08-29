package edu.gatech.coc_hackathon_2015_09;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Override;
import java.util.HashMap;

public class TripActivity extends Activity {

    TextView mTitleTextView;
    Button mEndTripButton;
    static boolean mCurrentlyDriving;
    static boolean mFinishedDriving;
    static double mTirePressure;
    static HashMap<Integer, Double> mSpeedMap = new HashMap<>();
    static String mVehicleStatus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_layout);

        mTitleTextView = (TextView) findViewById(R.id.current_trip);
        mEndTripButton = (Button) findViewById(R.id.button_end_trip);

        //Engine Status code
        setVehicleStatus();

        //tire pressure code
        setTirePressure();

        //start engine
        startEngine();

        //start TripService

        mEndTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stop engine
                mCurrentlyDriving = false;
                mFinishedDriving = true;
                Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(dashboardIntent);
            }
        });

        //Start service


    }

    @Override
    public void onResume() {
        super.onResume();
        while (mCurrentlyDriving) {
            //ApiRequests
        }
    }

    protected void setTirePressure() {
        double tirePressure = 0.0;
        //Use API for tire pressure and save value into tirePressure;


        mTirePressure = tirePressure;
    }

    protected void setVehicleStatus() {
        mVehicleStatus= "Green";
        //code for engine light, set result to 'status'

    }

    protected void startEngine() {
        //code to start engine
    }



    protected static Integer getHardBrakes() {
        return 4323;
    }

    protected static Double getTirePressure() {
        return mTirePressure;
    }



    protected static String getAverageSpeed() {
        double sum = 0.0;
        for (int i = 0; i < mSpeedMap.size(); i++) {
            sum += mSpeedMap.get(i);
        }

        return (sum / mSpeedMap.size()) + " mph";
    }

    protected static String getStatus() {
        return mVehicleStatus;
    }

    protected static boolean finishedDriving() {
        return mFinishedDriving;
    }

    protected void  getCurrentSpeed() {
        double speed = 0.0;
        int counter = 0;
        //code to get current speed, save result in 'speed'
        mSpeedMap.put(0, speed);
    }


}