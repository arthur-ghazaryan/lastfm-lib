//Copyright 2016 Arthur Ghazaryan

//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.

package com.ag.lfm.api.httpClient;

import android.os.AsyncTask;

import com.ag.lfm.Lfm;
import com.ag.lfm.LfmError;
import com.ag.lfm.LfmParameters;
import com.ag.lfm.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Operation for authorization.
 */
public class AuthOperation extends AsyncTask<Lfm.LfmCallback<Session>, Void, Void> {

    /**
     * The API auth URL (HTTPS)
     */
    private static final String AUTH_URL_HTTPS = "https://ws.audioscrobbler.com/2.0/?method=auth.getMobileSession&format=json";

    /**
     * Error from API request.
     */
    private LfmError error = new LfmError();

    /**
     * JSON response from API request.
     */
    private JSONObject response;
    /**
     * Session key.
     */
    private Session sessionKey = new Session();

    private Lfm.LfmCallback<Session> callback;

    private LfmParameters params;

    public AuthOperation(LfmParameters params) {
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (sessionKey != null) {
            Lfm.saveSession();
            callback.onResult(sessionKey);
        } else if (error != null)
            callback.onError(error);
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(Lfm.LfmCallback<Session>... params) {
        this.callback = params[0];
        authorize(params[0]);
        return null;
    }


    private String userCredentials(LfmParameters params) {
        StringBuilder builder = new StringBuilder();
        try {
            builder
                    .append("username").append("=")
                    .append(URLEncoder.encode(params.get("username"), "UTF-8"))
                    .append("&").append("password").append("=")
                    .append(params.get("password"))
                    .append("&").append("api_sig").append("=").append(params.get("api_sig"))
                    .append("&").append("api_key").append("=")
                    .append(Lfm.getApiKey());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    private void authorize(Lfm.LfmCallback<Session> callback) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;
        try {
            URL url = new URL(AUTH_URL_HTTPS);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(userCredentials(params));
            wr.flush();
            wr.close();
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                try {
                    response = new JSONObject(buffer.toString());
                    if (!response.optString("error").isEmpty()) {
                        error = new LfmError(response);
                        sessionKey = null;
                    } else {
                        sessionKey = new Session(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                sessionKey = null;
                error.errorCode = connection.getResponseCode();
                error.errorMessage = connection.getResponseMessage();
            }
        } catch (IOException e) {
            error.httpClientError = true;
            error.errorMessage = e.getMessage();
            sessionKey = null;
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    error.httpClientError = true;
                    error.errorMessage = e.getMessage();
                    sessionKey = null;
                }
        }

    }
}
