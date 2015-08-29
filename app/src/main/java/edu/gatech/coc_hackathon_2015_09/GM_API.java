package edu.gatech.coc_hackathon_2015_09;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

/**
 * Created by Tejasvi on 8/29/2015.
 */
public class GM_API {
    private static final ApiNetwork NETWORK_SERVER = new ApiNetwork();

    public enum DATA_SERVICE_TYPE {
        TELEMETRY, HARD_BRAKE, HARD_ACCELERATION, DTC_NOTIFICATION
    }

    public static JSONObject getOwenerInfo(String vin) throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder url = new StringBuilder("/account/subscribers".replace("{vin}", URLEncoder.encode(vin, "UTF-8")));
        url.append("?");
        url.append(URLEncoder.encode("offset", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&");
        url.append(URLEncoder.encode("limit","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8") + "&");
        url.append(URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("OWNER", "UTF-8"));

        return NETWORK_SERVER.makeGetRequest(url.toString(), null);
    }

    public static void startIgnition(String vin) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("/account/vehicles/{vin}/commands/enableIgnition".replace("{vin}", URLEncoder.encode(vin, "UTF-8")));
        NETWORK_SERVER.makePostRequest(url.toString(), null, null);

        url = new StringBuilder("/account/vehicles/{vin}/commands/start".replace("{vin}", URLEncoder.encode(vin, "UTF-8")));
        NETWORK_SERVER.makePostRequest(url.toString(), null, null);
    }

    public static String getDiagnosticUrl(String vin) throws UnsupportedEncodingException, JSONException {
        StringBuilder url = new StringBuilder("/account/vehicles/{vin}/commands/diagnostics".replace("{vin}", URLEncoder.encode(vin, "UTF-8")));
        String body = "{  \"diagnosticsRequest\": {    \"diagnosticItem\": [      \"FUEL TANK INFO\",      \"OIL LIFE\",      \"TIRE PRESSURE\"    ]  }}";
        JSONObject json = NETWORK_SERVER.makePostRequest(url.toString(), null, body);

        String returnedURL = json.getJSONObject("commandResponse").getString("url");

        //return returnedURL.substring(returnedURL.indexOf("requests/") + 10, returnedURL.length());
        return returnedURL;
    }


    public static JSONObject getDiagnosticInfo(String vin) throws UnsupportedEncodingException {
        StringBuilder url = null;
        try {
            url = new StringBuilder(getDiagnosticUrl(vin));
            url.append("?");
            url.append(URLEncoder.encode("units", "UTF-8") + "=" + URLEncoder.encode("METRIC", "UTF-8"));
            JSONObject response = null;
            do {
                response = NETWORK_SERVER.makeGetRequest(url.toString(), null);
            } while(response.getJSONObject("commandResponse").getString("status").equals("inProgress"));

            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject getTelemetryData(String vin) throws UnsupportedEncodingException {
        return getSpecificData(DATA_SERVICE_TYPE.TELEMETRY, vin);
    }

    public static JSONObject getHardBrakeData(String vin) throws UnsupportedEncodingException {
        return getSpecificData(DATA_SERVICE_TYPE.HARD_BRAKE, vin);
    }

    public static JSONObject getHardAccelerationData(String vin) throws UnsupportedEncodingException {
        return getSpecificData(DATA_SERVICE_TYPE.HARD_ACCELERATION, vin);
    }

    public static JSONObject getDTCMalfunctionData(String vin) throws UnsupportedEncodingException {
        return getSpecificData(DATA_SERVICE_TYPE.DTC_NOTIFICATION, vin);
    }

    private static JSONObject getSpecificData(DATA_SERVICE_TYPE dst, String vin) throws UnsupportedEncodingException {
        String type;
        if (dst.equals(DATA_SERVICE_TYPE.TELEMETRY)) {
            type = "TELEMETRY";
        } else if (dst.equals(DATA_SERVICE_TYPE.HARD_ACCELERATION)) {
            type = "HARD_ACCELERATION";
        } else if (dst.equals(DATA_SERVICE_TYPE.HARD_BRAKE)) {
            type = "HARD_BRAKE";
        } else{
            type = "DTC_NOTIFICATION";
        }

        StringBuilder url = new StringBuilder("/account/vehicles/{vin}/data/services/{service_code}".replace("{vin}", URLEncoder.encode(vin, "UTF-8")).replace("{service_code}", URLEncoder.encode(type, "UTF-8")));
        url.append("?");
        url.append(URLEncoder.encode("from", "UTF-8") + "=" + URLEncoder.encode("1990-10-05T19:00:00.000Z", "UTF-8") + "&");
        url.append(URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode("2015-8-05T19:00:00.000Z", "UTF-8") + "&");
        url.append(URLEncoder.encode("offet", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&");
        url.append(URLEncoder.encode("limit", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));

        return NETWORK_SERVER.makeGetRequest(url.toString(), null);
    }

}
