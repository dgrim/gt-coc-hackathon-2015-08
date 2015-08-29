// Copyright 2004-present Facebook. All Rights Reserved.

package edu.gatech.coc_hackathon_2015_09;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by clawrence on 7/2/15.
 * This class contains the methods neccessary to connect to a URL and return the results of the
 * connection.
 */
public class ApiNetwork {

    private String mApiShortcode;

    private static final String TAG = "ApiNetwork";
    private static final String ENDPOINT = "https://developer.gm.com/api/v1";
    private static final String BASE_64_ENC_CREDS = "bDd4eGIxYWNlM2I0YzQ0NTRmMWE5ZWM3YTMwYjY5Yzg1MGY2OjVkNDEwMzVhNTYxOTQxM2Q4Y2ViZTZlYjFjNGJkOWVi";
    public String accessToken;
    public String accessTokenType;
    public int accessTokenExpiresIn;


    protected static float longitude;
    protected static float latitude;
    protected static String thumbnailUrl;
    protected static String caption;
    protected static String locationName;
    protected static String standardResUrl;

    /*
     * Constructs a special instance of ApiNetwork that is intended to visit the media shortcode
     * enpoint of the Instagram API and use the fetchItems() method
     * @param shortcode the shortcode of the Instagram post you would like to receive a JSON for.
     */
    public ApiNetwork(String shortcode) {
        mApiShortcode = shortcode;
    }

    /*
     * Constructs an instance of ApiNetwork. Use this when making general connections that are not
     * related to the media shortcode endpoint of the Instagram API.
     */
    public ApiNetwork() {
        setToken();
    }

    /**
     * Make a get request.
     * @param urlStr
     * @param parameters
     * @return
     */
    public JSONObject makeGetRequest(String urlStr, Map<String, String> parameters) {
        JSONObject responseJSON = null;
        try{
            String urlString = urlStr.startsWith(ENDPOINT) ? urlStr : String.format("%s%s", ENDPOINT, urlStr);
            URL url =  new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if(null == parameters){
                parameters = new HashMap<String, String>();
            }

            if(null != accessToken){
                parameters.put("Accept", "application/json");
                parameters.put("Authorization", String.format("%s %s", accessTokenType, accessToken));
            }

            //If there are additional paramaters
            for(String key : parameters.keySet()){
                connection.setRequestProperty(key, parameters.get(key));
            }

            // get response in bytes.
            String responseString = getResponseString(connection);
            responseJSON = new JSONObject(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseJSON;
    }


    /**
     * Make POST request.
     *
     * @param urlStr
     * @param parameters
     * @param body
     * @return
     */
    public JSONObject makePostRequest(String urlStr, Map<String, String> parameters, String body) {
        JSONObject responseJSON = null;
        try{
            String urlString = urlStr.startsWith(ENDPOINT) ? urlStr : String.format("%s%s", ENDPOINT, urlStr);
            URL url =  new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");


            if(null == parameters){
                parameters = new HashMap<String, String>();
            }

            if(null != accessToken){
                parameters.put("Accept", "application/json");
                parameters.put("Authorization", String.format("%s %s", accessTokenType, accessToken));
            }

            //If there are additional paramaters
            for(String key : parameters.keySet()){
                connection.setRequestProperty(key, parameters.get(key));
            }

            if(null != body){
                //write to output stream.

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                byte[] outputInBytes = body.getBytes("UTF-8");
                connection.setFixedLengthStreamingMode(outputInBytes.length);

                connection.getOutputStream().write(outputInBytes);
            }

            // get response in bytes.
            String responseString = getResponseString(connection);
            responseJSON = new JSONObject(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseJSON;
    }

    /**
     * Sets token for all requests.
     */
    public void setToken() {
        try {
            Map<String, String> params = new HashMap<String,String>();

            // create params.
            params.put("Accept", "application/json");
            params.put("Authorization", String.format("Basic %s", BASE_64_ENC_CREDS));

            JSONObject tokenResponse = makeGetRequest("/oauth/access_token?grant_type=client_credentials", params);

            this.accessToken = tokenResponse.getString("access_token");
            this.accessTokenExpiresIn = tokenResponse.getInt("expires_in");
            this.accessTokenType = tokenResponse.getString("token_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * Opens a new HTTP URL connection, visits a target url, and returns a byte array of the
     * connection's result.
     * @param urlSpec the target url
     * @return the resulting byte array from the connection, if successful
     */
    private byte[] getResponseInBytes(HttpURLConnection connection) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK && connection.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
                throw new IOException(connection.getResponseMessage() + ": with " + connection.getResponseCode());
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return null;
    }

    private String getResponseString(HttpURLConnection connection){
        return new String(getResponseInBytes(connection));
    }
}
