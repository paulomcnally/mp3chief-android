package com.mc.mp3chief.tools;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Created by paulomcnally on 6/10/13.
 */
public class Api {
    private static String log = "Api";
    private static String url = "http://mp3chief.aws.af.cm/";

    private String responseBody = null;

    private JSONArray json;

    public Api() {
        super();
    }

    public JSONArray request(String query) {
        Log.i(log, "request called");
        HttpResponse response = null;
        String query_parse = "";
        try {
            query_parse = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncodingException", e.getMessage());
        }
        String response_string = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url + "?q=" + query_parse));
            response = client.execute(request);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                response_string = EntityUtils.toString(responseEntity);
            }
            try {
                json = new JSONArray(response_string);
            } catch (JSONException e) {
                Log.e("JSONException", e.getMessage());
            }
        } catch (URISyntaxException e) {
            Log.e("URISyntaxException", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

        return json;
    }
}
