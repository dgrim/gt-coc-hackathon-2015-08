package edu.gatech.coc_hackathon_2015_09;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dgrim on 8/29/15.
 */
public class ApiServiceActivity extends Activity {
    public ApiServiceActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tokentest);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Button button = (Button) findViewById(R.id.token_button);
        final TextView textView = (TextView) findViewById(R.id.tokenTextView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiNetwork service = new ApiNetwork();
                //textView.setText(service.accessToken);
                //JSONObject json = service.makeGetRequest("/account/vehicles?offset=0&size=2", null);
                //JSONObject json = service.makePostRequest("/account/vehicles/1G6DH5E53C0000003/commands/start", null, null);
                String body = "{  \"diagnosticsRequest\": {    \"diagnosticItem\": [      \"FUEL TANK INFO\",      \"LAST TRIP DISTANCE\",      \"LAST TRIP FUEL ECONOMY\",      \"LIFETIME FUEL ECON\",      \"LIFETIME FUEL USED\",      \"ODOMETER\",      \"OIL LIFE\",      \"TIRE PRESSURE\",      \"VEHICLE RANGE\"    ]  }}";
                JSONObject json = service.makePostRequest("/account/vehicles/1G6DH5E53C0000003/commands/diagnostics", null, body);

                textView.setText(json.toString());
            }
        });



    }

}
