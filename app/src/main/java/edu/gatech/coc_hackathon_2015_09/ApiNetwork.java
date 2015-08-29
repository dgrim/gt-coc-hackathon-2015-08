// Copyright 2004-present Facebook. All Rights Reserved.

package edu.gatech.coc_hackathon_2015_09;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by clawrence on 7/2/15.
 * This class contains the methods neccessary to connect to a URL and return the results of the
 * connection.
 */
public class ApiNetwork {

    private String mApiShortcode;

    private static final String TAG = "ApiNetwork";
    private static final String ENDPOINT = "https://api.instagram.com/v1/media/shortcode/";
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

    }

    /*
     * Opens a new HTTP URL connection, visits a target url, and returns a byte array of the
     * connection's result.
     * @param urlSpec the target url
     * @return the resulting byte array from the connection, if successful
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /*
     * Takes the byte array from getUrlBytes(urlSpec) and returns a String representation of the
     * result.
     * @param urlSpec the target url
     * @return a String representation of the connection's result
     */
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    /*
     * Builds a URI to a specified Instagram post using the user's login credentials. Successfully
     * visiting the API enpoint will return a String representation of the post's JSON that is
     * parsed for the caption, location data, thumbnail image URL, and standard resolution image
     * URL.
     *
     */
    public void fetchItems() throws JSONException {
//        try {
//            String url = Uri.parse(ENDPOINT).buildUpon().appendPath(mApiShortcode)
//                    .appendQueryParameter("access_token", LoginFragment.getAccessToken())
//                    .build().toString();
//
//            String jsonstring = getUrlString(url);
//            InstagramParser parser = new InstagramParser();
//
//            try {
//                //In case the user didn't tag a location
//                latitude = parser.parseFromString(jsonstring).data.location.latitude;
//                longitude = parser.parseFromString(jsonstring).data.location.longitude;
//                locationName = parser.parseFromString(jsonstring).data.location.name;
//            } catch (NullPointerException npe) {
//                Log.e("Error", "NullPointerException occurred: ", npe);
//            }
//
//            try {
//                //Just in case the user doesn't have a caption
//                caption = parser.parseFromString(jsonstring).data.caption.text;
//            } catch (NullPointerException npe) {
//                Log.e("NoCaption", "NullPointerException occurred: ", npe);
//            }
//            thumbnailUrl = parser.parseFromString(jsonstring).data.images.thumbnail.url;
//            standardResUrl = parser.parseFromString(jsonstring).data.images.standard_resolution.url;
//
//        } catch (IOException ioe) {
//            Log.e(TAG, "Failed to get JSON", ioe);
//        }

    }

}
