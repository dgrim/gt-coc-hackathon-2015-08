package edu.gatech.coc_hackathon_2015_09;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Override;

public class TripActivity extends Activity {

    TextView mTitleTextView;
    Button mEndTripButton;
    boolean mCurrentlyDriving;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_layout);

        mTitleTextView = (TextView) findViewById(R.id.current_trip);
        mEndTripButton = (Button) findViewById(R.id.button_end_trip);

        //Malfunction light code

        //tire pressure code

        //start engine

        //start TripService

        mEndTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stop engine
                mCurrentlyDriving = false;
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

    protected static Integer getHardBrakes() {
        return 4323;
    }

    protected static Integer getTirePressure() {
        return 0;
    }

    protected static String getAverageSpeed() {
        return 55 + " mph";
    }

    protected static String getStatus() {
        return "Good";
    }
}