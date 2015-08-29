package edu.gatech.coc_hackathon_2015_09;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.Override;
import java.util.HashMap;

public class TripActivity extends Activity {

    TextView mTitleTextView;
    Button mEndTripButton;
    static boolean mCurrentlyDriving;
    static boolean mFinishedDriving;
    static String mTirePressure;
    static HashMap<Integer, Double> mSpeedMap = new HashMap<>();
    static String mVehicleStatus;
    static String vin = "1G6DH5E53C0000003";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mTitleTextView = (TextView) findViewById(R.id.current_trip);
        mEndTripButton = (Button) findViewById(R.id.button_end_trip);

        //get the list of diagnostic info

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
        String color = "GREEN";

        try {
            JSONObject diagnostics = GM_API.getDiagnosticInfo(vin);
            JSONArray jsonArr = diagnostics.getJSONObject("commandResponse").getJSONObject("body").getJSONArray("diagnosticResponse");
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject nestedObj = jsonArr.getJSONObject(i);
                String name = nestedObj.getString("name");
                if (name.equals("TIRE PRESSURE")) {
                    JSONArray tires = nestedObj.getJSONArray("diagnosticElement");
                    for (int j = 0; j < tires.length(); j++) {
                        ;
                        if (tires.getJSONObject(i).getString("message").equals("RED")) {
                            color = "RED";
                            break;
                        } else if (tires.getJSONObject(i).getString("message").equals("YELLOW")) {
                            color = "YELLOW";
                        }

                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Use API for tire pressure and save value into tirePressure;


        mTirePressure = color;
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

    protected static String getTirePressure() {
        return mTirePressure;
    }



    protected static Double getAverageSpeed() {
        double sum = 0.0;
        for (int i = 0; i < mSpeedMap.size(); i++) {
            sum += mSpeedMap.get(i);
        }
        //return (sum / mSpeedMap.size());
        return 42.0;
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

    protected static Integer calculateScore() {
        int score = 0;
        if (getStatus().toLowerCase().equals("green")) {
            score += 10;
        } else if (getStatus().toLowerCase().equals("yellow")) {
            score += 5;
        } else {
            score -= 5;
        }

        if (getHardBrakes() < 3) {
            score += 10;
        } else {
            score -= 3;
        }

        if (getAverageSpeed() < 55.0) {
            score += 15;
        } else if (getAverageSpeed() < 70.0) {
            score += 5;
        } else {
            score -= 10;
        }
        return score;
    }
}