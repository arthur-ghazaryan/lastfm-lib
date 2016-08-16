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

import com.ag.lfm.LfmError;
import com.ag.lfm.LfmParameters;
import com.ag.lfm.LfmRequest;
import com.ag.lfm.ScrobbleParameters;
import com.ag.lfm.util.LfmUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Sending request using AsyncTask.
 */
public class JSONOperation extends AsyncTask<LfmRequest.LfmRequestListener, Void, Void> {


    /**
     * API request parameters.
     */
    private LfmParameters params;

    /**
     * Root URL.
     */
    private static final String ROOT_URL = "https://ws.audioscrobbler.com/2.0/";

    /**
     * API method.
     */
    private String method;

    /**
     * API request URL.
     */
    private String requestURL;

    /**
     * Error from API request.
     */
    private LfmError error = new LfmError();

    /**
     * JSON response from API request.
     */
    private JSONObject response;

    /**
     * Scrobble parameters.
     */
    private ScrobbleParameters scrobbleParameters;

    private LfmRequest.LfmRequestListener listener;

    public boolean scrobbleMethod;


    private boolean restRequest;

    public JSONOperation(String method, LfmParameters params, boolean restRequest) {
        this.params = params;
        this.method = method;
        this.restRequest = restRequest;
    }

    public JSONOperation(ScrobbleParameters scrobbleParameters) {
        this.scrobbleParameters = scrobbleParameters;
        scrobbleMethod = true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!restRequest && !scrobbleMethod)
            requestURL = LfmUtil.generateRequestURL(method, params);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (response != null)
            listener.onComplete(response);
        else if (error != null)
            listener.onError(error);
    }

    @Override
    protected Void doInBackground(LfmRequest.LfmRequestListener... params) {
        this.listener = params[0];
        if (restRequest) {
            restRequest(params[0]);
        } else if (scrobbleMethod) {
            scrobble(params[0]);
        } else
            getJSON(params[0]);
        return null;
    }

    /**
     * Method for JSON request.
     */
    private void getJSON(LfmRequest.LfmRequestListener listener) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;
        try {
            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();

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
                    if (!response.optString("error").equals("")) {
                        error = new LfmError(response);
                        response = null;
                    } else {
                        error = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                error.errorCode = connection.getResponseCode();
                error.errorMessage = connection.getResponseMessage();
                response = null;
            }
        } catch (IOException e) {
            error.httpClientError = true;
            error.errorMessage = e.getMessage();
            response = null;
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                error.httpClientError = true;
                error.errorMessage = e.getMessage();
            }

        }

    }


    private void scrobble(LfmRequest.LfmRequestListener listener) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;
        try {
            URL url = new URL(ROOT_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(scrobbleParameters.parseParameters());
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
                    if (!response.optString("error").equals("")) {
                        error = new LfmError(response);
                        response = null;
                    } else if (response.optJSONObject("ignoredMessage") != null) {
                        error.errorCode = Integer.valueOf(response.optJSONObject("ignoredMessage").optString("code"));
                        error.errorMessage = response.optJSONObject("ignoredMessage").optString("#text");
                    } else {
                        error = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                error.errorCode = connection.getResponseCode();
                error.errorMessage = connection.getResponseMessage();
                response = null;
            }
        } catch (IOException e) {
            error.httpClientError = true;
            error.errorMessage = e.getMessage();
            response = null;
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                error.httpClientError = true;
                error.errorMessage = e.getMessage();
            }

        }

    }

    private void restRequest(LfmRequest.LfmRequestListener listener) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;
        try {
            URL url = new URL(ROOT_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(LfmUtil.parseRestRequestParams(method, params));
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
                    if (!response.optString("error").equals("")) {
                        error = new LfmError(response);
                        response = null;
                    } else {
                        error = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                error.errorCode = connection.getResponseCode();
                error.errorMessage = connection.getResponseMessage();
                response = null;
            }
        } catch (IOException e) {
            error.httpClientError = true;
            error.errorMessage = e.getMessage();
            response = null;
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                error.httpClientError = true;
                error.errorMessage = e.getMessage();
            }

        }

    }

}

