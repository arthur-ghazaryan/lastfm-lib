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


package com.ag.lfm;

import android.support.annotation.Nullable;

import com.ag.lfm.api.httpClient.JSONOperation;

import org.json.JSONObject;

/**
 * Class for execution API-requests.
 */
@SuppressWarnings("unused")
public class LfmRequest {

    /**
     * Selected method name
     */
    private String methodName;

    /**
     * Method parameters
     */
    private LfmParameters parameters;

    /**
     * Parameters for scrobble.
     */
    private ScrobbleParameters scrobbleParameters = null;

    /**
     * Used for indicating if request need authentication.
     */
    private boolean needAuth;

    /**
     * Specify listener for current request
     */
    @Nullable
    public LfmRequestListener requestListener;

    /**
     * Create new request with parameters.
     *
     * @param method     API - method name,e.g track.getInfo
     * @param parameters method parameters.
     */
    public LfmRequest(String method, LfmParameters parameters, boolean needAuth) {
        this.methodName = method;
        this.parameters = parameters;
        this.needAuth = needAuth;
    }

    /**
     * Create new request with scroblle parameters.
     */
    public LfmRequest(ScrobbleParameters scrobbleParameters) {
        this.scrobbleParameters = scrobbleParameters;
    }

    /**
     * Executes that request, and returns result to blocks
     *
     * @param listener listener for request events
     */
    public void executeWithListener(LfmRequestListener listener) {
        this.requestListener = listener;
        start();
    }

    private void start() {
        if (scrobbleParameters == null)
            new JSONOperation(methodName, parameters, needAuth).execute(requestListener);
        else
            new JSONOperation(scrobbleParameters).execute(requestListener);
    }

    public static abstract class LfmRequestListener {

        /**
         * Called if there were no HTTP or API errors, returns execution result.
         *
         * @param response response from LfmRequest
         */
        public abstract void onComplete(JSONObject response);


        /**
         * Called immediately if there was API error, or  if there was an HTTP error
         *
         * @param error error for LfmRequest
         */
        public abstract void onError(LfmError error);
    }
}
