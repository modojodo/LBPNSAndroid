package com.lbpns.android.lbpnsandroid;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Umer on 11/19/2015.
 */
public class ServerCommunication {
    //    Genymotion/virtualbox ip address for localhost: 192.168.56.1:3000
    static final String CHARSET = "UTF-8";
    static final String ROOT_URL = "http://192.168.0.101:3000";
    static final String SIGNUP_LINK = ROOT_URL + "/signup";
    static final String LOGIN_LINK = ROOT_URL + "/login";
    static final String AUTH_LINK = ROOT_URL + "/authenticate";
    static final String GET_PREFERENCES_BY_RESTAURANT = ROOT_URL + "/getPreferencesByRestaurant";
    static final String GET_PREFERENCES_BY_CUISINE = ROOT_URL + "/getPreferencesByCuisine";
    private static final String TAG = "ServerCommunication";
    private static Context _this = null;

    public ServerCommunication(Context c) {
        _this = c;
    }

    public static boolean signup(String email, String password) {

        String urlParameters = "";
        String statusProperty = "signedUp";
        boolean signedUp = false;
        try {
            urlParameters = String.format("email=%s&password=%s", URLEncoder.encode(email, CHARSET), URLEncoder.encode(password, CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject response = postRequest(urlParameters, new URL(SIGNUP_LINK));
            if (response != null) {

                if (response.has(statusProperty)) {
                    try {
                        String statusResponse = response.getString(statusProperty);
                        Log.d(TAG, statusResponse);
                        if (statusResponse.equals("true"))
                            signedUp = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return signedUp;
    }

    public static boolean login(String email, String password) {

        String urlParameters = "";
        String statusProperty = "logged";
        boolean loggedIn = false;
        try {
            urlParameters = String.format("email=%s&password=%s", URLEncoder.encode(email, CHARSET), URLEncoder.encode(password, CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject response = postRequest(urlParameters, new URL(LOGIN_LINK));
            if (response != null) {

                if (response.has(statusProperty)) {
                    try {
                        String statusResponse = response.getString(statusProperty);
                        Log.d(TAG, statusResponse);
                        if (statusResponse.equals("true"))
                            loggedIn = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return loggedIn;
    }

    public static JSONObject postRequest(String postQuery, URL url) {
        HttpURLConnection connection = null;
//        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", CHARSET);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
            connection.setInstanceFollowRedirects(false);
            HttpURLConnection.setFollowRedirects(true);
            connection.setDoOutput(true);
            OutputStream output = connection.getOutputStream();
            output.write(postQuery.getBytes(CHARSET));
            if (connection.getResponseCode() == 201 || connection.getResponseCode() == 200) {
                InputStream response = connection.getInputStream();
                String jsonStringResponse = convertStreamToString(response);
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(jsonStringResponse);
                    List<HttpCookie> list = cookieManager.getCookieStore().getCookies();
                    System.out.println("The length of cookie is: " + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        HttpCookie cookie = list.get(i);
                        if (cookie.getName().equals("connect.sid")) {
                            CookiePreferenceStore.saveValue(_this, cookie.getValue());
                            break;
                        }
                    }
                    response.close();
                    connection.disconnect();
                    return jsonResponse;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONObject getRequest(String link) {

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
//            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Sending the cookies: " + CookiePreferenceStore.getValue(_this));
            conn.setRequestProperty("Cookie", "connect.sid=" + CookiePreferenceStore.getValue(_this));
            conn.connect();


//            conn.setDoOutput(true);
            Log.d(TAG, "Request sent");
            InputStream response;
            String jsonReply;
            JSONObject jsonResponse = null;
            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
                response = conn.getInputStream();
                jsonReply = convertStreamToString(response);
                try {
                    jsonResponse = new JSONObject(jsonReply);
                    List<HttpCookie> list = cookieManager.getCookieStore().getCookies();
                    System.out.println("The length of cookie is: " + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        HttpCookie cookie = list.get(i);
                        if (cookie.getName().equals("connect.sid")) {
                            CookiePreferenceStore.saveValue(_this, cookie.getValue());
                            break;
                        }
                    }
                    response.close();
                    conn.disconnect();
                    return jsonResponse;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONArray getRequest(URL url) {

//        URL url = null;
//        try {
//            url = new URL(link);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        HttpURLConnection conn = null;
        try {
//            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Sending the cookies: " + CookiePreferenceStore.getValue(_this));
            conn.setRequestProperty("Cookie", "connect.sid=" + CookiePreferenceStore.getValue(_this));
            conn.connect();


//            conn.setDoOutput(true);
            Log.d(TAG, "Request sent");
            InputStream response;
            String jsonReply;
            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
                response = conn.getInputStream();
                jsonReply = convertStreamToString(response);

                List<HttpCookie> list = cookieManager.getCookieStore().getCookies();
                System.out.println("The length of cookie is: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    HttpCookie cookie = list.get(i);
                    if (cookie.getName().equals("connect.sid")) {
                        CookiePreferenceStore.saveValue(_this, cookie.getValue());
                        break;
                    }
                }
                response.close();
                conn.disconnect();
                JSONArray jsonArrReply = new JSONArray(jsonReply);
                return jsonArrReply;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean authenticate() {
        String statusProperty = "authenticated";
        boolean authenticated = false;
        JSONObject authenticationStatus = getRequest(AUTH_LINK);
        if (authenticationStatus != null) {
            if (authenticationStatus.has(statusProperty)) {
                try {
                    String statusResponse = authenticationStatus.getString(statusProperty);
                    Log.d(TAG, statusResponse);
                    if (statusResponse.equals("true"))
                        authenticated = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return authenticated;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


//    public String executePost(String[] params) throws ExecutionException, InterruptedException {
//        ServerRequestTask requestTask = new ServerRequestTask();
//        String response = requestTask.execute(params).get();
//        return response;
//    }
}